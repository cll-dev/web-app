package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.mp.View.ProductInfo;
import com.yitongyin.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商户产品表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 11:30:20
 */
@Mapper
public interface AdMerchantProductDao extends BaseMapper<AdMerchantProductEntity> {
    //根据后台分类获取商户下产品按点击量排序(分页)
    @Select("SELECT a.id,a.product_id,b.productName,b.ossId,b.productDescr as productDes FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId  WHERE a.merchant_id=#{merchantId} AND a.service_type_id=#{serviceTypeId}\n" +
            "AND a.mp_show=1 AND a.publish_able=1 ORDER BY b.orderNo ASC limit #{start},#{limit}")
    List<AdMerchantProductEntity> getPopularList(Long merchantId, Long serviceTypeId,Integer start,Integer limit);
    //根据后台分类获取商户下产品按点击量排序(总数)
    @Select("SELECT COUNT(*) FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId  WHERE a.merchant_id=#{merchantId} AND a.service_type_id=#{serviceTypeId}\n" +
            "AND a.mp_show=1 AND a.publish_able=1 ")
    Integer getCountPopularList(Long merchantId, Long serviceTypeId);
    //根据后台多个分类ID获取商户下对应的产品按点击量排序(分页)
    @Select({
            "<script>",
            "SELECT a.id,a.product_id,b.productName,b.ossId,b.productDescr as productDes",
            "FROM ad_merchant_product a",
            "LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.service_type_id in ",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "AND a.mp_show=1 AND a.publish_able=1 ORDER BY b.orderNo ASC  limit #{start},#{limit}",
            "</script>"
    })
    List<AdMerchantProductEntity> getPopularListByParentType(Long merchantId, @Param("ids") List<Object> ids,Integer start,Integer limit);
    //根据后台多个分类ID获取商户下对应的产品按点击量排序(总数)
    @Select({
            "<script>",
            "SELECT COUNT(*)",
            "FROM ad_merchant_product a",
            "LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.service_type_id in ",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "AND a.mp_show=1 AND a.publish_able=1",
            "</script>"
    })
    Integer getCountPopularListByParentType(Long merchantId, @Param("ids") List<Object> ids);
    @Select("SELECT a.id,b.productName,b.ossId,b.productDescr FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId  WHERE a.merchant_id=#{merchantId} AND a.service_type_id=#{serviceTypeId}\n" +
            "AND a.mp_show=1 AND a.publish_able=1")
    List<AdMerchantProductEntity> getPopularListNoHits(Long merchantId, Long serviceTypeId);
    @Select("SELECT a.*,b.* FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId  WHERE a.merchant_id=#{merchantId} AND a.mp_show=1 AND a.publish_able=1 AND b.tags LIKE\"%#{tags}%\"\n")
    List<AdMerchantProductEntity> getListByTags(Long merchantId, String tags);
    @Select("SELECT a.*,b.* FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId  WHERE a.merchant_id=#{merchantId} AND a.publish_able=1 AND a.mp_show=1")
    List<AdMerchantProductEntity> getListAllByMerchantId(Long merchantId);
    @Select("SELECT b.productContent,b.productName,b.ossId,a.service_type_id FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id =b.productId WHERE a.id=#{id}  AND a.publish_able=1")
    AdMerchantProductEntity getInfoById(Long id);
    @Select("SELECT a.id,b.ossId FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id =b.productId WHERE a.service_type_id=#{typeId} and merchant_id=#{merchantId} AND a.publish_able=1 AND a.id<>#{proId} limit 3")
    List<AdMerchantProductEntity> getBrothers(Long merchantId,Long typeId,Long proId);
    @Select("SELECT a.id,b.ossId FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id =b.productId WHERE a.service_type_id=#{typeId} and merchant_id=#{merchantId} AND a.publish_able=1 AND a.mp_show=1 AND a.id <>#{proId}")
    List<AdMerchantProductEntity> getMpBrothers(Long merchantId,Long typeId,Long proId);
    @Select("SELECT a.hit_count,b.product_id FROM ad_merchant_product_hits a LEFT JOIN ad_merchant_product b on a.merchant_product_id = b.id WHERE a.merchant_id=#{merchantId} AND a.hit_date BETWEEN #{startTime} and #{endTime} ")
    List<AdMerchantProductEntity> getHotsByTime(Long merchantId,String startTime,String endTime);
    @Select("SELECT a.hit_count,b.product_id FROM ad_merchant_product_hits a LEFT JOIN ad_merchant_product b on a.merchant_product_id = b.id WHERE a.merchant_id=#{merchantId}")
    List<AdMerchantProductEntity> getHotsAll(Long merchantId);
    @Select({
            "<script>",
            "select",
            "a.*,b.productName,b.ossId,b.supplier_spec_conf",
            "FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.publish_able=1",
            " AND a.product_id in ",
            "<foreach collection='proIds' item='proId' open='(' separator=',' close=')'>",
            "#{proId}",
            "</foreach>",
            "<when test='typeId!=null'>",
            " AND a.service_type_id=#{typeId}",
            "</when>",
            "<when test='name!=null'>",
            " AND (b.tags like CONCAT('%',#{name},'%') or b.productName like CONCAT('%',#{name},'%'))",
            "</when>",
            "<when test='status!=null'>",
            " AND a.mp_show=#{status}",
            "</when>",
            "ORDER BY b.orderNo ASC",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<AdMerchantProductEntity> getListByTypeAndName(Long merchantId,Long typeId,String name,Integer status,
                                                       @Param("proIds")List<Long> proIds,Integer start,Integer limit);
    @Select({
            "<script>",
            "select",
            "count(*)",
            "FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.publish_able=1",
            " AND a.product_id in ",
            "<foreach collection='proIds' item='proId' open='(' separator=',' close=')'>",
            "#{proId}",
            "</foreach>",
            "<when test='typeId!=null'>",
            " And a.service_type_id=#{typeId}",
            "</when>",
            "<when test='name!=null'>",
            " AND (b.tags like CONCAT('%',#{name},'%') or b.productName like CONCAT('%',#{name},'%'))",
            "</when>",
            "<when test='status!=null'>",
            " AND a.mp_show=#{status}",
            "</when>",
            "</script>"
    })
    Integer getCountByTypeAndName(Long merchantId,Long typeId,String name,Integer status,@Param("proIds")List<Long> proIds);
    @Select({
            "<script>",
            "select",
            "a.*,b.productName,b.ossId",
            "FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.publish_able=1",
            "AND a.mp_show=1",
            " AND a.product_id in ",
            "<foreach collection='proIds' item='proId' open='(' separator=',' close=')'>",
            "#{proId}",
            "</foreach>",
            "<when test='lowPrice!=null and lowDay==null'>",
            "AND a.product_id in(SELECT product_id FROM ad_merchant_spec_group WHERE merchant_id =#{merchantId} " ,
            "AND price BETWEEN #{lowPrice} and #{highPrice} )",
            "</when>",
            "<when test='lowDay!=null and lowPrice==null'>",
            "AND a.product_id in(SELECT product_id FROM ad_merchant_spec_group WHERE merchant_id =#{merchantId} " ,
            "AND delivery_day BETWEEN #{lowDay} and #{highDay} )",
            "</when>",
            "<when test='lowDay!=null and lowPrice!=null'>",
            "AND a.product_id in(SELECT product_id FROM ad_merchant_spec_group WHERE merchant_id =#{merchantId} " ,
            "AND delivery_day BETWEEN #{lowDay} and #{highDay} AND price BETWEEN #{lowPrice} and #{highPrice})",
            "</when>",
            "<when test='typeId!=null'>",
            " AND a.service_type_id=#{typeId}",
            "</when>",
            "<when test='tags!=null'>",
            " AND (b.tags like CONCAT('%',#{tags},'%') or b.productName like CONCAT('%',#{tags},'%'))",
            "</when>",
            "<choose>",
            "<when test='hits!=null'>",
            " ORDER BY a.hits Desc",
            "</when>",
            "<otherwise>ORDER BY b.orderNo ASC</otherwise>",
            "</choose>",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<AdMerchantProductEntity> getMpListByConditions(Long merchantId,Long typeId,String tags,
                        Double lowPrice,Double highPrice,Integer lowDay,Integer highDay,Integer hits,
                                                        @Param("proIds")List<Long>proIds,Integer start,Integer limit);
    @Select({
            "<script>",
            "select",
            "COUNT(*)",
            "FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.publish_able=1",
            "AND a.mp_show=1",
            "<when test='lowPrice!=null and lowDay==null'>",
            "AND a.product_id in(SELECT product_id FROM ad_merchant_spec_group WHERE merchant_id =#{merchantId} " ,
            "AND price BETWEEN #{lowPrice} and #{highPrice} )",
            "</when>",
            "<when test='lowDay!=null and lowPrice==null'>",
            "AND a.product_id in(SELECT product_id FROM ad_merchant_spec_group WHERE merchant_id =#{merchantId} " ,
            "AND delivery_day BETWEEN #{lowDay} and #{highDay} )",
            "</when>",
            "<when test='lowDay!=null and lowPrice!=null'>",
            "AND a.product_id in(SELECT product_id FROM ad_merchant_spec_group WHERE merchant_id =#{merchantId} " ,
            "AND delivery_day BETWEEN #{lowDay} and #{highDay} AND price BETWEEN #{lowPrice} and #{highPrice})",
            "</when>",
            "<when test='typeId!=null'>",
            " AND a.service_type_id=#{typeId}",
            "</when>",
            "<when test='tags!=null'>",
            " AND (b.tags like CONCAT('%',#{tags},'%') or b.productName like CONCAT('%',#{tags},'%'))",
            "</when>",
            "</script>"
    })
    Integer getCountMpListByConditions(Long merchantId,Long typeId,String tags,
                                                        Double lowPrice,Double highPrice,Integer lowDay,Integer highDay,@Param("proIds")List<Long>proIds);
    @Select({
            "<script>",
            "SELECT a.*,b.productName,b.ossId,b.supplier_spec_conf FROM ad_merchant_product a",
            "LEFT JOIN bus_product b on a.product_id=b.productId",
            "WHERE a.merchant_id=#{merchantId} AND a.publish_able=1",
            " AND a.product_id in ",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "ORDER BY b.orderNo ASC",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<AdMerchantProductEntity> getListAllByMerchantIdAndProIds(Long merchantId,List<Long> ids,Integer start,Integer limit);
    @Select("SELECT a.*,b.productName,b.supplier_spec_conf,b.ossId,b.orderNo as orderNumber,b.tags FROM ad_merchant_product a LEFT JOIN bus_product b ON a.product_id = b.productId")
    List<EsProduct> getAllEsProduct();
    @Select("SELECT a.id, b.productName,b.productDescr,c.url as coverUrl FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId LEFT JOIN ad_oss c on b.ossId=c.id WHERE a.service_type_id=#{typeId} and a.merchant_id=#{merchantId} limit #{start},#{limit}")
    List<ProductInfo> getListByTypeId(Long typeId,Long merchantId,Integer start,Integer limit);
    @Select("SELECT count(*) FROM ad_merchant_product a LEFT JOIN bus_product b on a.product_id=b.productId LEFT JOIN ad_oss c on b.ossId=c.id WHERE a.service_type_id=#{typeId} and a.merchant_id=#{merchantId}")
    Integer getCountByTypeId(Long typeId,Long merchantId);
    @Select("SELECT  * FROM ad_merchant_product WHERE merchant_id=#{merchantId} AND publish_able=1 AND mp_show=1 limit 10")
    List<AdMerchantProductEntity> mpGetIdsShowListLimit(Long merchantId);
}

