package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdSceneMerchantEntity;
import com.yitongyin.modules.mp.View.SceneMerchantSearch;
import com.yitongyin.modules.mp.View.SceneMerchantView;

import java.util.Map;

/**
 * 场景商家表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
public interface AdSceneMerchantService extends IService<AdSceneMerchantEntity> {
    /**
     * 分页获取
     * @param params
     * @param search
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, SceneMerchantSearch search);

    /**
     * 获取详情
     * @param params
     * @return
     */
    SceneMerchantView getInfoById(Map<String, Object> params);


}

