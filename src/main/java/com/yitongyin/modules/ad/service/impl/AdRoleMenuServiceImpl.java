package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdRoleMenuDao;
import com.yitongyin.modules.ad.entity.AdMenuEntity;
import com.yitongyin.modules.ad.entity.AdRoleMenuEntity;
import com.yitongyin.modules.ad.service.AdRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adRoleMenuService")
public class AdRoleMenuServiceImpl extends ServiceImpl<AdRoleMenuDao, AdRoleMenuEntity> implements AdRoleMenuService {

    @Autowired
    private AdRoleMenuDao adRoleMenuDao;

    @Override
    public List<AdMenuEntity> getMenuByRole(Long roleId) {
        return adRoleMenuDao.getMenuByRoleId(roleId);
    }
}