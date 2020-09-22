package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.yitongyin.modules.ad.dao.AdUserDao;

import com.yitongyin.modules.ad.entity.AdRoleEntity;
import com.yitongyin.modules.ad.entity.AdUserEntity;

import com.yitongyin.modules.ad.entity.AdUserRoleEntity;
import com.yitongyin.modules.ad.service.AdRoleService;
import com.yitongyin.modules.ad.service.AdUserRoleService;
import com.yitongyin.modules.ad.service.AdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserDao, AdUserEntity> implements AdUserService {
    @Autowired
    AdUserRoleService adUserRoleService;
    @Autowired
    AdRoleService adRoleService;

    @Override
    public AdUserEntity queryByUserName(String username) {

        QueryWrapper<AdUserEntity>  query = new QueryWrapper<>();
        query.eq("mobile",username);
        return this.getOne(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userReg(AdUserEntity userEntity) {
        userEntity.setCreateTime(new Date());
            try {
                this.save(userEntity);
                AdUserRoleEntity userAndRole= new AdUserRoleEntity();
                userAndRole.setUserId(userEntity.getUserId());
                AdRoleEntity roleEntity = adRoleService.getByName("普通商户");
                userAndRole.setRoleId(roleEntity==null?null:roleEntity.getRoleid());
                adUserRoleService.insert(userAndRole);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePwd(AdUserEntity userEntity) {
        userEntity.setUpdateTime(new Date());
        UpdateWrapper<AdUserEntity> update= new UpdateWrapper<>();
        update.eq("mobile",userEntity.getMobile()
        );
        this.update(userEntity,update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePhone(AdUserEntity userEntity) {
        userEntity.setUpdateTime(new Date());
        UpdateWrapper<AdUserEntity> update= new UpdateWrapper<>();
        update.eq("userId",userEntity.getUserId());
        this.update(userEntity,update);
    }

    @Override
    public AdUserEntity queryBuOpenId(String openId) {
        QueryWrapper<AdUserEntity>  query = new QueryWrapper<>();
        query.eq("open_id",openId);
        return this.getOne(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindOpenId(String mobile,String openId) {
        AdUserEntity userEntity=new AdUserEntity();
        userEntity.setOpenId(openId);
        userEntity.setUpdateTime(new Date());
        UpdateWrapper<AdUserEntity> update= new UpdateWrapper<>();
        update.eq("mobile",mobile);
        this.update(userEntity,update);
    }

    @Override
    public void unbindOpenId(long userId) {
        AdUserEntity userEntity=new AdUserEntity();
        userEntity.setUserId(userId);
        userEntity.setUpdateTime(new Date());
        UpdateWrapper<AdUserEntity> update= new UpdateWrapper<>();
        update.eq("userId",userId);
        update.set("open_id",null);
        this.update(userEntity,update);
    }

    @Override
    public AdUserEntity getOneByEmail(String email) {
        QueryWrapper<AdUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        return this.getOne(queryWrapper);
    }
}
