package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdRoleEntity;

import java.util.List;

public interface AdRoleService extends IService<AdRoleEntity> {

    List<AdRoleEntity> getAllRoles();

    void insert(AdRoleEntity role);
    /**
     * 根据角色名称获取entity
     */
    AdRoleEntity getByName(String name);
}
