package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.BusSupplierServiceTypeEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import com.yitongyin.modules.ad.form.IdValue;
import com.yitongyin.modules.ad.form.SupplierProductList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 11:24:58
 */
@Mapper
public interface BusSupplierServiceTypeDao extends BaseMapper<BusSupplierServiceTypeEntity> {
    @Select("SELECT t.serviceTypeId,t.serviceName,t.parentServiceTypeId FROM bus_supplier_service_type b LEFT JOIN t_service_type t on b.serviceTypeId=t.serviceTypeId WHERE b.supplierId=#{supplierId}")
    List<TServiceTypeEntity> getTypeListBySupId(long supplierId);
}
