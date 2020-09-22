package com.yitongyin.modules.ad.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonParseException;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.dao.AdMerchantProductDao;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.Product;
import com.yitongyin.modules.ad.form.ProductTag;
import com.yitongyin.modules.ad.form.SpecKeyOrder;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.DesignFee;
import com.yitongyin.modules.mp.View.PriceAndDays;
import com.yitongyin.modules.mp.View.ProInfoAndCase;
import com.yitongyin.modules.mp.View.TypeAndPro;
import com.yitongyin.nosql.elasticsearch.document.EsProduct;
import com.yitongyin.nosql.elasticsearch.repository.EsProductRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;


@Service("adMerchantProductService")
public class AdMerchantProductServiceImpl extends ServiceImpl<AdMerchantProductDao, AdMerchantProductEntity> implements AdMerchantProductService {
    @Autowired
    AdOssService adOssService;
    @Autowired
    AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    AdMerchantCaseService adMerchantCaseService;
    @Autowired
    BusSearchLogService busSearchLogService;
    @Autowired
    BusProductService busProductService;
    @Autowired
    AdMerchantProductDao adMerchantProductDao;
    @Autowired
    TServiceTypeService tServiceTypeService;
    @Autowired
    BusSupplierService busSupplierService;
    @Autowired
    BusSupplierProductService busSupplierProductService;
    @Autowired
    EsProductRepository esProductRepository;
    @Autowired
    BusSupplierSpecGroupService busSupplierSpecGroupService;
    @Autowired
    TMerchantService tMerchantService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(AdMerchantProductEntity entity) {
        UpdateWrapper<AdMerchantProductEntity> update = new UpdateWrapper<>();
        update.eq("service_type_id", entity.getServiceTypeId()).eq("merchant_id",entity.getMerchantId());
        this.update(entity, update);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusByIds(List<Object> ids, Long merchantId,Integer show) {
        UpdateWrapper<AdMerchantProductEntity> update = new UpdateWrapper<>();
        update.set("mp_show",show);
        update.eq("merchant_id",merchantId);
        update.in("service_type_id",ids);
        this.update(update);
    }

    @Override
    public Integer getSelectCount(long id) {
        QueryWrapper<AdMerchantProductEntity> query = new QueryWrapper<>();
        query.eq("product_id",id);
        query.eq("mp_show",1);
        return this.count(query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatuByIds(List<Integer> ids,Integer status,Long merchantId) {
        AdMerchantProductEntity entity =new AdMerchantProductEntity();
        entity.setMpShow(status);
        UpdateWrapper<AdMerchantProductEntity> update = new UpdateWrapper<>();
        update.in("id",ids);
        update.eq("merchant_id",merchantId);
        this.update(entity, update);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updMpShowByBusPro(List<Long> busIds, Integer status, Long merchantId) {
        AdMerchantProductEntity entity =new AdMerchantProductEntity();
        entity.setMpShow(status);
        UpdateWrapper<AdMerchantProductEntity> update = new UpdateWrapper<>();
        update.in("product_id",busIds);
        update.eq("merchant_id",merchantId);
        return this.update(entity, update);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDescrip(AdMerchantProductEntity entity) {
        UpdateWrapper<BusProductEntity> update = new UpdateWrapper<>();
        update.set("productContent",entity.getProductContent());
        update.eq("id",entity.getId());
        busProductService.update(update);
    }



    @Override
    public AdMerchantProductEntity mpGetOneByMerIdAndProId(long merchantId, long proId) {
        QueryWrapper<AdMerchantProductEntity> query = new QueryWrapper<>();
        query.eq("merchant_id",merchantId);
        query.eq("product_id",proId);
        query.eq("publish_able",1).eq("mp_show",1);
        return this.getOne(query);
    }

    @Override
    public List<AdMerchantProductEntity> getListByTypeId(long typeId, long merchantId) {
        QueryWrapper<AdMerchantProductEntity> query = new QueryWrapper<>();
        query.eq("merchant_id",merchantId);
        query.eq("service_type_id",typeId);
        return this.list();
    }

    @Override
    public List<TypeAndPro.proInfo> getPopularListByMerchantIdAndType(List<Object> typeIds, long merchantId,Long serviceTypeId) {
        List<AdMerchantProductEntity> products= new ArrayList<>();
        if(typeIds!=null&&typeIds.size()!=0){
            products= adMerchantProductDao.getPopularListByParentType(merchantId,typeIds,0,5);
        }else{
            products= adMerchantProductDao.getPopularList(merchantId,serviceTypeId,0,5);
        }
        List<TypeAndPro.proInfo> infos =products.stream().map(entity -> {
            TypeAndPro.proInfo info =  new TypeAndPro().new proInfo();
            info.setProName(entity.getProductName());
            info.setProId(entity.getId());
            info.setDes(entity.getProductDes());
            if(entity.getOssId()!=null&&!entity.getOssId().equals("")){
                AdOssEntity ossEntity = adOssService.findById(entity.getOssId());
                info.setCoverUrl(ossEntity==null?null:ossEntity.getUrl());
            }
            return  info;
        }).collect(Collectors.toList());
//        Collections.sort(infos, new Comparator<TypeAndPro.proInfo>() {
//            @Override
//            public int compare(TypeAndPro.proInfo o1, TypeAndPro.proInfo o2) {
//                //闄嶅簭
//                return o1.getSelectCount().compareTo(o2.getSelectCount());
//            }
//        });
        return infos;
    }

    @Override
    public PageUtils getPageListByMerchantIdAndType(Map<String, Object> params, List<Object> typeIds, long merchantId, Long serviceTypeId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdMerchantProductEntity> products= new ArrayList<>();
        if(typeIds!=null&&typeIds.size()!=0){
            products= adMerchantProductDao.getPopularListByParentType(merchantId,typeIds,start,limit);
            Integer count =adMerchantProductDao.getCountPopularListByParentType(merchantId,typeIds);
            products.forEach( e -> {
                if(e.getOssId()!=null)
                    e.setOssUrl(adOssService.findById(e.getOssId())==null?null:adOssService.findById(e.getOssId()).getUrl());
            } );
            return new PageUtils(products,count,limit,page);
        }else{
            products= adMerchantProductDao.getPopularList(merchantId,serviceTypeId,start,limit);
            Integer count =adMerchantProductDao.getCountPopularList(merchantId,serviceTypeId);
            products.forEach( e -> {
                if(e.getOssId()!=null)
                    e.setOssUrl(adOssService.findById(e.getOssId())==null?null:adOssService.findById(e.getOssId()).getUrl());
            } );
            return new PageUtils(products,count,limit,page);
        }

    }




    @Override
    public PageUtils getMpListByConditions(Map<String, Object> params, Long serviceTypeId, long merchantId, PriceAndDays priceAndDays) {
        if(priceAndDays==null){
            priceAndDays = new PriceAndDays();
        }
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        TMerchantEntity merchantEntity = tMerchantService.getById(merchantId);
        List<BusSupplierEntity>supplierEntityList =busSupplierService.getPageByMerchantCountyWithAllSupplier(merchantEntity.getCounty());
        if(supplierEntityList==null||supplierEntityList.size()==0){
            PageUtils nullPageUtils =new PageUtils(new ArrayList<>(),0,limit,page);
            return nullPageUtils;
        }
        List<Long> supplierIds=supplierEntityList.stream().map(e->e.getSupplierid()).collect(Collectors.toList());
        List<BusSupplierProductEntity> supProducts=busSupplierProductService.getListBySupIds(supplierIds);
        List<Long> proIds=supProducts.stream().map(e->e.getProductId()).collect(Collectors.toList());

        List<AdMerchantProductEntity> list= adMerchantProductDao.getMpListByConditions(merchantId,serviceTypeId,priceAndDays.getTags(),
                priceAndDays.getLowestPrice(),priceAndDays.getHighestPrice(),priceAndDays.getLowestDays(),priceAndDays.getHighestDays(),
                priceAndDays.getHits(),proIds,start,limit);
        Integer  count =adMerchantProductDao.getCountMpListByConditions(merchantId,serviceTypeId,priceAndDays.getTags(),
                priceAndDays.getLowestPrice(),priceAndDays.getHighestPrice(),priceAndDays.getLowestDays(),priceAndDays.getHighestDays(),proIds
        );
        list.forEach( e -> {
            if(e.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(e.getOssId());
                e.setOssUrl(ossEntity==null?null:ossEntity.getUrl());
            }else{
                e.setOssUrl(null);
            }
            AdMerchantSpecGroupEntity groupEntity =adMerchantSpecGroupService.getLowestPriceByMerAndPro(merchantId,e.getProductId());
            if(groupEntity!=null){
                AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getDefRateByClientIdAndMerchantProcutId(e.getId(),merchantId);

               if(adGroupProductRateEntity!=null){
                   if(adGroupProductRateEntity.getIsShow()==1&&groupEntity.getPrice()!=null){
                       e.setLowPrice(groupEntity.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                   }
               }else{
                   e.setLowPrice(groupEntity.getPrice());
               }
                e.setDay(groupEntity.getDeliveryDay());
            }
        } );
        if(StringUtils.isNotBlank(priceAndDays.getTags())) {
            if(list!=null&&list.size()!=0){
                BusSearchLogEntity entity = new BusSearchLogEntity();
                entity.setSarchtitle(priceAndDays.getTags());
                entity.setLastsearchip(priceAndDays.getIp());
                busSearchLogService.saveOrUpd(entity,true);
            }else{
                BusSearchLogEntity entity = new BusSearchLogEntity();
                entity.setSarchtitle(priceAndDays.getTags());
                entity.setLastsearchip(priceAndDays.getIp());
                busSearchLogService.saveOrUpd(entity,false);
            }
        }
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public ProInfoAndCase getInfoById(long id) {
        QueryWrapper<AdMerchantProductEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_able",1).eq("mp_show",1).eq("id",id);
        AdMerchantProductEntity product=this.getOne(queryWrapper);
        if(product==null)
            return null;
        BusProductEntity busProductEntity =busProductService.getSomePropertyById(product.getProductId());
        if(busProductEntity==null){
            return null;
        }
        String jsons=busProductEntity.getDesignfee();
        List<DesignFee> designFees = new ArrayList<>();
        if(StringUtils.isNotBlank(jsons)){
             try {
                 designFees= JSONArray.parseArray(jsons, DesignFee.class);
             }catch (JSONException e){
                  throw  new RRException("数据异常",e);
             }
        }
        product.setProductName(busProductEntity.getProductname());
        product.setOssId(busProductEntity.getOssid().longValue());
        product.setProductContent(busProductEntity.getProductcontent());
        product.setProductDes(busProductEntity.getProductdescr());
        List<AdMerchantProductEntity> entities= adMerchantProductDao.getMpBrothers(product.getMerchantId(),product.getServiceTypeId(),id);
        for (AdMerchantProductEntity entity1: entities) {
            if(entity1.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(entity1.getOssId());
                entity1.setOssUrl(ossEntity==null?null:ossEntity.getUrl());
            }
        }
        product.setBrothers(entities);
       // TServiceTypeEntity typeEntity=tServiceTypeService.getById(product.getServiceTypeId());
        ProInfoAndCase infoAndCase = new ProInfoAndCase();
        infoAndCase.setId(product.getId());
        infoAndCase.setDesignFees(designFees);
        infoAndCase.setIntro(product.getProductDes());
        infoAndCase.setName(product.getProductName());
        infoAndCase.setBrothers(product.getBrothers());
        infoAndCase.setMaterialUrl(busProductEntity.getMaterialurl());
        infoAndCase.setOutlink(busProductEntity.getOutlink());
        if(product.getOssId()!=null&&!product.getOssId().equals("")){
            String url =adOssService.findById(product.getOssId()).getUrl();
            infoAndCase.setCover(url);
        }
        infoAndCase.setContent(product.getProductContent());
        infoAndCase.setCaseCount(adMerchantCaseService.countByProId(product.getProductId(),product.getMerchantId()));
        infoAndCase.setCases(adMerchantCaseService.getOneByMerchantIdAndProId(product.getProductId(),product.getMerchantId(),product.getProductName()));
        return  infoAndCase;
    }


    @Override
    public List<AdMerchantProductEntity> listAll(Long merchantId) {

        return adMerchantProductDao.getListAllByMerchantId(merchantId);
    }

    @Override
    public AdMerchantProductEntity infoById(Long id,Long merchantId) {
        AdMerchantProductEntity entity =adMerchantProductDao.getInfoById(id);
        if(entity.getOssId()!=null){
            entity.setOssUrl(adOssService.findById(entity.getOssId())==null?null:adOssService.findById(entity.getOssId()).getUrl());
        }
        List<AdMerchantProductEntity> entities= adMerchantProductDao.getBrothers(merchantId,entity.getServiceTypeId(),id);
        for (AdMerchantProductEntity entity1: entities) {
            if(entity1.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(entity1.getOssId());
                entity1.setOssUrl(ossEntity==null?null:ossEntity.getUrl());
            }
        }
        entity.setBrothers(entities);
//        List<String> urls = ossIds.stream().map(p -> adOssService.findById(p==null?0:p)==null?null:adOssService.findById(p).getUrl()).collect(Collectors.toList());
//        entity.setBrotherUrls(urls);
        return entity;
    }

    @Override
    public List<AdMerchantProductEntity> getHotsByTime(Long merchantId, String startTime, String endTime) {
        List<AdMerchantProductEntity> list=new ArrayList<>();
        if(StringUtils.isBlank(startTime)){
            list=adMerchantProductDao.getHotsAll(merchantId);
        }else{
            list=adMerchantProductDao.getHotsByTime(merchantId,startTime,endTime);
        }
        Map<Long, List<AdMerchantProductEntity>> listMap = list.stream().collect(Collectors.groupingBy(AdMerchantProductEntity::getProductId));
        List<AdMerchantProductEntity> list1= new ArrayList<>();
        for (Long lo:listMap.keySet()) {
            AdMerchantProductEntity adMerchantProductEntity = new AdMerchantProductEntity();
            int totalValue = listMap.get(lo).stream().mapToInt(AdMerchantProductEntity::getHitCount).sum();
            BusProductEntity productEntity =busProductService.getSomePropertyById(lo);
            adMerchantProductEntity.setProductName(productEntity==null?null:productEntity.getProductname());
            adMerchantProductEntity.setHits(totalValue);
            list1.add(adMerchantProductEntity);
        }
        List<AdMerchantProductEntity> sortList = list1.stream().sorted((u1, u2) -> u2.getHits().compareTo(u1.getHits())).collect(Collectors.toList());
        return sortList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertSupProId(Long merchantId, Long productId, Long supProId) {
        UpdateWrapper<AdMerchantProductEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("supplier_product_id",supProId);
        updateWrapper.eq("merchant_id",merchantId);
        updateWrapper.eq("product_id",productId);
        updateWrapper.isNull("supplier_product_id");
        return this.update(updateWrapper);
    }

    @Override
    public boolean saveTagsById(Long proId, List<ProductTag> tags) {
        String jsonTags=JSONArray.toJSONString(tags);
        UpdateWrapper<AdMerchantProductEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("tag_json",jsonTags);
        updateWrapper.eq("id",proId);
        return this.update(updateWrapper);
    }

    @Override
    public List<AdMerchantProductEntity> getAllShowList(Long merchantId) {
        QueryWrapper<AdMerchantProductEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        queryWrapper.eq("merchant_id",merchantId);
        return this.list(queryWrapper);
    }

    @Override
    public PageUtils getRateListByConditions(Map<String, Object> params, Long serviceTypeId, long merchantId, PriceAndDays priceAndDays, Long clientId) {
        if(priceAndDays==null){
            priceAndDays = new PriceAndDays();
        }
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        TMerchantEntity merchantEntity = tMerchantService.getById(merchantId);
        List<BusSupplierEntity>supplierEntityList =busSupplierService.getPageByMerchantCountyWithAllSupplier(merchantEntity.getCounty());
        if(supplierEntityList==null||supplierEntityList.size()==0){
            PageUtils nullPageUtils =new PageUtils(new ArrayList<>(),0,limit,page);
            return nullPageUtils;
        }
        List<Long> supplierIds=supplierEntityList.stream().map(e->e.getSupplierid()).collect(Collectors.toList());
        List<BusSupplierProductEntity> supProducts=busSupplierProductService.getListBySupIds(supplierIds);
        List<Long> proIds=supProducts.stream().map(e->e.getProductId()).collect(Collectors.toList());

        List<AdMerchantProductEntity> list= adMerchantProductDao.getMpListByConditions(merchantId,serviceTypeId,priceAndDays.getTags(),
                priceAndDays.getLowestPrice(),priceAndDays.getHighestPrice(),priceAndDays.getLowestDays(),priceAndDays.getHighestDays(),
                priceAndDays.getHits(),proIds,start,limit);
        Integer  count =adMerchantProductDao.getCountMpListByConditions(merchantId,serviceTypeId,priceAndDays.getTags(),
                priceAndDays.getLowestPrice(),priceAndDays.getHighestPrice(),priceAndDays.getLowestDays(),priceAndDays.getHighestDays(),proIds
        );
        list.forEach( e -> {
            if(e.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(e.getOssId());
                e.setOssUrl(ossEntity==null?null:ossEntity.getUrl());
            }else{
                e.setOssUrl(null);
            }
            AdMerchantSpecGroupEntity groupEntity =adMerchantSpecGroupService.getLowestPriceByMerAndPro(merchantId,e.getProductId());
            if(groupEntity!=null){
                AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
                        ,e.getId(),merchantId);
                if(adGroupProductRateEntity!=null){
                    if(adGroupProductRateEntity.getIsShow()==1&&groupEntity.getPrice()!=null){
                        e.setLowPrice(groupEntity.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                    }
                }else{
                    e.setLowPrice(groupEntity.getPrice());
                }
                e.setDay(groupEntity.getDeliveryDay());
            }
        } );
        if(StringUtils.isNotBlank(priceAndDays.getTags())) {
            if(list!=null&&list.size()!=0){
                BusSearchLogEntity entity = new BusSearchLogEntity();
                entity.setSarchtitle(priceAndDays.getTags());
                entity.setLastsearchip(priceAndDays.getIp());
                busSearchLogService.saveOrUpd(entity,true);
            }else{
                BusSearchLogEntity entity = new BusSearchLogEntity();
                entity.setSarchtitle(priceAndDays.getTags());
                entity.setLastsearchip(priceAndDays.getIp());
                busSearchLogService.saveOrUpd(entity,false);
            }
        }
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public AdMerchantProductEntity getOneByMerchantIdAndProductId(Long merchantId, Long productId) {
        QueryWrapper<AdMerchantProductEntity> query = new QueryWrapper<>();
        query.eq("merchant_id",merchantId);
        query.eq("product_id",productId);
        return this.getOne(query);
    }

    @Override
    public PageUtils
    queryRatePage(Map<String, Object> params, TMerchantEntity merchantEntity,Long groupId,String name,Long typeId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<BusSupplierEntity>supplierEntityList =busSupplierService.getPageByMerchantCountyWithAllSupplier(merchantEntity.getCounty());
        if(supplierEntityList==null||supplierEntityList.size()==0){
            PageUtils nullPageUtils =new PageUtils(new ArrayList<>(),0,limit,page);
            return nullPageUtils;
        }
        List<Long> supplierIds=supplierEntityList.stream().map(e->e.getSupplierid()).collect(Collectors.toList());
        List<BusSupplierProductEntity> supProducts=busSupplierProductService.getListBySupIds(supplierIds);
        List<Long> proIds=supProducts.stream().map(e->e.getProductId()).collect(Collectors.toList());
        List<AdMerchantProductEntity> searchList= adMerchantProductDao.getListByTypeAndName(merchantEntity.getMerchantid(),typeId,name,null,
                proIds,start,limit);
        Integer count =adMerchantProductDao.getCountByTypeAndName(merchantEntity.getMerchantid(),typeId,name,null,proIds);
        PageUtils pageutils = new PageUtils(searchList,count,limit,page);
        List<AdMerchantProductEntity> list= (List<AdMerchantProductEntity>)pageutils.getList();
        List<Product> productList=new ArrayList<>();
        for(AdMerchantProductEntity entity :list){
            Product product=new Product();
            product.setId(entity.getId());
            product.setIsSupModel(entity.getSupplierSpecConf());
            product.setBusId(entity.getProductId());
            product.setName(entity.getProductName());
            product.setStatus(entity.getMpShow());
            product.setSelectCount(getSelectCount(entity.getProductId()));
            AdGroupProductRateEntity rateEntity = adGroupProductRateService.getRateByGroupIdAndMerchantProcutId(product.getId(),groupId);
            if(rateEntity!=null){
                product.setRate(rateEntity.getPriceRate());
            }
            List<BusSupplierProductEntity> busSupplierProductEntities=busSupplierProductService.getEntListByProId(entity.getProductId());
            List<Long> objectIds=busSupplierProductEntities.stream().map(e->e.getSupplierId()).collect(Collectors.toList());
            List<Long> filterList = objectIds.stream().filter(item -> supplierIds.contains(item)).collect(Collectors.toList());
            List<Long> supIds = new ArrayList<>();
            if(entity.getSupplierSpecConf()!=null&&entity.getSupplierSpecConf().equals(1)){
                supIds=filterList;
            }else{
                BusSupplierEntity busSupplierEntity = busSupplierService.getByLevelId();
                if(busSupplierEntity!=null){
                    supIds.add(busSupplierEntity.getSupplierid());
                }
            }
            Boolean hvData=adMerchantSpecGroupService.getByProductIdAndMerchantId(merchantEntity.getMerchantid(),entity.getProductId());
            List<BusSupplierSpecGroupEntity> supGroupList=busSupplierSpecGroupService.getListBySupplierIdsAndProId(supIds,
                    entity.getProductId());
            if(hvData||(supGroupList!=null&&supGroupList.size()>0)){
                product.setIsPrice(1);
            }else{
                product.setIsPrice(0);
            }
            if(hvData){
                product.setHavPrice(1);
            }else{
                product.setHavPrice(0);
            }
            product.setSupplierCount(filterList.size());
            List<Object> objectList =filterList.stream().map(e->Long.valueOf(e.toString())).collect(Collectors.toList());
            List<Map<String,Object>>  mapList=busSupplierService.getListById(objectList);
            product.setSuppliers(mapList);
            if(entity.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(entity.getOssId());
                product.setUrl(ossEntity==null?null:ossEntity.getUrl());
            }
            productList.add(product);

        }

        pageutils.setList(productList);
        return pageutils;
    }

    @Override
    public List<AdMerchantProductEntity> mpGetAllShowListLimit(Long merchantId) {
       return adMerchantProductDao.mpGetIdsShowListLimit(merchantId);
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params,Long typeId,TMerchantEntity merchantEntity,String name,Integer status) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<BusSupplierEntity>supplierEntityList =busSupplierService.getPageByMerchantCountyWithAllSupplier(merchantEntity.getCounty());
        if(supplierEntityList==null||supplierEntityList.size()==0){
            PageUtils nullPageUtils =new PageUtils(new ArrayList<>(),0,limit,page);
             return nullPageUtils;
        }
        List<Long> supplierIds=supplierEntityList.stream().map(e->e.getSupplierid()).collect(Collectors.toList());
        List<BusSupplierProductEntity> supProducts=busSupplierProductService.getListBySupIds(supplierIds);
        List<Long> proIds=supProducts.stream().map(e->e.getProductId()).collect(Collectors.toList());
        List<AdMerchantProductEntity> searchList= adMerchantProductDao.getListByTypeAndName(merchantEntity.getMerchantid(),typeId,name,status,
                proIds,start,limit);
        Integer count =adMerchantProductDao.getCountByTypeAndName(merchantEntity.getMerchantid(),typeId,name,status,proIds);
        PageUtils pageutils = new PageUtils(searchList,count,limit,page);
        List<AdMerchantProductEntity> list= (List<AdMerchantProductEntity>)pageutils.getList();
        List<Product> productList=new ArrayList<>();
        for(AdMerchantProductEntity entity :list){
            Product product=new Product();
            product.setId(entity.getId());
            product.setIsSupModel(entity.getSupplierSpecConf());
            product.setBusId(entity.getProductId());
            product.setName(entity.getProductName());
            product.setStatus(entity.getMpShow());
            product.setSelectCount(getSelectCount(entity.getProductId()));
            List<BusSupplierProductEntity> busSupplierProductEntities=busSupplierProductService.getEntListByProId(entity.getProductId());
            List<Long> objectIds=busSupplierProductEntities.stream().map(e->e.getSupplierId()).collect(Collectors.toList());
            List<Long> filterList = objectIds.stream().filter(item -> supplierIds.contains(item)).collect(Collectors.toList());
            List<Long> supIds = new ArrayList<>();
            if(entity.getSupplierSpecConf()!=null&&entity.getSupplierSpecConf().equals(1)){
                 supIds=filterList;
            }else{
                BusSupplierEntity busSupplierEntity = busSupplierService.getByLevelId();
                if(busSupplierEntity!=null){
                    supIds.add(busSupplierEntity.getSupplierid());
                }
            }
            Boolean hvData=adMerchantSpecGroupService.getByProductIdAndMerchantId(merchantEntity.getMerchantid(),entity.getProductId());
            List<BusSupplierSpecGroupEntity> supGroupList=busSupplierSpecGroupService.getListBySupplierIdsAndProId(supIds,
                    entity.getProductId());
            if(hvData||(supGroupList!=null&&supGroupList.size()>0)){
                product.setIsPrice(1);
            }else{
                product.setIsPrice(0);
            }
            if(hvData){
                product.setHavPrice(1);
            }else{
                product.setHavPrice(0);
            }
            product.setSupplierCount(filterList.size());
            List<Object> objectList =filterList.stream().map(e->Long.valueOf(e.toString())).collect(Collectors.toList());
            List<Map<String,Object>>  mapList=busSupplierService.getListById(objectList);
            product.setSuppliers(mapList);
            if(entity.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(entity.getOssId());
                product.setUrl(ossEntity==null?null:ossEntity.getUrl());
            }
            productList.add(product);

        }

        pageutils.setList(productList);
        return pageutils;
    }

    private Page<EsProduct> esProductQuery(int page, int limit,Long typeId,Long merchantId,String name,Integer status, String queryType){
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        FieldSortBuilder sort = SortBuilders.fieldSort("orderNumber").order(SortOrder.ASC);
        Pageable pageable = PageRequest.of(page-1,limit);
        builder.must(QueryBuilders.termQuery("publishAble",1));
        builder.must(QueryBuilders.termQuery("merchantId",merchantId));
        if (typeId!= null&&typeId!=0) {
            builder.must(QueryBuilders.termQuery("serviceTypeId",typeId));
        }
        if (status!= null) {
            builder.must(QueryBuilders.termQuery("mpShow",status));
        }
        if(StringUtils.isNotBlank(name)){
            QueryBuilder keywordQB = null;
            //閫傜敤浜庢暟瀛椼€佸瓧姣嶆ā绯婃煡璇紝涓嶉€傜敤涓枃妯＄硦鏌ヨ
            if (name.matches("^[A-Za-z0-9]+$")){
                keywordQB = wildcardQuery("productName", ("*" + name + "*").toLowerCase());
            }else{
                if(queryType == "matchPhraseQuery"){
                    keywordQB = matchPhraseQuery("productName", (name));
                }else {
                    keywordQB = matchQuery("productName", (name));
                }
            }
            builder.must(keywordQB);
        }

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        nativeSearchQueryBuilder.withPageable(pageable);
        nativeSearchQueryBuilder.withSort(sort);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        return esProductRepository.search(query);
    }

    @Override
    public List<Product> getByMerchantId(Long merchantId,List<Long> ids,Integer start,Integer limit) {
        if(ids==null||ids.size()==0)
            return new ArrayList<>();
        List<AdMerchantProductEntity> searchList= adMerchantProductDao.getListAllByMerchantIdAndProIds(merchantId,ids,start,limit);
        List<Product> productList=new ArrayList<>();
        for(AdMerchantProductEntity entity :searchList){
            Product product=new Product();
            product.setId(entity.getId());
            product.setIsSupModel(entity.getSupplierSpecConf());
            product.setBusId(entity.getProductId());
            product.setName(entity.getProductName());
            product.setStatus(entity.getMpShow());
            product.setSelectCount(getSelectCount(entity.getProductId()));
            List<Object> busSupplierProductEntities=busSupplierProductService.getListByProId(entity.getProductId());
            product.setSupplierCount(busSupplierProductEntities.size());
            List<Map<String,Object>>  mapList=busSupplierService.getListById(busSupplierProductEntities);
            product.setSuppliers(mapList);
            if(entity.getOssId()!=null){
                AdOssEntity ossEntity =adOssService.findById(entity.getOssId());
                product.setUrl(ossEntity==null?null:ossEntity.getUrl());
            }
            productList.add(product);

        }
        return  productList;
    }

}