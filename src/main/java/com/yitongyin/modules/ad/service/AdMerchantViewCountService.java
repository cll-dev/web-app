package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantViewCountEntity;

import java.util.List;


/**
 * 商户访客数量表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:05:24
 */
public interface AdMerchantViewCountService extends IService<AdMerchantViewCountEntity> {
    /**
     * 获取商户当天访客数
     * @param merchantId
     * @return
     */
    AdMerchantViewCountEntity findLastCount(long merchantId);

    /**
     * 根据开始时间和结束时间获取商户一周访客数
     * @param merchantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<AdMerchantViewCountEntity> findWeekCount(long merchantId, String startTime, String endTime);

    /**
     * 根据IP增加或修改商户访客数据
     * @param entity
     * @return
     */
    Boolean saveOrUpdByIpAndToday(AdMerchantViewCountEntity entity);

    /**
     * 根据商户ID获取商户访客viewId List
     * @param merchantId
     * @return
     */
    List<Object> findByMerId(Long merchantId);

}

