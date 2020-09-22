package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.entity.BusSupplierProductEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import com.yitongyin.modules.ad.form.SupplierProductList;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 厂商对应产品的关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 16:07:31
 */
@Mapper
public interface BusSupplierProductDao extends BaseMapper<BusSupplierProductEntity> {
    @Select("SELECT p.productId,p.productName,p.coverResId,p.mpShow FROM bus_supplier_product s  LEFT JOIN bus_product p on s.product_id=p.productId where s.supplier_id=#{supplierId}")
    List<SupplierProductList> getProListBySupId(long supplierId,long merchantId);
    @Select("SELECT s.product_note,p.productId,p.productName,p.coverResId,p.mpShow FROM bus_supplier_product s  LEFT JOIN bus_product p on s.product_id=p.productId where s.supplier_id=#{supplierId}  and s.service_type_id=#{typeId}")
    List<SupplierProductList> getProListByTypeId(long supplierId,Long merchantId, Long typeId);
    @Select("SELECT s.product_note,p.productId,p.productName,p.coverResId,p.mpShow FROM bus_supplier_product s  LEFT JOIN bus_product p on s.product_id=p.productId where s.supplier_id=#{supplierId}")
    List<SupplierProductList> getAllProList(long supplierId);
//    @Select("SELECT COUNT(*) FROM bus_supplier_product where s.supplier_id=#{supplierId}")
//    List<SupplierProductList> getAllProCountBySup(long supplierId);
//    @Select("SELECT COUNT(*) FROM bus_supplier_product s LEFT JOIN bus_product p on s.product_id=p.productId where s.supplier_id=#{supplierId} and s.service_type_id=#{typeId}")
//    Integer getProCountByTypeId(long supplierId,Long typeId);
//    @Select("SELECT  COUNT(DISTINCT supplier_id) FROM bus_supplier_product WHERE service_type_id in(#{supplierIds})")
    @Select({
          "<script>",
            "select",
            "COUNT(DISTINCT a.supplier_id)",
            "FROM bus_supplier_product a",
            "left join bus_supplier b on a.supplier_id = b.supplierId",
            "WHERE a.service_type_id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "AND b.supplier_level_id is null",
            "</script>"
    })
    Integer getSupCountByServiceType(@Param("ids") List<Object> ids);
    @Select("SELECT  COUNT(DISTINCT a.supplier_id) FROM bus_supplier_product a left join bus_supplier b on a.supplier_id = b.supplierId WHERE service_type_id = (#{id}) AND b.supplier_level_id is null")
    Integer getSupCountBySingleServiceType(String id);
    @Select("SELECT  DISTINCT supplier_id FROM bus_supplier_product WHERE service_type_id = (#{id})")
    List<BusSupplierEntity> getIdsBySingleServiceType(Long id);
    @Select("SELECT  DISTINCT t.serviceName FROM bus_supplier_product b LEFT JOIN t_service_type t on b.service_type_id =t.serviceTypeId  WHERE b.supplier_id = #{id}")
    List<String> getTypeNamesBySupId(Long id);
    @Select("SELECT  DISTINCT t.serviceTypeId,t.serviceName,t.parentServiceTypeId FROM bus_supplier_product b LEFT JOIN t_service_type t on b.service_type_id =t.serviceTypeId  WHERE b.supplier_id = #{id}")
    List<TServiceTypeEntity> getTServiceEntityBySupId(Long id);
    @Select("SELECT  DISTINCT b.supplier_id as supplierId,s.supplierName FROM bus_supplier_product b LEFT JOIN bus_supplier s ON b.supplier_id=s.supplierId WHERE b.service_type_id = #{id}")
    List<BusSupplierEntity> getIdAndNamesBySingleServiceType(Long id);
    @Select("select s1.supplier_id as supplierId from bus_supplier_product s1 left join bus_product p on s1.product_id = p.productId where p.productName like CONCAT('%',#{name},'%')\n" +
            "union\n" +
            "select supplierId from bus_supplier where supplierName like CONCAT('%',#{name},'%')")
    List<Long> getSupIdsByProNameAndSupName(String name);
    @Select("SELECT b.* FROM ad_merchant_product a LEFT JOIN bus_supplier_product b ON a.supplier_product_id=b.id WHERE a.merchant_id=#{merchantId} AND a.product_id=#{proId}")
    BusSupplierProductEntity getOneByProIdAndMerId(Long proId,Long merchantId);
    @Select({
            "<script>",
            "select",
            "DISTINCT product_id FROM bus_supplier_product",
            "WHERE supplier_id in",
            "<foreach collection='supIds' item='supId' open='(' separator=',' close=')'>",
            "#{supId}",
            "</foreach>",
            "</script>"
    })
    List<BusSupplierProductEntity> getListBySupIds(@Param("supIds")List<Long> supIds);

}
