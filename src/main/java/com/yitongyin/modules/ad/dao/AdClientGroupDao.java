package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户和客户组关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Mapper
public interface AdClientGroupDao extends BaseMapper<AdClientGroupEntity> {
    @Select("SELECT b.merchant_id FROM ad_client_group a LEFT JOIN ad_group b on a.group_id=b.groupId WHERE a.client_id=#{clientId} GROUP BY b.merchant_id")
    List<Long> getMerchantIdsByClientId(Long clientId);
    @Select("SELECT a.*,b.show_price FROM ad_client_group a LEFT JOIN ad_group b on a.group_id=b.groupId WHERE a.client_id=#{clientId} AND b.merchant_id=#{merchantId}")
    AdClientGroupEntity getOneAndIsShowPriceByClientIdAndMerId (Long clientId,Long merchantId);
}
