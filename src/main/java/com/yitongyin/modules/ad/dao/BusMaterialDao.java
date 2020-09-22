package com.yitongyin.modules.ad.dao;

import com.yitongyin.modules.ad.entity.BusMaterialEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.nosql.elasticsearch.document.EsMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 09:48:32
 */
@Mapper
public interface BusMaterialDao extends BaseMapper<BusMaterialEntity> {

    @Select("select m.*,o.url from bus_material m left join ad_oss o on m.cover_res_id = o.id limit #{start},#{end}")
    List<BusMaterialEntity> findPageList(long start, long end);

    @Select("select id,bmtId,title,cover_res_id as coverResId,file_type as fileType,file_res_id as fileResId,tag_key " +
            "as tagKey,create_time as createTime,view_counts as viewCounts,send_counts as sendCounts from bus_material")
    List<EsMaterial> findAllEsMaterials();
}
