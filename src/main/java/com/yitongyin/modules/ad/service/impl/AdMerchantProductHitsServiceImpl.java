package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdMerchantProductHitsDao;
import com.yitongyin.modules.ad.entity.AdMerchantProductHitsEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductHitsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("adMerchantProductHitsService")
public class AdMerchantProductHitsServiceImpl extends ServiceImpl<AdMerchantProductHitsDao, AdMerchantProductHitsEntity> implements AdMerchantProductHitsService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveOrUpdByToday(AdMerchantProductHitsEntity entity) {
        QueryWrapper<AdMerchantProductHitsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id",entity.getMerchantId());
        queryWrapper.eq("merchant_product_id",entity.getMerchantProductId());
        queryWrapper.eq("hit_date",entity.getDate());
        AdMerchantProductHitsEntity searchEntity=this.getOne(queryWrapper);
        if(searchEntity==null){
            entity.setHitCount(1);
           return this.save(entity);
        }else {
            entity.setHitCount(searchEntity.getHitCount()+1);
            UpdateWrapper<AdMerchantProductHitsEntity> update = new UpdateWrapper<>();
            update.eq("id", searchEntity.getId());
            return this.update(entity,update);
        }
    }
}