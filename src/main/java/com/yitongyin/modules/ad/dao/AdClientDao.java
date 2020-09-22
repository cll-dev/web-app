package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户账号表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Mapper
public interface AdClientDao extends BaseMapper<AdClientEntity> {
    @Select({
            "<script>",
            "SELECT a.*,b.group_id FROM ad_client a LEFT JOIN ad_client_group b on a.clientId=b.client_id " +
                    "LEFT JOIN ad_client_tag c on a.clientId =c.client_id " +
                    "WHERE a.mer=1",
            "<when test='mobile!=null'>",
            " AND a.mobile like CONCAT('%',#{mobile},'%')",
            "</when>",
            "<when test='name!=null'>",
            " AND a.name like CONCAT('%',#{name},'%')",
            "</when>",
            "<when test='groupId!=null'>",
            " AND b.group_id=#{groupId}",
            "</when>",
            "<when test='tagId!=null'>",
            " AND c.tag_id=#{tagId}",
            "</when>",
            "GROUP BY a.clientId",
            "LIMIT #{start},#{limit}",
            "</script>"
    })
    List<AdClientEntity> getByGroupIdAndTagId(Long merchantId, Long groupId, Long tagId, String mobile, String name,
                                              Integer start, Integer limit);
    @Select({
            "<script>",
            "SELECT count(*) FROM ad_client a LEFT JOIN ad_client_group b on a.clientId=b.client_id " +
                    "LEFT JOIN ad_client_tag c on a.clientId =c.client_id " +
                    "WHERE 1=1",
            "<when test='mobile!=null'>",
            " AND a.mobile like CONCAT('%',#{mobile},'%')",
            "</when>",
            "<when test='name!=null'>",
            " AND a.name like CONCAT('%',#{name},'%')",
            "</when>",
            "<when test='groupId!=null'>",
            " AND b.group_id=#{groupId}",
            "</when>",
            "<when test='tagId!=null'>",
            " AND c.tag_id=#{tagId}",
            "</when>",
            "GROUP BY a.clientId",
            "</script>"
    })
    Integer  getCountByGroupIdAndTagId(Long merchantId, Long groupId, Long tagId, String mobile, String name);
    @Select("SELECT a.id as client_group_id,a.group_id,b.`name`,b.mobile,c.`name` as group_name,a.client_note as spec_note,d.url FROM ad_client_group a LEFT JOIN ad_client b on a.client_id=b.clientId LEFT JOIN ad_group c on a.group_id=c.groupId LEFT JOIN ad_oss d on b.avatar = d.id WHERE c.merchant_id=#{merchantId} AND b.clientId=#{clientId} group by a.client_id")
    AdClientEntity getInfoByIdAndMerchantId(Long merchantId, Long clientId);
}
