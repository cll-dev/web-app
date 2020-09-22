package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户组表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Mapper
public interface AdGroupDao extends BaseMapper<AdGroupEntity> {
    @Select("SELECT a.*,count(c.group_id) as clientCount FROM ad_group a LEFT JOIN ad_client_group c " +
            "on a.groupId=c.group_id WHERE a.merchant_id=#{merchantId} GROUP BY a.groupId")
    List<AdGroupEntity> getClientByMerId(Long merchantId);

}
