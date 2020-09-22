package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 厂家对应产品的规格价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-30 10:40:45
 */
@Mapper
public interface BusSupplierSpecGroupDao extends BaseMapper<BusSupplierSpecGroupEntity> {
    @Select({"<script>"+
            "SELECT * FROM" +
            "(SELECT bg.*,GROUP_CONCAT(bv.spec_value_id ORDER BY bv.spec_value_id) as valueId FROM bus_supplier_spec_group bg LEFT JOIN bus_supplier_spec_value bv on bg.ssgId=bv.ssgId  WHERE bg.supplier_id=#{supplierId} and bg.product_id=#{productId} GROUP BY bg.ssgId) b " +
            "WHERE b.valueId not in",
            "<foreach collection='valueIds' item='valueId' open='(' separator=',' close=')'>",
            "#{valueId}",
            "</foreach>",
            "</script>"})
    List<BusSupplierSpecGroupEntity> getListBySupIdAndProIdAndValueIds(Long supplierId, Long productId,@Param("valueIds")List<String> valueIds);
	
}
