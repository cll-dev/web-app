package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantCaseEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商户案例表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 11:34:13
 */
@Mapper
public interface AdMerchantCaseDao extends BaseMapper<AdMerchantCaseEntity> {
    @Select({
            "<script>",
            "select",
            "a.*,c.productName",
            "FROM ad_merchant_case a",
            "LEFT JOIN bus_product c on a.product_id=c.productId",
            "WHERE a.merchant_id=#{merchantId}",
            "<when test='proIds!=null'>",
            "AND a.product_id in",
            "<foreach collection='proIds' item='proId' open='(' separator=',' close=')'>",
            "#{proId}",
            "</foreach>",
            "</when>",
            "<when test='name!=null'>",
            " AND (c.productName like CONCAT('%',#{name},'%') or a.content like CONCAT('%',#{name},'%'))",
            "</when>",
            "<when test='isHit'>",
            " ORDER BY hits desc",
            "</when>",
            "<when test='isDate'>",
            " ORDER BY create_time desc",
            "</when>",
            "<when test='isDate==null and isHit==null'>",
            " ORDER BY create_time desc",
            "</when>",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<AdMerchantCaseEntity> getListByConditions(Long merchantId,@Param("proIds")List<Long> proIds, String name,Boolean isHit,Boolean isDate, Integer start, Integer limit);
    @Select({
            "<script>",
            "select",
            "COUNT(*)",
            "FROM ad_merchant_case a",
            "LEFT JOIN bus_product c on a.product_id=c.productId",
            "WHERE a.merchant_id=#{merchantId}",
            "<when test='proIds!=null'>",
            "AND a.product_id in",
            "<foreach collection='proIds' item='proId' open='(' separator=',' close=')'>",
            "#{proId}",
            "</foreach>",
            "</when>",
            "<when test='name!=null'>",
            " AND c.productName like CONCAT('%',#{name},'%')",
            "</when>",
            "</script>"
    })
    Integer getCountConditions(Long merchantId, @Param("proIds")List<Long> proIds, String name);
}
