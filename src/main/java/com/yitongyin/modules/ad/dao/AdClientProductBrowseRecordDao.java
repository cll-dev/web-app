package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientProductBrowseRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户产品浏览记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@Mapper
public interface AdClientProductBrowseRecordDao extends BaseMapper<AdClientProductBrowseRecordEntity> {
    @Select("SELECT a.*,b.product_id,c.productName as product_name,d.url FROM ad_client_product_browse_record a LEFT JOIN  ad_merchant_product b on a.merchant_product_id=b.id LEFT JOIN bus_product c on b.product_id=c.productId\n" +
            "LEFT JOIN ad_oss d on c.ossId=d.id WHERE a.client_id=#{clientId} AND a.merchant_id=#{merchantId} ORDER BY a.browse_date desc limit 3")
    List<AdClientProductBrowseRecordEntity> getListByClientIdAndMerchantId(Long clientId, Long merchantId);
    @Select("SELECT a.*,b.product_id,c.productName as product_name,d.url FROM ad_client_product_browse_record a LEFT JOIN  ad_merchant_product b on a.merchant_product_id=b.id LEFT JOIN bus_product c on b.product_id=c.productId\n" +
            "LEFT JOIN ad_oss d on c.ossId=d.id WHERE a.client_id=#{clientId} AND a.merchant_id=#{merchantId} ORDER BY a.browse_date desc limit #{start},#{limit}")
    List<AdClientProductBrowseRecordEntity> getPageListByClientIdAndMerchantId(Long clientId, Long merchantId,Integer start,Integer limit);
    @Select("SELECT count(*) FROM ad_client_product_browse_record a LEFT JOIN  ad_merchant_product b on a.merchant_product_id=b.id LEFT JOIN bus_product c on b.product_id=c.productId\n" +
            "LEFT JOIN ad_oss d on c.ossId=d.id WHERE a.client_id=#{clientId} AND a.merchant_id=#{merchantId} ORDER BY a.browse_date desc")
    Integer getCountByClientIdAndMerchantId(Long clientId, Long merchantId);
    @Select("SELECT a.*,b.product_id,c.productName as product_name,d.url FROM ad_client_product_browse_record a LEFT JOIN  ad_merchant_product b on a.merchant_product_id=b.id LEFT JOIN bus_product c on b.product_id=c.productId\n" +
            "LEFT JOIN ad_oss d on c.ossId=d.id WHERE a.client_id=#{clientId} AND a.merchant_id=#{merchantId} AND a.browse_date BETWEEN #{startTime} and #{endTime} ORDER BY a.browse_date desc")
    List<AdClientProductBrowseRecordEntity> getTimeListByClientIdAndMerchantId(Long clientId, Long merchantId,String startTime,String endTime);
}
