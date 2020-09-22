package com.yitongyin.modules.ad.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.controller.cloud.OSSFactory;
import com.yitongyin.modules.ad.dao.BusMaterialDao;
import com.yitongyin.modules.ad.dao.BusMaterialIndustryDao;
import com.yitongyin.modules.ad.dao.BusMaterialTypeDao;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.BusMaterialEntity;
import com.yitongyin.modules.ad.entity.BusMaterialIndustryEntity;
import com.yitongyin.modules.ad.entity.BusMaterialTypeEntity;
import com.yitongyin.modules.ad.form.BusMaterialConditions;
import com.yitongyin.modules.ad.form.MaterialIM;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.BusMaterialService;
import com.yitongyin.nosql.elasticsearch.document.EsMaterial;
import com.yitongyin.nosql.elasticsearch.repository.EsMaterialRepository;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;


@Service("busMaterialService")
public class BusMaterialServiceImpl extends ServiceImpl<BusMaterialDao, BusMaterialEntity> implements BusMaterialService {

    @Autowired
    AdOssService adOssService;
    @Autowired
    BusMaterialTypeDao busMaterialTypeDao;
    @Autowired
    BusMaterialIndustryDao busMaterialIndustryDao;
    @Resource
    Environment environment;
    @Autowired
    EsMaterialRepository esMaterialRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public PageUtils queryPage1(Map<String, Object> params, BusMaterialConditions conditions) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        String keyword =conditions.getKeyWord();
        Pageable pageable = PageRequest.of(page-1,limit);

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        FieldSortBuilder sort = null;
        if (conditions.getBtmId() != null&&conditions.getBtmId()!=0) {
            builder.must(QueryBuilders.termQuery("bmtid",conditions.getBtmId()));
        }
        if (conditions.getTypeId() != null&&conditions.getTypeId()!=0) {
            builder.must(QueryBuilders.termQuery("fileType",conditions.getTypeId()));
        }
        if(conditions.getBitId()!=null&&conditions.getBitId()!=0){
            builder.must(QueryBuilders.termQuery("bitIds",conditions.getBitId()));
        }
        if (conditions.getIsMostSend() != null) {
            sort = SortBuilders.fieldSort("sendCounts").order(SortOrder.DESC);
        }
        if (conditions.getIsMostView() != null) {
            sort = SortBuilders.fieldSort("viewCounts").order(SortOrder.DESC);
        }
        if (conditions.getIsNew() != null) {
            sort = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        }
        if(StringUtils.isNotBlank(keyword)){
            QueryBuilder keywordQB = null;
            //适用于数字、字母模糊查询，不适用中文模糊查询
            if(keyword.matches("[0-9]+")){
                keywordQB=QueryBuilders.termQuery("id", keyword);
            }else{
                if (keyword.matches("^[A-Za-z0-9]+$")){
                    keywordQB = QueryBuilders.disMaxQuery().add(wildcardQuery("tagKey", ("*" + keyword + "*").toLowerCase())).tieBreaker((float)0.3);
                }else{
                    //按best_field查询并乘以tie_breaker
                    //keywordQB = QueryBuilders.disMaxQuery().add(matchQuery("tagKey",keyword)).tieBreaker((float) 0.3);
                    keywordQB =  QueryBuilders
                            .multiMatchQuery(keyword, "tagKey")
                            .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                            .tieBreaker(0.1f);
                }
            }

            builder.must(keywordQB);
        }

        //把查询纳入sore排序
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builder);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        nativeSearchQueryBuilder.withPageable(pageable);
        //按tie_breaker乘积的分数排序
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort());
        if(sort!=null){
            nativeSearchQueryBuilder.withSort(sort);
        }
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<EsMaterial> res = esMaterialRepository.search(query);

        PageUtils pageutils = new PageUtils(res.getContent(),(int)res.getTotalElements(),limit,page);
        if(StringUtils.isNotBlank(keyword)){
            List<EsMaterial> list=(List<EsMaterial>) pageutils.getList();
            List<EsMaterial> resultList = new ArrayList<>();
            List<EsMaterial> exactMatchList=list.stream().filter(e -> e.getTagKey().contains(keyword)).collect(Collectors.toList());
            List<EsMaterial> fuzzyMatchList=list.stream().filter(e -> !e.getTagKey().contains(keyword)).collect(Collectors.toList());
            if(exactMatchList!=null&&exactMatchList.size()!=0){
                resultList.addAll(exactMatchList);
            }
            if(fuzzyMatchList!=null&&fuzzyMatchList.size()!=0){
                resultList.addAll(fuzzyMatchList);
            }
            pageutils.setList(resultList);
        }
        return pageutils;
    }

    // restful-api方式查询
    private List<EsMaterial> searchByCondition(Map<String, Object> params, BusMaterialConditions conditions)throws Exception{
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        String keyword =conditions.getKeyWord();
        SearchRequestBuilder srb= client.prepareSearch("yitongyin_material").setTypes("bus_material");

        // query
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if (conditions.getBtmId() != null&&conditions.getBtmId()!=0) {
            builder.must(QueryBuilders.termQuery("bmtid",conditions.getBtmId()));
        }
        if (conditions.getTypeId() != null&&conditions.getTypeId()!=0) {
            builder.must(QueryBuilders.termQuery("fileType",conditions.getTypeId()));
        }
        if(conditions.getBitId()!=null&&conditions.getBitId()!=0){
            builder.must(QueryBuilders.termQuery("bitIds",conditions.getBitId()));
        }
        if(StringUtils.isNotBlank(keyword)){
            builder.must(QueryBuilders.matchQuery("tagKey", keyword));
        }

        // sort
        String sortName="";
        SortOrder sortOrder=SortOrder.DESC;
        if (conditions.getIsMostSend() != null) {
            sortName = "sendCounts";
            sortOrder = SortOrder.DESC;
        }
        if (conditions.getIsMostView() != null) {
            sortName = "viewCounts";
            sortOrder = SortOrder.DESC;
        }
        if (conditions.getIsNew() != null) {
            sortName = "createTime";
            sortOrder = SortOrder.DESC;
        }

        List<EsMaterial> esMaterials = new ArrayList<>();
        if(sortName!="") {
            SearchResponse sr = srb.setQuery(builder)
                    .setFetchSource(new String[]{"id", "bmtid", "title", "coverResId", "fileType", "fileResId", "createTime", "viewCounts", "sendCounts"}, null)
                    .setFrom(page).setSize(limit)
                    .addSort(sortName, sortOrder)
                    .execute()
                    .actionGet(); // 分页排序所有
            SearchHits hits = sr.getHits();
            for (SearchHit hit : hits) {
                EsMaterial esMaterial = JSON.parseObject(hit.getSourceAsString(),EsMaterial.class);
                esMaterials.add(esMaterial);
                System.out.println(hit.getSourceAsString());
            }
        }else{
            SearchResponse sr = srb.setQuery(builder)
                    .setFetchSource(new String[]{"id", "bmtid", "title", "coverResId", "fileType", "fileResId", "createTime", "viewCounts", "sendCounts"}, null)
                    .setFrom(page).setSize(limit)
                    .execute()
                    .actionGet();
            SearchHits hits = sr.getHits();
            for (SearchHit hit : hits) {
                EsMaterial esMaterial = JSON.parseObject(hit.getSourceAsString(),EsMaterial.class);
                esMaterials.add(esMaterial);
                System.out.println(hit.getSourceAsString());
            }
        }

        client.close();
        return esMaterials;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, BusMaterialConditions conditions) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());

        Page<EsMaterial> phrasePage = this.esMatchQuery(page,limit,conditions,"matchPhraseQuery");
        Page<EsMaterial> matchPage = this.esMatchQuery(page,limit,conditions,"");
        List<EsMaterial> listAll = new ArrayList<>();
        listAll.addAll(phrasePage.getContent());
        listAll.addAll(matchPage.getContent());
        listAll = new ArrayList<EsMaterial>(new LinkedHashSet<>(listAll));

        PageUtils pageutils = new PageUtils(listAll,(int)(phrasePage.getTotalElements()+matchPage.getTotalElements()),limit,page);
        return pageutils;
    }

    private Page<EsMaterial> esMatchQuery(int page, int limit,BusMaterialConditions conditions,String queryType){
        String keyword =conditions.getKeyWord();
        Pageable pageable = PageRequest.of(page-1,limit);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        FieldSortBuilder sort = null;
        if (conditions.getBtmId() != null&&conditions.getBtmId()!=0) {
            builder.must(QueryBuilders.termQuery("bmtid",conditions.getBtmId()));
        }
        if (conditions.getTypeId() != null&&conditions.getTypeId()!=0) {
            builder.must(QueryBuilders.termQuery("fileType",conditions.getTypeId()));
        }
        if (conditions.getIsMostSend() != null) {
            sort = SortBuilders.fieldSort("sendCounts").order(SortOrder.DESC);
        }
        if (conditions.getIsMostView() != null) {
            sort = SortBuilders.fieldSort("viewCounts").order(SortOrder.DESC);
        }
        if (conditions.getIsNew() != null) {
            sort = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        }
        if(StringUtils.isNotBlank(keyword)){
            QueryBuilder keywordQB = null;
            //适用于数字、字母模糊查询，不适用中文模糊查询
            if(keyword.matches("[0-9]+")){
                keywordQB=QueryBuilders.termQuery("id", keyword);
            }else{
                if (keyword.matches("^[A-Za-z0-9]+$")){
                    keywordQB = QueryBuilders.disMaxQuery().add(wildcardQuery("tagKey", ("*" + keyword + "*").toLowerCase())).tieBreaker((float)0.3);
                }else{
                    //按best_field查询并乘以tie_breaker
                    //keywordQB = QueryBuilders.disMaxQuery().add(matchQuery("tagKey",keyword)).tieBreaker((float) 0.3);
                    if(queryType == "matchPhraseQuery") {
                        keywordQB = QueryBuilders.matchPhraseQuery("tagKey", keyword);
                    }else{
                        keywordQB = QueryBuilders.matchQuery("tagKey", keyword);
                    }
                }
            }

            builder.must(keywordQB);
        }

        //把查询纳入sore排序
        //FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builder);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        nativeSearchQueryBuilder.withPageable(pageable);
        //按tie_breaker乘积的分数排序
        //nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort());
        if(sort!=null){
            nativeSearchQueryBuilder.withSort(sort);
        }
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        return esMaterialRepository.search(query);
    }

    //同步导入素材图片和文件
    @Override
    public void importMaterialSync(List<MaterialIM> list) {
        String uploadPrefix = environment.getProperty("material-import-path");

        for (MaterialIM item : list) {
            String coverResPath = uploadPrefix + item.getCoverImgPath();
            String filePath = uploadPrefix + item.getFilePath();
            QueryWrapper<BusMaterialTypeEntity> query = new QueryWrapper<>();
            query.eq("name", item.getMaterialType());
            BusMaterialTypeEntity materialTypeEntity = busMaterialTypeDao.selectOne(query);

            int fileTypeId = 0;
            switch (item.getFileType().toUpperCase()) {
                case "PSD":
                    fileTypeId = 1;
                    break;
                case "AI":
                    fileTypeId = 2;
                    break;
                case "CDR":
                    fileTypeId = 3;
                    break;
                case "EPS":
                    fileTypeId = 4;
                    break;
                case "TIF":
                    fileTypeId = 5;
                    break;
                case "JPG":
                    fileTypeId = 6;
                    break;
                case "PDF":
                    fileTypeId = 7;
                    break;
            }

            BusMaterialEntity material = new BusMaterialEntity();
            material.setCreateTime(new Date());
            material.setTitle(item.getTagKey());
            material.setTagKey(item.getTagKey());
            material.setBmtid(materialTypeEntity.getBmtid());
            material.setFileType(fileTypeId);

            try {
                if (!filePath.isEmpty()) {
                    Long fileResId = uploadOssWithStatus(filePath, 1);
                    material.setFileResId(fileResId);
                }

                if (!coverResPath.isEmpty()) {
                    Long coverResId = uploadOssWithStatus(coverResPath, 1);
                    material.setCoverResId(coverResId);
                }

                int r = baseMapper.insert(material);
                if(r>0){
                    String[] idStr = item.getIndustryIds().split("_");
                    Long[] ids = (Long[])ConvertUtils.convert(idStr,Long.class);
                    for (Long id : ids) {
                        BusMaterialIndustryEntity miEntity = new BusMaterialIndustryEntity();
                        miEntity.setMaterialId(material.getId());
                        miEntity.setBitid(id);
                        busMaterialIndustryDao.insert(miEntity);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("导入失败的ID:"+item.getId());
                System.out.println("导入失败的title:"+item.getTagKey());
                break;
            }
        }

    }

    @Override
    public void importMaterialIndustrySync(List<MaterialIM> list) {

        for (MaterialIM item : list) {
            try {
                QueryWrapper<BusMaterialEntity> mQuery = new QueryWrapper<>();
                mQuery.eq("title", item.getTagKey());
                mQuery.orderByDesc("id");
                BusMaterialEntity materialEntity = baseMapper.selectOne(mQuery);
                if(materialEntity!=null){
                    QueryWrapper<BusMaterialIndustryEntity> miQuery = new QueryWrapper<>();
                    miQuery.eq("material_id", materialEntity.getId());
                    List<BusMaterialIndustryEntity> miList = busMaterialIndustryDao.selectList(miQuery);
                    if(miList!=null&&miList.size()>0){
                        continue;
                    }else{
                        //写入行业id
                        String[] idStr = item.getIndustryIds().split("_");
                        Long[] ids = (Long[])ConvertUtils.convert(idStr,Long.class);
                        for (Long id : ids) {
                            BusMaterialIndustryEntity miEntity = new BusMaterialIndustryEntity();
                            miEntity.setMaterialId(materialEntity.getId());
                            miEntity.setBitid(id);
                            busMaterialIndustryDao.insert(miEntity);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("导入失败的ID:"+item.getId());
                System.out.println("导入失败的title:"+item.getTagKey());
                break;
            }
        }

    }

    @Override
    public List<BusMaterialEntity> findByTitle(String title) {
        QueryWrapper<BusMaterialEntity> query = new QueryWrapper<>();
        query.eq("title", title);
        return baseMapper.selectList(query);
    }

    @Override
    public int importAll() {
        List<EsMaterial> esMaterialList = baseMapper.findAllEsMaterials();
        for (EsMaterial esMaterial: esMaterialList) {
             List<Long> bitIds=busMaterialIndustryDao.getBitIdsByMaterialId(esMaterial.getId());
             esMaterial.setBitIds(bitIds);
        }
        Iterable<EsMaterial> esMaterialIterable = esMaterialRepository.saveAll(esMaterialList);
        Iterator<EsMaterial> iterator = esMaterialIterable.iterator();
        int result = 0;
        while (iterator.hasNext()){
            result++;
            iterator.next();
        }
        return result;
    }

//    @Override
//    public Page<EsMaterial> searchByTagKey(String keyword,Long bmtId, Integer pageNum, Integer pageSize) throws Exception {
//        Pageable pageable = PageRequest.of(pageNum,pageSize);
//
//        QueryBuilder keywordQB = null;
//        //适用于数字、字母模糊查询，不适用中文模糊查询
//        if (keyword.matches("^[A-Za-z0-9]+$")){
//            keywordQB = wildcardQuery("tagKey", ("*" + keyword + "*").toLowerCase());
//        }else{
//            keywordQB = matchQuery("tagKey", (keyword));
//        }
//
//        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        builder.must(QueryBuilders.termQuery("bmtid",bmtId));
//        builder.must(keywordQB);
//
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        nativeSearchQueryBuilder.withQuery(builder);
//        nativeSearchQueryBuilder.withPageable(pageable);
//
//        NativeSearchQuery query = nativeSearchQueryBuilder.build();
//        Page<EsMaterial> res = esMaterialRepository.search(query);
//        return res;
//    }

    @Override
    public void deleteAll() {
        esMaterialRepository.deleteAll();
    }

    private Long uploadOss(String filePath) {
        return uploadOssWithStatus(filePath, 2);
    }

    private Long uploadOssWithStatus(String filePath, Integer status) {
        String suffix = filePath.substring(filePath.lastIndexOf('.'));
        byte[] fileBytes = new byte[0];
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                switch (suffix){
                    case ".rar":
                        filePath = filePath.replace(suffix,".zip");
                        break;
                    case ".zip":
                        filePath = filePath.replace(suffix,".rar");
                        break;
                    case ".jpg":
                        filePath = filePath.replace(suffix,".png");
                        break;
                    case ".png":
                        filePath = filePath.replace(suffix,".jpg");
                        break;
                }
            }
            fileBytes = getFileBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = OSSFactory.build().uploadSuffix(fileBytes, suffix);
        //保存文件信息
        AdOssEntity ossEntity = new AdOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        ossEntity.setStatus(status);
        adOssService.save(ossEntity);
        return ossEntity.getId();
    }

    private byte[] getFileBytes(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}