package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdSceneProjectEntity;
import com.yitongyin.modules.mp.View.SceneProject;
import com.yitongyin.modules.mp.View.SceneProjectSimilarView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 场景主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Mapper
public interface AdSceneProjectDao extends BaseMapper<AdSceneProjectEntity> {
    @Select({
            "<script>",
            "SELECT a.spId,a.project_title,a.thumbs_up_number,a.big_main_pic_id,d.url FROM ad_scene_project a LEFT JOIN ad_scene_merchant b on a.smId=b.smId LEFT JOIN ad_scene_type c on a.space_stId=c.stId LEFT JOIN ad_oss d on",
            "a.main_pic_id=d.id WHERE 1=1",
            "<when test='bitId!=null'>",
            " AND b.bitId=#{bitId}",
            "</when>",
            "<when test='spaceStId!=null'>",
            " AND a.space_stId=#{spaceStId}",
            "</when>",
            "<when test='smId!=null'>",
            " AND b.smId=#{smId}",
            "</when>",
            "<when test='title!=null'>",
            " AND (a.project_title like CONCAT('%',#{title},'%') or b.name like CONCAT('%',#{title},'%'))",
            "</when>",
            "ORDER BY a.order_number LIMIT #{start},#{limit}",
            "</script>"
    })
    List<SceneProject> getListByConditions(Long bitId, Long spaceStId, Long smId, String title,
                                           Integer start, Integer limit);
    @Select({
            "<script>",
            "SELECT count(*) FROM ad_scene_project a LEFT JOIN ad_scene_merchant b on a.smId=b.smId LEFT JOIN ad_scene_type c on a.space_stId=c.stId LEFT JOIN ad_oss d on",
            "a.main_pic_id=d.id WHERE 1=1",
            "<when test='bitId!=null'>",
            " AND b.bitId=#{bitId}",
            "</when>",
            "<when test='spaceStId!=null'>",
            " AND a.space_stId=#{spaceStId}",
            "</when>",
            "<when test='smId!=null'>",
            " AND b.smId=#{smId}",
            "</when>",
            "<when test='title!=null'>",
            " AND (a.project_title like CONCAT('%',#{title},'%') or b.name like CONCAT('%',#{title},'%'))",
            "</when>",
            "</script>"
    })
    Integer getCountByConditions(Long bitId, Long spaceStId, Long smId, String title);
    @Select("SELECT b.url FROM ad_scene_project a LEFT JOIN  ad_oss b on a.main_pic_id=b.id WHERE a.smId=#{smId} ORDER BY a.order_number LIMIT 0,1")
    String getUrlBySmId(Long smId);
    @Select({
            "<script>",
            "SELECT a.spId,a.project_title,a.thumbs_up_number,d.url,a.space_stId FROM ad_scene_project a LEFT JOIN ad_scene_merchant b on a.smId=b.smId  LEFT JOIN ad_oss d on",
            "a.main_pic_id=d.id WHERE 1=1",
            "<when test='smId!=null'>",
            " AND b.smId=#{smId}",
            "</when>",
            "ORDER BY a.order_number LIMIT #{start},#{limit}",
            "</script>"
    })
    List<SceneProject> getListBySmId(Long smId, Integer start, Integer limit);
    @Select("SELECT count(*) FROM ad_scene_project WHERE smId=#{smId}")
    Integer getCountBySmId(Long smId);
    @Select("SELECT a.*,c.`name` as industryName,b.bitId,b.name as merchantName FROM ad_scene_project a  LEFT JOIN  ad_scene_merchant b on a.smId=b.smId LEFT JOIN bus_industry_type c on b.bitId=c.bitId WHERE a.spId=#{spId}")
    AdSceneProjectEntity getInfoAndIndustryNameById(Long spId);
    @Select("SELECT a.spId,A.project_title,c.url,a.space_stId,a.thumbs_up_number,b.bitId FROM ad_scene_project a LEFT JOIN ad_scene_merchant b on a.smId=b.smId LEFT JOIN ad_oss c on a.main_pic_id=c.id WHERE b.bitId=#{bitId} AND b.smId!=#{smId} \n" +
            "AND a.space_stId=#{spaceStId}")
    List<SceneProjectSimilarView> getSimilarsByConditions(Long bitId, Long smId, Long spaceStId);
}
