package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientSpecCollectRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户产品方案收藏记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:04
 */
@Mapper
public interface AdClientSpecCollectRecordDao extends BaseMapper<AdClientSpecCollectRecordEntity> {
    @Select("SELECT a.* FROM ad_client_spec_collect_record a LEFT JOIN ad_merchant_product b on a.merchant_product_id=b.id\n" +
            " where a.client_id=#{clientId} and b.merchant_id=#{merchantId} LIMIT #{start},#{limit}")
    List<AdClientSpecCollectRecordEntity> getListByClientId(Long clientId, Long merchantId, Integer start, Integer limit);
    @Select("SELECT count(*)  FROM ad_client_spec_collect_record a LEFT JOIN ad_merchant_product b on a.merchant_product_id=b.id where a.client_id=#{clientId}\n" +
            "and b.merchant_id=#{merchantId}")
    Integer getCountByClientId(Long clientId, Long merchantId);

}
