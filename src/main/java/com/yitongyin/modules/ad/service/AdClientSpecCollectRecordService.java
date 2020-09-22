package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdClientSpecCollectRecordEntity;

import java.util.Map;

/**
 * 客户产品方案收藏记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:04
 */
public interface AdClientSpecCollectRecordService extends IService<AdClientSpecCollectRecordEntity> {

    PageUtils queryPage(Map<String, Object> params, Long clientId, Long merchantId);

    /**
     * 判断是否已经收藏过
     * @param entity
     * @return
     */
    AdClientSpecCollectRecordEntity getByEntity(AdClientSpecCollectRecordEntity entity);
}

