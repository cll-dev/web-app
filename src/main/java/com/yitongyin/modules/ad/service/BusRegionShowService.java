package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusRegionShowEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 13:31:11
 */
public interface BusRegionShowService extends IService<BusRegionShowEntity> {
    /**
     * 分页获取广告素材
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}

