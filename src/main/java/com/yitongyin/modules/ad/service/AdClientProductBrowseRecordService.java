package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdClientProductBrowseRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 客户产品浏览记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
public interface AdClientProductBrowseRecordService extends IService<AdClientProductBrowseRecordEntity> {
    /**
     * 获取用户在当前商户下的产品浏览记录
     * @param clientId
     * @param merchantId
     * @return
     */
    List<AdClientProductBrowseRecordEntity> getListByClientIdAndMerchantId(Long clientId, Long merchantId);
    /**
     * 获取用户在当前商户下的产品浏览记录
     * @param clientId
     * @param merchantId
     * @return
     */
    List<AdClientProductBrowseRecordEntity> getListPriceByClientIdAndMerchantId(Long clientId, Long merchantId,String startTime,String endTime);

    /**
     * 添加足迹
     * @param entity
     * @return
     */
    boolean addOneByTime(AdClientProductBrowseRecordEntity entity);

    /**
     * 根据用户Id和商户id获取
     * @param clientId
     * @param merchantId
     * @return
     */
    AdClientProductBrowseRecordEntity getMaxTimeByClientId(Long clientId, Long merchantId);

    /**
     * 分页获取用户在当前商户下的产品浏览记录
     * @param params
     * @param clientId
     * @param merchantId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, Long clientId, Long merchantId);
}

