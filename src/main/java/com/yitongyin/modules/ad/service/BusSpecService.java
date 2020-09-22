package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusSpecEntity;

import java.util.List;
import java.util.Map;

/**
 * 规格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 14:13:42
 */
public interface BusSpecService extends IService<BusSpecEntity> {
    /**
     * 分页获取
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取所有key及名称
     * @return
     */
    List<BusSpecEntity> getKeyValues();
}

