package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdSceneProjectEntity;
import com.yitongyin.modules.mp.View.ProductInfo;
import com.yitongyin.modules.mp.View.SceneProject;
import com.yitongyin.modules.mp.View.SceneProjectSearch;

import java.util.List;
import java.util.Map;

/**
 * 场景主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
public interface AdSceneProjectService extends IService<AdSceneProjectEntity> {

    /**
     * 根据条件分页查询场景
     * @param params
     * @return
     */
    PageUtils queryPageByConditions(Map<String, Object> params, SceneProjectSearch search);

    /**
     * 根据整案ID获取排序最前的场景
     * @param smId
     * @return
     */
    String getUrlOrderOneBySmId(Long smId);

    /**
     * 获取详情
     * @param id
     * @return
     */
    SceneProject getInfoById(Long id,Long merchantId);
    /**
     * 获取某个整案下的场景数量
     * @param smId
     * @return
     */
     Integer getCountBySmId(Long smId);

    /**
     * 根据分类id获取相似产品
     * @param params
     * @param typeId
     * @return
     */
    PageUtils getProListByType(Map<String,Object> params,Long typeId,Long merchantId);
}

