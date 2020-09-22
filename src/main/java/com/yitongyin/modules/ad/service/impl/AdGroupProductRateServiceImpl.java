package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdGroupProductRateDao;
import com.yitongyin.modules.ad.entity.AdClientGroupEntity;
import com.yitongyin.modules.ad.entity.AdGroupEntity;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.service.AdClientGroupService;
import com.yitongyin.modules.ad.service.AdGroupProductRateService;
import com.yitongyin.modules.ad.service.AdGroupService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service("adGroupProductRateService")
public class AdGroupProductRateServiceImpl extends ServiceImpl<AdGroupProductRateDao, AdGroupProductRateEntity> implements AdGroupProductRateService {

    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private AdGroupService adGroupService;
    @Autowired
    private AdClientGroupService adClientGroupService;


    @Override
    public boolean saveListByAdGroupAndMerchantId(AdGroupEntity adGroup, Long merchantId) {
        QueryWrapper<AdGroupProductRateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",adGroup.getGroupid());
        if(this.getOne(queryWrapper)!=null){
            UpdateWrapper<AdGroupProductRateEntity> updWrapper = new UpdateWrapper<>();
            updWrapper.eq("group_id",adGroup.getGroupid());
            updWrapper.set("price_rate",adGroup.getPriceRate());
            return this.update(updWrapper);
        }

        List<AdMerchantProductEntity> list=adMerchantProductService.getAllShowList(merchantId);
        List<AdGroupProductRateEntity> rateEntities =list.stream().map(e->{
            AdGroupProductRateEntity rateEntity = new AdGroupProductRateEntity();
            rateEntity.setGroupId(adGroup.getGroupid());
            rateEntity.setMerchantProductId(e.getId());
            rateEntity.setPriceRate(adGroup.getPriceRate());
            return rateEntity;
        }).collect(Collectors.toList());
        return this.saveBatch(rateEntities);
    }

    @Transactional
    @Override
    public boolean updRateByProId(List<AdGroupProductRateEntity> list) {
        if(list==null||list.size()==0) return true;
        for (AdGroupProductRateEntity entity: list) {
            UpdateWrapper<AdGroupProductRateEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("price_rate",entity.getPriceRate());
            updateWrapper.eq("group_id",entity.getGroupId());
            updateWrapper.eq("merchant_product_id",entity.getMerchantProductId());
            this.update(updateWrapper);
        }
        return true;
    }

    @Override
    public AdGroupProductRateEntity getRateByClientIdAndMerchantProcutId(Long clientId, Long merProductId, Long merchantId) {
        AdClientGroupEntity adClientGroupEntity= adClientGroupService.getGroupIdByClientIdAndMerchantId(clientId,merchantId);
        if(adClientGroupEntity==null){
            return null;
        }
        if(adClientGroupEntity.getShowPrice()==0){
            AdGroupProductRateEntity groupProductRateEntity = new AdGroupProductRateEntity();
            groupProductRateEntity.setIsShow(0);
            return groupProductRateEntity;
        }
        QueryWrapper<AdGroupProductRateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",adClientGroupEntity.getGroupId());
        queryWrapper.eq("merchant_product_id",merProductId);
        AdGroupProductRateEntity rateEntity =this.getOne(queryWrapper);
        if(rateEntity!=null){
            rateEntity.setIsShow(1);
        }
        return rateEntity;
    }

    @Override
    public AdGroupProductRateEntity getDefRateByClientIdAndMerchantProcutId(Long merProductId, Long merchantId) {
        AdGroupEntity groupEntity = adGroupService.getVisitorByMerchantId(merchantId);
        if(groupEntity==null){
            return null;
        }
        if(groupEntity.getShowPrice()==0){
            AdGroupProductRateEntity groupProductRateEntity = new AdGroupProductRateEntity();
            groupProductRateEntity.setIsShow(0);
            return groupProductRateEntity;
        }
        QueryWrapper<AdGroupProductRateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupEntity.getGroupid());
        queryWrapper.eq("merchant_product_id",merProductId);
        AdGroupProductRateEntity rateEntity =this.getOne(queryWrapper);
        if(rateEntity!=null){
            rateEntity.setIsShow(1);
        }
        return rateEntity;
    }

    @Override
    public AdGroupProductRateEntity getRateByGroupIdAndMerchantProcutId(Long merProductId, Long groupId) {
        QueryWrapper<AdGroupProductRateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        queryWrapper.eq("merchant_product_id",merProductId);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean delByGroupId(Integer groupId) {
        QueryWrapper<AdGroupProductRateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        return this.remove(queryWrapper);
    }
}