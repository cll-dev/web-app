package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdRoleDao;
import com.yitongyin.modules.ad.entity.AdRoleEntity;
import com.yitongyin.modules.ad.service.AdRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdRoleServiceImpl extends ServiceImpl<AdRoleDao, AdRoleEntity> implements AdRoleService {

    @Override
    public List<AdRoleEntity> getAllRoles() {
        return this.list(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(AdRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);
    }

    @Override
    public AdRoleEntity getByName(String name) {
        QueryWrapper<AdRoleEntity> query = new QueryWrapper<>();
        query.eq("role_name",name);
        return this.getOne(query);
    }
}
