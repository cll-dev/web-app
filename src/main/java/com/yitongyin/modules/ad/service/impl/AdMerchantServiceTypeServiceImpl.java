package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.config.ManagerConfig;
import com.yitongyin.modules.ad.dao.AdMerchantServiceTypeDao;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.inti.SpringContextSupport;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.ProductType;
import com.yitongyin.modules.mp.View.TypeAndPro;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("adMerchantServiceTypeService")
public class AdMerchantServiceTypeServiceImpl extends ServiceImpl<AdMerchantServiceTypeDao, AdMerchantServiceTypeEntity> implements AdMerchantServiceTypeService {

    @Autowired
    AdMerchantProductService adMerchantProductService;
    @Autowired
    TServiceTypeService tServiceTypeService;
    @Autowired
    AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    AdMerchantServiceTypeDao adMerchantServiceTypeDao;
    @Autowired
    AdOssService adOssService;

    @Override
    public List<AdMerchantServiceTypeEntity> getShowType(long userId) {
        return adMerchantServiceTypeDao.getListIsShowByMerchantId(userId);
    }

    @Override
    public List<AdMerchantServiceTypeEntity> getMpShowType(long userId) {
        return adMerchantServiceTypeDao.getListMpShowByMerchantId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(AdMerchantServiceTypeEntity entity,Long merchantId) {
        entity.setUpdateTime(new Date());
        UpdateWrapper<AdMerchantServiceTypeEntity> update = new UpdateWrapper<>();
        update.eq("typeId", entity.getTypeid());
        this.update(entity, update);
        List<Object> objects =tServiceTypeService.getChilds(entity.getServiceTypeId());
        if(objects!=null&&objects.size()!=0){
            UpdateWrapper<AdMerchantServiceTypeEntity> update1 = new UpdateWrapper<>();
            update1.eq("parent_type_id", entity.getServiceTypeId());
            update1.set("mp_show",entity.getMpShow());
            update1.set("update_time",new Date());
            this.update(update1);
            adMerchantProductService.updateStatusByIds(objects,merchantId,entity.getMpShow());
        }else{
            AdMerchantProductEntity bean= new AdMerchantProductEntity();
            bean.setServiceTypeId(entity.getServiceTypeId());
            bean.setMpShow(entity.getMpShow());
            bean.setMerchantId(merchantId);
            adMerchantProductService.updateStatusById(bean);
        }
    }



    @Override
    public List<AdMerchantServiceTypeEntity> getByMerchantId(Long merchantId) {
        ManagerConfig managerConfig = SpringContextSupport.getBean(ManagerConfig.class);
        List<AdMerchantServiceTypeEntity> list=adMerchantServiceTypeDao.getParentListByMerchantId(merchantId);
        list.forEach( e -> {
            if(StringUtils.isNotBlank(e.getIconPath()))
            e.setIconPath(managerConfig.getUrl()+e.getIconPath());
        } );
        return  list;
    }

    @Override
    public PageUtils getProByPopular(Map<String, Object> params,Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdMerchantServiceTypeEntity> entities =adMerchantServiceTypeDao.getParentListByMerchantIdPage(merchantId,start,limit);
        List<TypeAndPro> typeAndPros =entities.stream().map(entity -> {
            TypeAndPro typeAndPro = new TypeAndPro();
            typeAndPro.setTypeName(entity.getTypeName());
            typeAndPro.setTypeId(entity.getTypeid());
            typeAndPro.setChildNumber(entity.getChildNumber());
            List<Object> ids= tServiceTypeService.getChilds(entity.getServiceTypeId());
            List<TypeAndPro.proInfo> infos= new ArrayList<>();
            if(entity.getServiceTypeId()==1001l) {
                List<AdMerchantServiceTypeEntity> list = adMerchantServiceTypeDao.getChildPageByTypeId(merchantId,
                        entity.getServiceTypeId(), 0, 3);
                list.forEach(e -> {
                    e.setProductName(e.getTypeName());
                    if (e.getCoverResId() != null) {
                        AdOssEntity adOssEntity = adOssService.findById(e.getCoverResId());
                        e.setOssUrl(adOssEntity == null ? null : adOssEntity.getUrl());
                    } else {
                        e.setOssUrl(null);
                    }
                });
                infos = list.stream().map(e->{
                    TypeAndPro.proInfo proInfo = new TypeAndPro().new proInfo();
                    proInfo.setCoverUrl(e.getOssUrl());
                    proInfo.setTypId(e.getTypeid());
                    proInfo.setProName(e.getProductName());
                    proInfo.setDes(e.getDescr());
                    return proInfo;
                }).collect(Collectors.toList());
            }else{
                infos=adMerchantProductService.getPopularListByMerchantIdAndType(ids,merchantId,entity.getServiceTypeId());
            }
            typeAndPro.setProInfoList(infos);
            return  typeAndPro;
        }).collect(Collectors.toList());
        Integer count =adMerchantServiceTypeDao.getCountParentListByMerchantId(merchantId);
        return new PageUtils(typeAndPros,count,limit,page);
    }

    @Override
    public PageUtils getProByTypeId(Map<String, Object> params, Long merchantId, Long serviceTypeId) {
        if(serviceTypeId==1001l){
            int page=Integer.valueOf(params.get("page").toString());
            int limit=Integer.valueOf(params.get("limit").toString());
            int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
            List<AdMerchantServiceTypeEntity> list =adMerchantServiceTypeDao.getChildPageByTypeId(merchantId,
                    serviceTypeId,start,limit);
            list.forEach( e -> {
                e.setProductName(e.getTypeName());
                if(e.getCoverResId()!=null){
                    AdOssEntity adOssEntity =adOssService.findById(e.getCoverResId());
                    e.setOssUrl(adOssEntity==null?null:adOssEntity.getUrl());
                }else{
                    e.setOssUrl(null);
                }
            } );
            Integer count=adMerchantServiceTypeDao.getCountChildPageByTypeId(merchantId,serviceTypeId);
            return new PageUtils(list,count,limit,page);
        }
        List<Object> ids= tServiceTypeService.getChilds(serviceTypeId);
        PageUtils pageUtils=adMerchantProductService.getPageListByMerchantIdAndType(params,ids,merchantId,serviceTypeId);
        return pageUtils;
    }

    @Override
    public List<ProductType> getChildList(Long parentId,Long merchantId) {
        List<AdMerchantServiceTypeEntity> entities =adMerchantServiceTypeDao.getIsChild(merchantId,parentId);
        List<ProductType> types =entities.stream().map(entity -> {
            ProductType type = new ProductType();
            type.setName(entity.getTypeName());
            type.setId(entity.getTypeid());
            type.setDescr(entity.getDescr());
            if(entity.getCoverResId()!=null) {
                AdOssEntity adOssEntity =adOssService.findById(entity.getCoverResId());
                type.setOssUrl(adOssEntity==null?null:adOssEntity.getUrl());
            }
            return  type;
        }).collect(Collectors.toList());
        return  types;
    }

    @Override
    public AdMerchantServiceTypeEntity getByServiceAndMerId(Long merchantId, Long serviceTypeId) {
        QueryWrapper<AdMerchantServiceTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_type_id",serviceTypeId).eq("merchant_id",merchantId);
        queryWrapper.eq("ad_show",1).eq("mp_show",1);
        return this.getOne(queryWrapper);
    }

    @Override
    public AdMerchantServiceTypeEntity getIsShowById(Long id) {
        QueryWrapper<AdMerchantServiceTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("typeId",id);
        queryWrapper.eq("ad_show",1).eq("mp_show",1);
        return this.getOne(queryWrapper);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params,long userId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdMerchantServiceTypeEntity> list=adMerchantServiceTypeDao.getPageIsParent(userId,start,limit);
        Integer count=adMerchantServiceTypeDao.getPageIsParentCount(userId);
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public PageUtils queryChildPage(Map<String, Object> params, long userId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        Long parentTypeId=Long.valueOf(params.get("parentTypeId").toString());
        List<AdMerchantServiceTypeEntity> list=adMerchantServiceTypeDao.getPageIsChild(userId,parentTypeId,start,limit);
        Integer count=adMerchantServiceTypeDao.getPageIsChildCount(userId,parentTypeId);
        return new PageUtils(list,count,limit,page);
    }

}