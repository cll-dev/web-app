package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantProductHitsEntity;

/**
 * 商户产品点击量
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-05 17:44:15
 */
public interface AdMerchantProductHitsService extends IService<AdMerchantProductHitsEntity> {
    /**
     * 根据商户产品ID修改+1或增加=1当天商户产品点击量
     * @param entity
     * @return
     */
    Boolean saveOrUpdByToday(AdMerchantProductHitsEntity entity);
}

