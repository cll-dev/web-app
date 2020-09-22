package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierCollectionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商户对应厂家的收藏表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
@Mapper
public interface AdMerchantSupplierCollectionDao extends BaseMapper<AdMerchantSupplierCollectionEntity> {
	
}
