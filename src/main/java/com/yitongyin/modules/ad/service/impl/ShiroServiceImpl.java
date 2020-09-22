/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yitongyin.modules.ad.service.impl;

import com.yitongyin.common.utils.Constant;
import com.yitongyin.modules.ad.dao.AdMenuDao;
import com.yitongyin.modules.ad.dao.AdUserDao;
import com.yitongyin.modules.ad.dao.AdUserTokenDao;
import com.yitongyin.modules.ad.entity.AdMenuEntity;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.entity.AdUserTokenEntity;
import com.yitongyin.modules.ad.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private AdMenuDao adMenuDao;
    @Autowired
    private AdUserDao adUserDao;
    @Autowired
    private AdUserTokenDao adUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<AdMenuEntity> menuList = adMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(AdMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = adUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public AdUserTokenEntity queryByToken(String token) {
        return adUserTokenDao.queryByToken(token);
    }

    @Override
    public AdUserEntity queryUser(Long userId) {
        return adUserDao.selectById(userId);
    }
}
