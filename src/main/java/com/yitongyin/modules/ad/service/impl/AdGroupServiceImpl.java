package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdGroupDao;
import com.yitongyin.modules.ad.entity.AdGroupEntity;
import com.yitongyin.modules.ad.service.AdGroupProductRateService;
import com.yitongyin.modules.ad.service.AdGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("adGroupService")
public class AdGroupServiceImpl extends ServiceImpl<AdGroupDao, AdGroupEntity> implements AdGroupService {
    @Autowired
    AdGroupDao adGroupDao;
    @Autowired
    AdGroupProductRateService adGroupProductRateService;

    @Override
    public List<AdGroupEntity> getClientByMerchantId(Long merchantId) {
        return adGroupDao.getClientByMerId(merchantId);
    }

    @Override
    public AdGroupEntity getOneByName(String name, Long merchantId) {
        QueryWrapper<AdGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        queryWrapper.eq("merchant_id",merchantId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<AdGroupEntity> getListByMerchantId(Long merchantId) {
        QueryWrapper<AdGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("groupId","name");
        queryWrapper.eq("merchant_id",merchantId).eq("join_client",1);
        return this.list(queryWrapper);
    }

    @Override
    public AdGroupEntity getDefaultByMerchantId(Long merchantId) {
        QueryWrapper<AdGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("groupId");
        queryWrapper.eq("merchant_id",merchantId).eq("join_client",1)
        .eq("default_group",1);
        return this.getOne(queryWrapper);
    }

    @Override
    public AdGroupEntity getVisitorByMerchantId(Long merchantId) {
        QueryWrapper<AdGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id",merchantId).eq("join_client",0)
                .eq("default_group",1);
        return this.getOne(queryWrapper);
    }

    @Transactional
    @Override
    public boolean deleteGroupAndProRateByGroupId(Integer groupId) {
        if(this.removeById(groupId)){
          return   adGroupProductRateService.delByGroupId(groupId);
        }
        return false;
    }

}