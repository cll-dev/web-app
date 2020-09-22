package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdSceneProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 场景产品表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Mapper
public interface AdSceneProductDao extends BaseMapper<AdSceneProductEntity> {

    @Select("SELECT a.*,b.productName,c.serviceName FROM ad_scene_product a LEFT JOIN bus_product b on a.product_id=b.productId LEFT JOIN t_service_type c on b.serviceTypeId=c.serviceTypeId WHERE a.spId=#{spId}")
    List<AdSceneProductEntity> getListBySpId(Long spId);
}
