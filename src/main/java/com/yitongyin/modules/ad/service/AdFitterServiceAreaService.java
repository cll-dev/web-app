package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdFitterServiceAreaEntity;

import java.util.Map;

/**
 * 安装工对应的安装范围
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-30 09:23:07
 */
public interface AdFitterServiceAreaService extends IService<AdFitterServiceAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

