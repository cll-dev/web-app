package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import com.yitongyin.modules.ad.form.AdSpecGroup;
import com.yitongyin.modules.mp.View.ClientCollectResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商户规格组合对应的价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
public interface AdMerchantSpecGroupService extends IService<AdMerchantSpecGroupEntity> {

    /**
     * 根据产品ID获取对应的商户规格
     * @param merchantId
     * @param productId
     * @return
     */
    List<AdMerchantSpecGroupEntity> getListByProAndMerchant(Long merchantId,Long productId);
    /**
     * 根据产品ID获取对应的商户规格
     * @param merchantId
     * @param productId
     * @return
     */
    List<AdMerchantSpecGroupEntity> getMpListByProAndMerchant(Long merchantId,Long productId);

    /**
     * 通过已选的规格值ID获取对应商户产品所有可选的规格值ID
     * @param merchantId
     * @param proId
     * @param specKey
     * @param ids
     * @return
     */
    AdMerchantSpecGroupEntity getMsgIdByValueIds(Long merchantId, Long proId,String specKey, List<Long> ids);


    /**
     * 根据产品ID获取规格组
     * @param merchantId
     * @param productId
     * @return
     */
    List<AdMerchantSpecGroupEntity> findSpecGroupByProductId(Long merchantId, Long productId,Long supplierId, List<BusSupplierSpecValueEntity> valueEntityList
            ,List<AdMerchantSpecGroupEntity> headValueEntityList);

    /**
     * 根据产品ID判断是否存在规格
     * @param merchantId
     * @param productId
     * @return
     */
    Boolean getByProductIdAndMerchantId(Long merchantId,Long productId);


    /**
     * 根据msgID修改价格和天数
     * @param id
     * @param days
     * @param price
     * @return
     */
    boolean upePriceAndDaysById(Long id, Integer days, BigDecimal price,String specNote);

    /**
     * 根据msgID删除对应规格
     * @param ids
     * @return
     */
    boolean deleteByMsgId(List<Long> ids,Long merchantId,Long productId);


    /**
     * 批量修改或保存规格
     * @param specGroup
     * @param merchantId
     * @return
     */
    boolean saveOrUpdBatch(List<AdSpecGroup> specGroup,Long merchantId,Long productId);
    /**
     * 批量修改或保存规格
     * @param productId
     * @param merchantId
     * @return
     */
    AdMerchantSpecGroupEntity getByMerAndPro(Long merchantId,Long productId);
    /**
     * 获取产品最低价格
     * @param productId
     * @param merchantId
     * @return
     */
    AdMerchantSpecGroupEntity getLowestPriceByMerAndPro(Long merchantId,Long productId);
    /**
     * 获取产品最低价格
     * @param productId
     * @param merchantId
     * @return
     */
    List<ClientCollectResult> getMpClientCollectByConditions(Long merchantId, Long productId, Long supplierId, String valueIds);
}


