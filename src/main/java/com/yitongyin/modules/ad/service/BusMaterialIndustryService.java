package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusMaterialIndustryEntity;

import java.util.Map;

/**
 * 素材与行业对应关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
public interface BusMaterialIndustryService extends IService<BusMaterialIndustryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

