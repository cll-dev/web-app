package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.BusMaterialIndustryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 素材与行业对应关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
@Mapper
public interface BusMaterialIndustryDao extends BaseMapper<BusMaterialIndustryEntity> {
    @Select("select bitId FROM bus_material_industry WHERE material_id =#{materialId}")
    List<Long> getBitIdsByMaterialId(Long materialId);
}
