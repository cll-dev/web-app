package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdGroupEntity;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;

import java.util.List;

/**
 * 客户组产品价格倍数表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-08 17:40:57
 */
public interface AdGroupProductRateService extends IService<AdGroupProductRateEntity> {
    /**
     * 添加商户等级同时添加产品价格倍数数据
     * @param groupEntity
     * @param merchantId
     * @return
     */
    boolean saveListByAdGroupAndMerchantId(AdGroupEntity groupEntity, Long merchantId);

    /**
     * 根据groupId修改对应产品价格倍数
     * @param list
     * @return
     */
    boolean updRateByProId(List<AdGroupProductRateEntity> list);
    /**
     * 根据商户产品Id和用户Id获取对应产品价格倍数
     * @param clientId
     * @param merProductId
     * @param  merchantId
     * @return
     */
    AdGroupProductRateEntity getRateByClientIdAndMerchantProcutId(Long clientId, Long merProductId, Long merchantId);
    /**
     * 根据商户产品Id和用户Id获取对应产品价格倍数
     * @param merProductId
     * @param  merchantId
     * @return
     */
    AdGroupProductRateEntity getDefRateByClientIdAndMerchantProcutId(Long merProductId, Long merchantId);
    /**
     * 根据商户产品Id和用户Id获取对应产品价格倍数
     * @param merProductId
     * @param  groupId
     * @return
     */
    AdGroupProductRateEntity getRateByGroupIdAndMerchantProcutId(Long merProductId, Long groupId);
    boolean delByGroupId(Integer groupId);
}

