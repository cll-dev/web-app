package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Mapper
public interface AdTagDao extends BaseMapper<AdTagEntity> {
    @Select("SELECT a.*,count(c.tag_id) as clientCount FROM ad_tag a LEFT JOIN ad_client_tag c " +
            "on a.tagId=c.tag_id WHERE a.merchant_id=#{merchantId} GROUP BY c.tag_id")
    List<AdTagEntity> getClientByMerId(Long merchantId);
}
