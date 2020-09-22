package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;
import com.yitongyin.modules.ad.entity.BusSpecEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 规格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 14:13:42
 */
@Mapper
public interface BusSpecDao extends BaseMapper<BusSpecEntity> {
    @Select("SELECT k.specKey,k.spec_name,v.id,v.val_name FROM bus_spec k LEFT JOIN bus_spec_value v on k.specKey =v.specKey WHERE v.id is not NULL")
    List<BusSpecEntity> getKeyValues();
}
