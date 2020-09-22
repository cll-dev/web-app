package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.form.NewSuppllierInfo;
import com.yitongyin.modules.ad.form.SupplierConditions;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:17:10
 */
public interface BusSupplierService extends IService<BusSupplierEntity> {
   /**
    * 根据调解分页获取厂家列表
    * @param params
    * @param ids
    * @param conditions
    * @return
    */
   PageUtils queryPage(Map<String, Object> params, List<Long> ids, SupplierConditions conditions);

   /**
    * 根据厂家id获取厂家列表
    * @param ids
    * @return
    */
   List<Map<String,Object>> getListById(List<Object> ids);

   /**
    * 根据商户ID获取厂家信息
    * @param id
    * @param merchantId
    * @return
    */
   NewSuppllierInfo getInfoById(Long id,Long merchantId);

   /**
    * 根据county获取厂家列表，过滤“印下单”模板厂家
    * @param county
    * @return
    */
   List<BusSupplierEntity> queryByMerchantCounty(Integer county);

   /**
    * 根据county获取厂家列表,不过滤“印下单”模板厂家
    * @param county
    * @return
    */
   List<BusSupplierEntity> getPageByMerchantCountyWithAllSupplier(Integer county);

   /**
    * 根据厂家名称获取厂家数据
    * @param supplierName
    * @return
    */
   BusSupplierEntity findByName(String supplierName);

   /**
    * 根据厂家等级获取厂家数据
    * @return
    */
   BusSupplierEntity getByLevelId();

   /**
    * 过滤level_id不等于1的厂家
    * @param ids
    * @return
    */
   List<Object> filterTemplate(List<Object> ids);
   /**
    * 过滤level_id不等于1的厂家
    * @param ids
    * @return
    */
   List<BusSupplierEntity> filterEntTemplate(List<Object> ids);
}

