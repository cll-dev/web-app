package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantDynamicEntity;

import java.util.Map;

/**
 * 商户动态表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-01 09:14:38
 */
public interface AdMerchantDynamicService extends IService<AdMerchantDynamicEntity> {
    /**
     * 分页获取店铺动态
     * @param params
     * @param merchantId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params,Long merchantId);

    /**
     * 根据标题获取店铺动态
     * @param title
     * @return
     */
    AdMerchantDynamicEntity getByTitle(String title,Long merchantId);
}

