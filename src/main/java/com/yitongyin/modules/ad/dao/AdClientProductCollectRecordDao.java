package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientProductCollectRecordEntity;
import com.yitongyin.modules.mp.View.ProductPriceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户产品收藏记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@Mapper
public interface AdClientProductCollectRecordDao extends BaseMapper<AdClientProductCollectRecordEntity> {

    @Select("SELECT a.*,b.product_id,c.productName,d.url FROM ad_client_product_collect_record a LEFT JOIN ad_merchant_product b on a.merchant_product_id=b.id LEFT JOIN bus_product c on b.product_id=c.productId LEFT JOIN \n" +
            "ad_oss d on c.ossId=d.id where a.client_id=#{clientId} and b.merchant_id=#{merchantId} LIMIT #{start},#{limit}")
    List<AdClientProductCollectRecordEntity> getListByClientId(Long clientId, Long merchantId, Integer start, Integer limit);
    @Select("SELECT count(*)  FROM ad_client_product_collect_record a LEFT JOIN ad_merchant_product b on a.merchant_product_id=b.id where a.client_id=#{clientId}\n" +
            "and b.merchant_id=#{merchantId}")
    Integer getCountByClientId(Long clientId, Long merchantId);
    @Select("SELECT a.id,a.product_id, b.productName,c.url FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId LEFT JOIN  ad_oss c on b.ossId=c.id WHERE \n" +
            " a.merchant_id=#{merchantId}\n" +
            "                     AND a.id not in(SELECT merchant_product_id FROM ad_client_product_collect_record WHERE client_id=#{clientId}) AND a.publish_able=1 AND a.mp_show=1 ORDER BY a.hits DESC limit #{start},#{limit}")
    List<ProductPriceInfo> getSimilarListByMerchantId(Long clientId,Long merchantId,Integer start,Integer limit);
    @Select("SELECT COUNT(*) FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId LEFT JOIN  ad_oss c on b.ossId=c.id WHERE \n" +
            " a.merchant_id=#{merchantId}\n" +
            "                     AND a.id not in(SELECT merchant_product_id FROM ad_client_product_collect_record WHERE client_id=#{clientId}) AND a.publish_able=1 AND a.mp_show=1")
    Integer getCountSimilarListByMerchantId(Long clientId,Long merchantId);


}
