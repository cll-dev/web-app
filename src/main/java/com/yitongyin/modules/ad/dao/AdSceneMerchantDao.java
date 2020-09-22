package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdSceneMerchantEntity;
import com.yitongyin.modules.mp.View.SceneMerchantView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 场景商家表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Mapper
public interface AdSceneMerchantDao extends BaseMapper<AdSceneMerchantEntity> {

    @Select("SELECT a.smId,a.bitId,a.`name`,a.main_no, b.`name` as industry_name,a.main_desc,a.address,a.house_number FROM ad_scene_merchant a LEFT JOIN bus_industry_type b on a.bitId=b.bitId WHERE a.smId=#{smId}")
    SceneMerchantView getInfoById(Long smId);
    @Select({
            "<script>",
            "SELECT a.*,SUM(b.thumbs_up_number) as thumbs_up_number,count(b.thumbs_up_number) as projectCount" ,
            " FROM ad_scene_merchant  a LEFT JOIN ad_scene_project b on a.smId=b.smId where thumbs_up_number is not null",
            "<when test='name!=null'>",
            " AND a.name like CONCAT('%',#{name},'%')",
            "</when>",
            "<when test='bitId!=null'>",
            " AND a.bitId = #{bitId}",
            "</when>",
            "GROUP BY b.smId",
            "<choose>",
            "<when test='isMostPro'>",
            " ORDER BY projectCount desc",
            "</when>",
            "<when test='isMostLike'>",
            " ORDER BY thumbs_up_number desc",
            "</when>",
            "<otherwise>",
            " ORDER BY create_time desc",
            "</otherwise>",
            "</choose>",
            "limit #{start},#{limit}",
            "</script>"
    })
    List<AdSceneMerchantEntity> queryPage(String name,Long bitId,boolean isMostPro,boolean isMostLike,Integer start,Integer limit);
    @Select({
            "<script>",
            "SELECT count(*)" ,
            " FROM ad_scene_merchant  a LEFT JOIN ad_scene_project b on a.smId=b.smId  where thumbs_up_number is not null",
            "<when test='name!=null'>",
            " AND a.name like CONCAT('%',#{name},'%')",
            "</when>",
            "<when test='bitId!=null'>",
            " AND a.bitId = #{bitId}",
            "</when>",
            "</script>"
    })
    Integer queryCountPage(String name,Long bitId);
    @Select("SELECT a.*,count(b.thumbs_up_number) as projectCount  FROM ad_scene_merchant  a LEFT JOIN ad_scene_project b on a.smId=b.smId where a.bitId=#{bitId} AND a.smId <>#{smId} AND thumbs_up_number is not null GROUP BY b.smId")
    List<AdSceneMerchantEntity> getSimilarList(Long bitId,Long smId);
}
