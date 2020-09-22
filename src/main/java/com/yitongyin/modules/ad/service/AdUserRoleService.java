package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yitongyin.modules.ad.entity.AdUserRoleEntity;

public interface AdUserRoleService  extends IService<AdUserRoleEntity> {

    void insert(AdUserRoleEntity userAndRole);
    AdUserRoleEntity getByUserId(Long userId);


}
