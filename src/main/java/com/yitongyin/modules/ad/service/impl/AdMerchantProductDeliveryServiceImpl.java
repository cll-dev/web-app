package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdMerchantProductDeliveryFeeDao;
import com.yitongyin.modules.ad.entity.AdMerchantProductDeliveryEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductDeliveryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("adMerchantProductDeliveryService")
public class AdMerchantProductDeliveryServiceImpl extends ServiceImpl<AdMerchantProductDeliveryFeeDao, AdMerchantProductDeliveryEntity> implements AdMerchantProductDeliveryService {


    @Override
    public List<AdMerchantProductDeliveryEntity> getByProId(Long merchantId, Long proId) {
        QueryWrapper<AdMerchantProductDeliveryEntity> query= new QueryWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",proId);
        return this.list(query);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updStatus(Long merchantId, Long merProId,Integer status) {
        UpdateWrapper<AdMerchantProductDeliveryEntity> query= new UpdateWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",merProId);
        query.set("show_able",status);
        return this.update(query);
    }

    @Override
    public AdMerchantProductDeliveryEntity getOneByProId(Long merchantId, Long proId) {
        QueryWrapper<AdMerchantProductDeliveryEntity> query= new QueryWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",proId);
        return this.getOne(query);
    }
}