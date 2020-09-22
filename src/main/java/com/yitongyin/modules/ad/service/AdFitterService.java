package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdFitterEntity;

import java.util.List;
import java.util.Map;

/**
 * 安装工表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-30 09:23:07
 */
public interface AdFitterService extends IService<AdFitterEntity> {

    PageUtils queryPage(Map<String, Object> params, Integer county);
    PageUtils queryPage(Map<String, Object> params);
}

