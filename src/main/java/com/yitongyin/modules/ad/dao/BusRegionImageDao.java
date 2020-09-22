package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.BusRegionImageEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 广告位对应的图片库
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-26 14:43:01
 */
@Mapper
public interface BusRegionImageDao extends BaseMapper<BusRegionImageEntity> {
    @Select({
            "<script>",
            "select b.*,a.url as ossUrl",
            "FROM bus_region_image b",
            "LEFT JOIN ad_oss a ON b.oss_id =a.id",
            "WHERE b.region =#{region}",
            "AND b.service_type_id=#{serviceTypeId}",
            "<when test='name!=null'>",
            " AND b.key_word like CONCAT('%',#{name},'%')",
            "</when>",
            "</script>"
    })
    List<BusRegionImageEntity> getOssByReionAndType(Object region, Object name,Long serviceTypeId);
    @Select({
            "<script>",
            "select t.serviceName,t.serviceTypeId",
            "FROM bus_region_image b",
            "LEFT JOIN t_service_type t ON b.service_type_id =t.serviceTypeId",
            "WHERE b.service_type_id is not null",
            "<when test='region!=null'>",
            " AND b.region =#{region}",
            "</when>",
            "<when test='name!=null'>",
            " AND b.key_word like CONCAT('%',#{name},'%')",
            "</when>",
            "GROUP BY t.serviceTypeId ORDER BY t.orderNumber ASC",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<TServiceTypeEntity> pageByRegion(Object region, Object name, Integer start, Integer limit);
    @Select({
            "<script>",
            "select t.serviceName,t.serviceTypeId",
            "FROM bus_region_image b",
            "LEFT JOIN t_service_type t ON b.service_type_id =t.serviceTypeId",
            "WHERE 1=1",
            "<when test='region!=null'>",
            " AND b.region =#{region}",
            "</when>",
            "<when test='name!=null'>",
            " AND b.key_word like CONCAT('%',#{name},'%')",
            "</when>",
            "GROUP BY t.serviceTypeId ORDER BY t.orderNumber ASC",
            "</script>"
    })
    List<TServiceTypeEntity> pageCountByRegion(Object region,Object name);
}
