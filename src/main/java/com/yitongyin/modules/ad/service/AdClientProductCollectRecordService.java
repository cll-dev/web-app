package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdClientProductCollectRecordEntity;

import java.util.Map;

/**
 * 客户产品收藏记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
public interface AdClientProductCollectRecordService extends IService<AdClientProductCollectRecordEntity> {
    /**
     * 产品收藏记录表
     *
     * @param params
     * @param clientId
     * @param merchantId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, Long clientId, Long merchantId);

    /**
     * 收藏产品
     */
    boolean addOneByTime(AdClientProductCollectRecordEntity entity);

    /**
     * 取消收藏产品
     * @param entity
     * @return
     */
    boolean delOne(AdClientProductCollectRecordEntity entity);
    /**
     * 获取产品收藏状态
     * @param entity
     * @return
     */
    boolean getStaOne(AdClientProductCollectRecordEntity entity);

}