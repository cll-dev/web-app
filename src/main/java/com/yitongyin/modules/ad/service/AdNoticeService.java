package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdNoticeEntity;


import java.util.List;
import java.util.Map;


/**
 * 公告表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 12:59:42
 */
public interface AdNoticeService extends IService<AdNoticeEntity> {
    /**
     * 根据type获取前count条公告
     * @param type
     * @param count
     * @return
     */
    List<AdNoticeEntity>  findNewByType(Integer type,Integer count);
    /**
     * 根据type获取前count条公告
     * @param type
     * @param params
     * @return
     */
    PageUtils pageNewByType(Map<String, Object> params, Integer type);
}

