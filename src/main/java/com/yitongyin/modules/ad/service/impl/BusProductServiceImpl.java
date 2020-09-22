package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.BusProductDao;
import com.yitongyin.modules.ad.entity.AdMerchantCaseEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.BusProductEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantCaseService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.BusProductService;
import com.yitongyin.modules.ad.service.BusSupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("busProductService")
public class BusProductServiceImpl extends ServiceImpl<BusProductDao, BusProductEntity> implements BusProductService {
    @Autowired
    AdMerchantProductService adMerchantProductService;
    @Autowired
    BusSupplierProductService busSupplierProductService;
    @Autowired
    AdMerchantCaseService adMerchantCaseService;
    @Override
    public Integer getCountByType(long type,Long merchantId) {
        QueryWrapper<AdMerchantProductEntity> query = new QueryWrapper<>();
        query.eq("service_type_id",type);
        query.eq("publish_able",1);
        query.eq("merchant_id",merchantId);
        return adMerchantProductService.count(query);
    }

    @Override
    public Integer getCountByChildTypes(List<Object> types,Long merchantId) {
        QueryWrapper<AdMerchantProductEntity> query = new QueryWrapper<>();
        query.eq("publish_able",1);
        query.eq("merchant_id",merchantId);
        query.in("service_type_id",types);
        return adMerchantProductService.count(query);
    }


    @Override
    public BusProductEntity getSomePropertyById(Long id) {
        QueryWrapper<BusProductEntity> query = new QueryWrapper<>();
        query.select("productName","productDescr","productContent","ossId","orderNo","tags","serviceTypeId","materialUrl","designFee","outlink");
        query.eq("productId",id);
        return this.getOne(query);
    }

    @Override
    public List<BusProductEntity> getNameByTypeId(Long serviceTypeId, TMerchantEntity merchantEntity,Integer state) {
        QueryWrapper<BusProductEntity> query = new QueryWrapper<>();
        query.select("productName","productId");
        query.eq("serviceTypeId",serviceTypeId);
        query.eq("`group`","system");
        query.eq("publishAble",1);
        List<BusProductEntity> list = this.list(query);
        if(list==null||list.size()==0){
            return list;
        }
        for (BusProductEntity productEntity: list) {
            if(state.equals(1)){
                productEntity.setSupCount(busSupplierProductService.getAreaListByProId(productEntity.getProductid(),
                        merchantEntity.getCounty()).size());
            }else{
                productEntity.setCaseCount(adMerchantCaseService.countByProId(productEntity.getProductid(),merchantEntity.getMerchantid()));
            }

        }
        return list;
    }


}