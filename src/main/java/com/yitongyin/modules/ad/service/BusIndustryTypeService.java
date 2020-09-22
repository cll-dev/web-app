package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusIndustryTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 行业分类表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
public interface BusIndustryTypeService extends IService<BusIndustryTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<BusIndustryTypeEntity> getListOrder();

}

