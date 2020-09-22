package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.dao.AdMerchantProductDao;
import com.yitongyin.modules.ad.dao.AdSceneProjectDao;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("adSceneProjectService")
public class AdSceneProjectServiceImpl extends ServiceImpl<AdSceneProjectDao, AdSceneProjectEntity> implements AdSceneProjectService {

    @Autowired
    AdSceneProjectDao adSceneProjectDao;
    @Autowired
    AdMerchantProductDao adMerchantProductDao;
    @Autowired
    AdSceneTypeService adSceneTypeService;
    @Autowired
    AdOssService adOssService;
    @Autowired
    AdSceneProductService adSceneProductService;
    @Autowired
    AdMerchantProductService adMerchantProductService;
    @Autowired
    BusIndustryTypeService busIndustryTypeService;

    @Override
    public PageUtils queryPageByConditions(Map<String, Object> params, SceneProjectSearch search) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        Long bitId=search.getBitId();
        Long spaceStId=search.getSpaceStId();
        Long smId=search.getSmId();
        String title= search.getTitle();
        List<SceneProject> list = adSceneProjectDao.getListByConditions(bitId,spaceStId,smId,title,start,limit);
        list.forEach(e->{
            if(e.getBigMainPicId()!=null){
                AdOssEntity oss= adOssService.getById(e.getBigMainPicId());
                e.setBigMainUrl(oss==null?null:oss.getUrl());
            }
        });
        Integer count= adSceneProjectDao.getCountByConditions(bitId,spaceStId,smId,title);
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public String getUrlOrderOneBySmId(Long smId) {
        return adSceneProjectDao.getUrlBySmId(smId);
    }

    @Override
    public SceneProject getInfoById(Long id,Long merchantId) {
        AdSceneProjectEntity entity = adSceneProjectDao.getInfoAndIndustryNameById(id);
        SceneProject projectView = new SceneProject();
        if(entity==null) return projectView;
        projectView.setSpid(entity.getSpid());
        projectView.setProjectDesc(entity.getProjectDesc());
        projectView.setProjectTitle(entity.getProjectTitle());
        projectView.setIndustryName(entity.getIndustryName());
        projectView.setThumbsUpNumber(entity.getThumbsUpNumber());
        projectView.setMerchantName(entity.getMerchantName());
        projectView.setSmId(entity.getSmid());
        if(entity.getBigMainPicId()!=null){
            AdOssEntity ossEntity = adOssService.getById(entity.getBigMainPicId());
            if(ossEntity!=null){
                projectView.setBigMainUrl(ossEntity.getUrl());
            }
        }
        AdSceneTypeEntity space=adSceneTypeService.getById(entity.getSpaceStid());
        if(space!=null){
            projectView.setSpace(space.getName());
        }
        List<String> urls = new ArrayList<>();
        if(entity.getMainPicId()!=null){
            AdOssEntity ossEntity = adOssService.getById(entity.getMainPicId());
            if(ossEntity!=null){
                urls.add(ossEntity.getUrl());
            }
        }

        if(StringUtils.isNotBlank(entity.getOtherPicIds())){
            List<String> ossIds= UtilString.stringToList(entity.getOtherPicIds());
            List<Long> longList =ossIds.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            List<AdOssEntity> ossEntities = adOssService.findByIds(longList);
            List<String> otherUrls = ossEntities.stream().map(e->e.getUrl()).collect(Collectors.toList());
            urls.addAll(otherUrls);
        }
        projectView.setUrls(urls);
        List<AdSceneProductEntity> sceneProductEntities = adSceneProductService.getListBySpId(id);
        List<SceneProduct> sceneProductViews = sceneProductEntities.stream().map(e->{
            SceneProduct sceneProduct = new SceneProduct();
            sceneProduct.setProductId(e.getProductId());
            sceneProduct.setProName(e.getProductName());
            sceneProduct.setServiceName(e.getServiceName());
            sceneProduct.setProTitle(e.getProductTitle());
            sceneProduct.setProductDesc(e.getProductDesc());
            if(e.getPositionStid()!=null){
                AdSceneTypeEntity typeEntity = adSceneTypeService.getById(e.getPositionStid());
                sceneProduct.setPosition(typeEntity==null?null:typeEntity.getName());
            }
            AdMerchantProductEntity productEntity = adMerchantProductService.mpGetOneByMerIdAndProId(merchantId,e.getProductId());
            sceneProduct.setMerchantProductId(productEntity==null?null:productEntity.getId());
            List<String> urlss =new ArrayList<>();
            if(e.getMainPicId()!=null){
                AdOssEntity oss = adOssService.getById(e.getMainPicId());
                if(oss!=null){
                    urlss.add(oss.getUrl());
                }
            }
            if(StringUtils.isNotBlank(e.getOtherPicIds())){
                List<String> otherPics= UtilString.stringToList(e.getOtherPicIds());
                List<Long> longList =otherPics.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                List<AdOssEntity> ossEntities = adOssService.findByIds(longList);
                List<String > otherUrls = ossEntities.stream().map(c->c.getUrl()).collect(Collectors.toList());
                urlss.addAll(otherUrls);
            }
            if(e.getBigMainPicId()!=null){
                AdOssEntity ossEntity = adOssService.getById(e.getBigMainPicId());
                if(ossEntity!=null){
                    sceneProduct.setBigMainUrl(ossEntity.getUrl());
                }
            }
            sceneProduct.setProUrls(urlss);
            return sceneProduct;
        }).collect(Collectors.toList());
        projectView.setProducts(sceneProductViews);
        List<SceneProjectSimilarView> similarViews = adSceneProjectDao.getSimilarsByConditions(entity.getBitid(),entity.getSmid(),
                entity.getSpaceStid());
        similarViews.forEach(e->{
            AdSceneTypeEntity spaceTypeEntity = adSceneTypeService.getById(e.getSpaceStId());
            e.setSpace(spaceTypeEntity==null?null:spaceTypeEntity.getName());
            BusIndustryTypeEntity industryTypeEntity = busIndustryTypeService.getById(e.getBitId());
            e.setIndustryName(industryTypeEntity==null?null:industryTypeEntity.getName());
        });
        projectView.setSimilars(similarViews);
        return projectView;
    }

    @Override
    public Integer getCountBySmId(Long smId) {
        QueryWrapper<AdSceneProjectEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("smId",smId);
        return this.list(queryWrapper).size();
    }

    @Override
    public PageUtils getProListByType(Map<String,Object> params,Long typeId,Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<ProductInfo> list= adMerchantProductDao.getListByTypeId(typeId,merchantId,start,limit);
        Integer count=adMerchantProductDao.getCountByTypeId(typeId,merchantId);
        return new PageUtils(list,count,limit,page);
    }

}