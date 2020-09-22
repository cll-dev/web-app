package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMenuEntity;
import com.yitongyin.modules.ad.entity.AdRoleMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 13:40:32
 */
public interface AdRoleMenuService extends IService<AdRoleMenuEntity> {

    /**
     * 根据角色获取对应的菜单列表
     * @param roleId
     * @return
     */
    List<AdMenuEntity> getMenuByRole(Long roleId);
}

