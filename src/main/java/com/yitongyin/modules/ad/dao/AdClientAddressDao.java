package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientAddressEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户收货地址表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@Mapper
public interface AdClientAddressDao extends BaseMapper<AdClientAddressEntity> {
	
}
