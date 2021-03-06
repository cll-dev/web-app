package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantViewIpEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商户访客的ip表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-06 17:46:12
 */
@Mapper
public interface AdMerchantViewIpDao extends BaseMapper<AdMerchantViewIpEntity> {
	
}
