package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:17:10
 */
@Mapper
public interface BusSupplierDao extends BaseMapper<BusSupplierEntity> {
    @Select("select s.supplierId from bus_supplier s where s.county = #{county} and s.supplier_level_id is null\n" +
            "union\n" +
            "select distinct a.supplier_id from bus_supplier_delivery_area a left join bus_supplier s on a.supplier_id = s.supplierId\n" +
            "where find_in_set(#{county}, a.district_ids) and s.supplier_level_id is null")
    List<BusSupplierEntity> getPageByMerchantCounty(Integer county);

    @Select("select s.supplierId from bus_supplier s where s.county = #{county} and s.supplier_level_id is null\n" +
            "union\n" +
            "select distinct a.supplier_id from bus_supplier_delivery_area a left join bus_supplier s on a.supplier_id = s.supplierId\n" +
            "where find_in_set(#{county}, a.district_ids)")
    List<BusSupplierEntity> getPageByMerchantCountyWithAllSupplier(Integer county);

    @Select({
            "<script>",
            "select",
            "s.supplierId,s.supplierName,s.contactWay,s.settlementModes,s.relevantQuotationResIds,s.address,s.houseNumber,",
            "s.deliveryMethod,s.webPath,",
            "ifnull(c.collectAmount,0) as collectAmount, st_distance(point (s.longitude, s.latitude),point(#{mLongitude},#{mLatitude}))* 111195 as distance",
            "from bus_supplier s",
            "left join (select count(supplier_id) as collectAmount,max(supplier_id) as supplierId ",
            "from ad_merchant_supplier_collection ",
            "where collect_status = 1 group by supplier_id) c on s.supplierId = c.supplierId",
            "where s.supplierId in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "<when test='provinceId!=null'>",
            " AND s.province =#{provinceId}",
            "</when>",
            "<when test='cityId!=null'>",
            " AND s.city =#{cityId}",
            "</when>",
            "<when test='relevantQuotationFlag!=null'>",
            " AND s.relevantQuotationResIds is not NULL AND relevantQuotationResIds!=''",
            "</when>",
            "<when test='collectFlag!=null'>",
            " order by collectAmount desc",
            "</when>",
            "<when test='distanceFlag!=null'>",
            " order by distance ASC",
            "</when>",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<BusSupplierEntity> queryPageByConditions(List<Long> ids,String mLongitude,String mLatitude,
            Integer provinceId,Integer cityId,Integer collectFlag,Integer distanceFlag,Integer relevantQuotationFlag,
            Integer start,Integer limit);
    @Select({
            "<script>",
            "select",
            "count(*) from bus_supplier s",
            "left join (select count(supplier_id) as collectAmount,max(supplier_id) as supplierId ",
            "from ad_merchant_supplier_collection ",
            "where collect_status = 1 group by supplier_id) c on s.supplierId = c.supplierId",
            "where s.supplierId in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "<when test='provinceId!=null'>",
            " AND s.province =#{provinceId}",
            "</when>",
            "<when test='cityId!=null'>",
            " AND s.city =#{cityId}",
            "</when>",
            "<when test='relevantQuotationFlag!=null'>",
            " AND s.relevantQuotationResIds is not NULL AND relevantQuotationResIds!=''",
            "</when>",
            "</script>"
    })
    Integer queryCountByConditions(List<Long> ids,Integer provinceId,Integer cityId,Integer relevantQuotationFlag);
}
