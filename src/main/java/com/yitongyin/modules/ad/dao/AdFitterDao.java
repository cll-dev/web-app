package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdFitterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 安装工表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-30 09:23:07
 */
@Mapper
public interface AdFitterDao extends BaseMapper<AdFitterEntity> {
    @Select("select distinct a.fitter_id from ad_fitter_service_area a \n" +
            "            where find_in_set(#{county}, a.district_ids) ")
    List<Long> getFiterIdsByCounty(Integer county);
	
}
