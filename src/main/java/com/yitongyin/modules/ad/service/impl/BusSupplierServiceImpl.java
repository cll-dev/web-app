package com.yitongyin.modules.ad.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.common.validator.ValidatorUtils;
import com.yitongyin.modules.ad.dao.BusSupplierDao;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.IdValue;
import com.yitongyin.modules.ad.form.NewSuppllierInfo;
import com.yitongyin.modules.ad.form.SupplierConditions;
import com.yitongyin.modules.ad.form.SupplierProductList;
import com.yitongyin.modules.ad.service.*;
import net.sf.ehcache.search.query.QueryManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("busSupplierService")
public class BusSupplierServiceImpl extends ServiceImpl<BusSupplierDao, BusSupplierEntity> implements BusSupplierService {
    @Autowired
    private BusSupplierProductService busSupplierProductService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private AdMerchantSupplierCollectionService adMerchantSupplierCollectionService;
    @Autowired
    private BusSupplierDao busSupplierDao;

    @Override
    public List<Map<String,Object>> getListById(List<Object> ids) {
        if(ids==null||ids.size()==0){
            return  null;
        }
        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
        query.select("supplierId","supplierName");
        query.isNull("supplier_level_id");
        query.in("supplierId",ids);
        return this.listMaps(query);
    }

//    @Override
//    public void addSupplier(BusSupplierEntity supplierEntity) {
//          supplierEntity.setCrtdate(new Date());
//          supplierEntity.setUpddate(new Date());
//        if(StrUtil.isBlank(supplierEntity.getSupplierpinyin())){
//            supplierEntity.setSupplierpinyin("待定");
//        }
//          if(StrUtil.isBlank(supplierEntity.getContacts())){
//              supplierEntity.setContacts("待定");
//          }
//          if(StrUtil.isBlank(supplierEntity.getContactway())){
//            supplierEntity.setContactway("待定");
//          }
//          if(supplierEntity.getVerifyuserid() == null){
//            supplierEntity.setVerifyuserid((long)1);
//          }
//          if(StrUtil.isBlank(supplierEntity.getGrouplevel())){
//            supplierEntity.setGrouplevel("待定");
//          }
//          if(supplierEntity.getProvince() == null){
//            supplierEntity.setProvince(1);
//          }
//          if(supplierEntity.getCity() == null){
//            supplierEntity.setCity(1);
//          }
//          if(supplierEntity.getCounty() == null){
//            supplierEntity.setCounty(1);
//          }
//          if(StrUtil.isBlank(supplierEntity.getAddress())){
//            supplierEntity.setAddress("待定");
//          }
//          if(StrUtil.isBlank(supplierEntity.getLongitude())){
//            supplierEntity.setLongitude("待定");
//          }
//          if(StrUtil.isBlank(supplierEntity.getLatitude())){
//            supplierEntity.setLatitude("待定");
//          }
//          if(StrUtil.isBlank(supplierEntity.getSettlementmodes())){
//            supplierEntity.setSettlementmodes("待定");
//          }
//        ValidatorUtils.validateEntity(supplierEntity);
//        this.save(supplierEntity);
//    }

    @Override
    public NewSuppllierInfo getInfoById(Long id,Long merchantId) {
        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
        query.select("supplierId","supplierName","`into`","address","houseNumber","settlementModes","contactWay"
                ,"otherLinkInfo","relevantQuotationResIds","webPath","deliveryMethod");
        query.eq("supplierId",id);
        BusSupplierEntity entity = this.getOne(query);
        if(entity==null)
        return null;
        NewSuppllierInfo info = new NewSuppllierInfo();
        AdMerchantSupplierCollectionEntity collectionEntity =adMerchantSupplierCollectionService.getStatusByMerchantId
                (id,merchantId);
        info.setStatus(collectionEntity==null?0:collectionEntity.getCollectStatus());
        info.setSupName(entity.getSuppliername());
        info.setAboutUs(entity.getInto());
        info.setAdr(entity.getAddress()+entity.getHousenumber());
        info.setPayWay(entity.getSettlementmodes());
        info.setPhone(entity.getContactway());
        info.setCollectCount(adMerchantSupplierCollectionService.getCountBySupId(id));
        info.setOtherInfo(entity.getOtherlinkinfo());
        info.setWebPath(entity.getWebpath());
        info.setDeliveryMethod(entity.getDeliverymethod());
        info.setTypeName(busSupplierProductService.getTypeNamesBySupId(entity.getSupplierid()));
        if(StringUtils.isNotBlank(entity.getRelevantquotationresids())){
            List<String> ids= UtilString.stringToList(entity.getRelevantquotationresids());
            List<Long> longList =ids.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            List<AdOssEntity> ossEntities =adOssService.findByIds(longList);
            List<String> urls=ossEntities.stream().map(s ->s==null?null:s.getUrl()).collect(Collectors.toList());
            info.setUrls(urls);
        }

        return info;
    }

    @Override
    public List<BusSupplierEntity> queryByMerchantCounty(Integer county) {
        return busSupplierDao.getPageByMerchantCounty(county);
    }

    @Override
    public List<BusSupplierEntity> getPageByMerchantCountyWithAllSupplier(Integer county) {
        return busSupplierDao.getPageByMerchantCountyWithAllSupplier(county);
    }

    @Override
    public BusSupplierEntity findByName(String supplierName) {
        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
        query.eq("supplierName",supplierName);
        return baseMapper.selectOne(query);
    }

    @Override
    public  BusSupplierEntity getByLevelId() {
        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
        query.eq("supplier_level_id",1);
        return this.getOne(query);
    }

    @Override
    public List<Object> filterTemplate(List<Object> ids) {
        if(ids==null||ids.size()==0){
            return  new ArrayList<>();
        }
        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
        query.select("supplierId");
        query.isNull("supplier_level_id");
        query.in("supplierId",ids);
        return this.listObjs(query);
    }

    @Override
    public List<BusSupplierEntity> filterEntTemplate(List<Object> ids) {
        if(ids==null||ids.size()==0){
            return  new ArrayList<>();
        }
        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
        query.select("supplierId");
        query.isNull("supplier_level_id");
        query.in("supplierId",ids);
        return this.list(query);
    }

//    @Override
////    public List<BusSupplierEntity> getInfoListByIds(List<Long> ids) {
////        QueryWrapper<BusSupplierEntity> query = new QueryWrapper<>();
//////      query.select("supplierId","supplierName");
////        query.in("supplierId",ids);
////        return this.list(query);
////    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, List<Long> ids, SupplierConditions conditions) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<BusSupplierEntity> list= busSupplierDao.queryPageByConditions(ids,conditions.getMLongitude(),
                conditions.getMLatitude(),
                conditions.getProvinceId(),
                conditions.getCityId(),conditions.getCollectFlag(),
                conditions.getDistanceFlag(),
                conditions.getRelevantQuotationFlag(),start,limit);
        Integer count = busSupplierDao.queryCountByConditions(ids,conditions.getProvinceId(),
                conditions.getCityId(),conditions.getRelevantQuotationFlag());
        PageUtils pageutils = new PageUtils(list,count,limit,page);
        return pageutils;
    }

}