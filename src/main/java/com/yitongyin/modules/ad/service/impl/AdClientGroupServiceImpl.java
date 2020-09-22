package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdClientGroupDao;
import com.yitongyin.modules.ad.entity.AdClientGroupEntity;
import com.yitongyin.modules.ad.entity.AdGroupEntity;
import com.yitongyin.modules.ad.form.AdClientGroupChange;
import com.yitongyin.modules.ad.service.AdClientGroupService;
import com.yitongyin.modules.ad.service.AdGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("adClientGroupService")
public class AdClientGroupServiceImpl extends ServiceImpl<AdClientGroupDao, AdClientGroupEntity> implements AdClientGroupService {

    @Autowired
    AdGroupService adGroupService;
    @Autowired
    AdClientGroupDao adClientGroupDao;
    @Override
    public AdClientGroupEntity getOneByGroupId(Integer groupId) {
        QueryWrapper<AdClientGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<AdClientGroupEntity> getListByGroupIds(List<Integer> groupIds) {
        QueryWrapper<AdClientGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("group_id",groupIds);
        return this.list(queryWrapper);
    }

    @Override
    public AdClientGroupEntity getOneByClientIdAndGroupIds(Long clientId, Long merchantId) {
        return adClientGroupDao.getOneAndIsShowPriceByClientIdAndMerId(clientId,merchantId);
    }

    @Override
    public boolean delByClientIdAndGroupIds(List<AdClientGroupChange> list) {
        List<Long> clientGroupIds=list.stream().map(e->e.getCliGroupId()).collect(Collectors.toList());
        if(this.removeByIds(clientGroupIds)){
            List<AdClientGroupEntity> clientGroupEntities = list.stream().map(e->{
                AdClientGroupEntity entity = new AdClientGroupEntity();
                entity.setClientId(e.getClientId());
                entity.setGroupId(e.getDefGroupId());
                return entity;
            }).collect(Collectors.toList());
            return this.saveBatch(clientGroupEntities);
        }
        return false;
    }

    @Override
    public boolean addByClientIdAndGroupIds(List<AdClientGroupChange> list) {
        List<Long> clientGroupIds=list.stream().map(e->e.getCliGroupId()).collect(Collectors.toList());
        if(this.removeByIds(clientGroupIds)){
            List<AdClientGroupEntity> clientGroupEntities = list.stream().map(e->{
                AdClientGroupEntity entity = new AdClientGroupEntity();
                entity.setClientId(e.getClientId());
                entity.setGroupId(e.getCurGroupId());
                return entity;
            }).collect(Collectors.toList());
            return this.saveBatch(clientGroupEntities);
        }
        return false;
    }

    @Override
    public AdClientGroupEntity getGroupIdByClientIdAndMerchantId(Long clientId, Long merchantId) {
        return adClientGroupDao.getOneAndIsShowPriceByClientIdAndMerId(clientId,merchantId);
    }

    @Override
    public List<Long> getMerchantIdsByClientIdAndGroup(Long clientId) {
        return adClientGroupDao.getMerchantIdsByClientId(clientId);
    }

}