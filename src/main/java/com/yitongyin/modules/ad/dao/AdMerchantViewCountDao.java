package com.yitongyin.modules.ad.dao;

import com.yitongyin.modules.ad.entity.AdMerchantViewCountEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商户访客数量表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:05:24
 */
@Mapper
public interface AdMerchantViewCountDao extends BaseMapper<AdMerchantViewCountEntity> {
	
}
