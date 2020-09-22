package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.entity.AdClientTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签和客户的关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Mapper
public interface AdClientTagDao extends BaseMapper<AdClientTagEntity> {
    @Select({"<script>",
            "SELECT id,client_id,GROUP_CONCAT(tag_id) as tag_ids FROM ad_client_tag WHERE tag_id in" ,
            "<foreach collection='tagIds' item='tagId' open='(' separator=',' close=')'>",
            "#{tagId}",
            "</foreach>",
            " GROUP BY client_id",
            "</script>"})
    List<AdClientTagEntity> getListByTagIds(@Param("tagIds") List<Integer> tagIds);
    @Select("SELECT GROUP_CONCAT(c.`name`) as tag_name,GROUP_CONCAT(a.tag_id) as tag_ids FROM ad_client_tag a  LEFT JOIN ad_tag c on a.tag_id=c.tagId WHERE a.client_id=#{clientId} AND c.merchant_id=#{merchantId} GROUP BY a.client_id ")
    AdClientEntity getTagNamesByClientId(Long clientId, Long merchantId);

}
