package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdSceneTypeEntity;

import java.util.List;

/**
 * 场景标签表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
public interface AdSceneTypeService extends IService<AdSceneTypeEntity> {
    /**
     * 获取位置下拉列表
     * @return
     */
    List<AdSceneTypeEntity> getPositionSearchList();

    /**
     * 获取空间下拉列表
     * @return
     */
    List<AdSceneTypeEntity> getSpaceSearchList();
}

