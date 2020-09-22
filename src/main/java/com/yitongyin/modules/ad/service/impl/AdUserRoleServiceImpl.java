package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdUserRoleDao;
import com.yitongyin.modules.ad.entity.AdUserRoleEntity;
import com.yitongyin.modules.ad.service.AdUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdUserRoleServiceImpl extends ServiceImpl<AdUserRoleDao, AdUserRoleEntity> implements AdUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(AdUserRoleEntity userAndRole) {
       this.save(userAndRole);
    }

    @Override
    public AdUserRoleEntity getByUserId(Long userId) {
        QueryWrapper<AdUserRoleEntity> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        return this.getOne(query);
    }
}
