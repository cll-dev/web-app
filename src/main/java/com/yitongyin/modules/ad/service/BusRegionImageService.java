package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusRegionImageEntity;

import java.util.Map;

/**
 * 广告位对应的图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-26 14:43:01
 */
public interface BusRegionImageService extends IService<BusRegionImageEntity> {
    /**
     * 分页获取广告素材库
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 根据分类分页获取广告素材库
     * @param params
     * @return
     */
    PageUtils queryPageByType(Map<String, Object> params,Long serviceType);
}

