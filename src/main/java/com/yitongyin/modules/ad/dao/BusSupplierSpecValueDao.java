package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.ad.entity.BusSpecValueEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-30 10:40:48
 */
@Mapper
public interface BusSupplierSpecValueDao extends BaseMapper<BusSupplierSpecValueEntity> {
    @Select("select v.specKey,v.spec_value_id,v.spec_on_footer,s1.spec_name as specKeyName,s2.val_name as specValueName,\n" +
            "s2.order_number,s2.val_des from bus_supplier_spec_value v\n" +
            "left join bus_spec_value s2 on v.spec_value_id = s2.id\n" +
            "left join bus_spec s1 on v.specKey = s1.specKey where v.ssgId = #{ssgId} AND v.spec_on_footer = #{isFoot}")
    List<BusSupplierSpecValueEntity> findIsFootBySsgId(Long ssgId,Integer isFoot);
    @Select("select  v.specKey,v.spec_value_id,v.spec_on_footer,s1.spec_name as specKeyName,s2.val_name as specValueName,s2.order_number,s2.val_des\n" +
            "from bus_supplier_spec_value v\n" +
            "left join bus_spec_value s2 on v.spec_value_id = s2.id\n" +
            "left join bus_spec s1 on v.specKey = s1.specKey where v.ssgId in (SELECT ssgId FROM bus_supplier_spec_group WHERE supplier_id =#{supplier_id} AND product_id=#{product_id}) GROUP BY v.spec_value_id\n" +
            "order by s2.order_number ASC")
    List<BusSupplierSpecValueEntity> getValueAndNameList(Long supplier_id,Long product_id);
	
}
