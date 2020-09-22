package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.entity.BusSupplierProductEntity;

import java.util.List;
import java.util.Map;

/**
 * 厂商对应产品的关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 16:07:31
 */
public interface BusSupplierProductService extends IService<BusSupplierProductEntity> {
    /**
     * 根据后台proID获取所有厂家产品
     * @param proId
     * @return
     */
    List<Object> getListByProId(long proId);
    /**
     * 根据后台proID获取所有厂家产品
     * @param proId
     * @return
     */
    List<BusSupplierProductEntity> getEntListByProId(Long proId);

    /**
     * 根据子分类获取厂家数量
     * @param ids
     * @return
     */
    Integer getCountByServiceType(List<Object> ids);

    /**
     * 根据分类获取厂家数量
     * @param id
     * @return
     */
    Integer getCountBySingleServiceType(String id);

    /**
     * 根据分类获取厂家列表
     * @param id
     * @return
     */
    List<BusSupplierEntity> getSupIdBySingleServiceType(Long id);

    /**
     * 厂家ID获取所有可做分类名称
     * @param id
     * @return
     */
    List<String> getTypeNamesBySupId(Long id);

    /**
     * 根据分类获取管理的厂家名称及ID
     * @param id
     * @return
     */
    List<BusSupplierEntity> getIdAndNamesByType(Long id);

    /**
     * 根据分类获取所有厂家可做产品对应的商户产品
     * @param params
     * @param supId
     * @param merchantId
     * @param serviceTypeId
     * @return
     */
    PageUtils getProListByType(Map<String, Object> params,Long supId, Long merchantId, Long serviceTypeId);

    /**
     * 获取所有厂家可做产品对应的商户产品
     * @param params
     * @param supId
     * @param merchantId
     * @return
     */
    PageUtils getAllProList(Map<String, Object> params,Long supId, Long merchantId);

    /**
     * 根据产品名称或者厂家名称获取所有厂家ID
     * @param name
     * @return
     */
    List<Long> getSupIdsByProNameAndSupName(String name);
    /**
     * 根据后台proID,merchantId获取所有厂家(过滤不在默认配送范围内的厂家)
     * @param proId
     * @return
     */
    List<Long> getAreaListByProId(Long proId,Integer county);
    /**
     * 根据后台proID,proId
     * @param proId
     * @param supId
     * @return
     */
    BusSupplierProductEntity getOrderByProIdAndSupId(Long supId,Long proId);
    /**
     * 根据后台proID,merchantId获取
     * @param proId
     * @param merchantId
     * @return
     */
    BusSupplierProductEntity getOrderByProIdAndMerchantId(Long proId,Long merchantId);
    /**
     * 根据supIDs获取
     * @param supplierIds
     * @return
     */
    List<BusSupplierProductEntity> getListBySupIds(List<Long> supplierIds);
}

