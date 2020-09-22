package com.yitongyin.modules.ad.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierCollectionEntity;

import java.util.List;

/**
 * 商户对应厂家的收藏表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
public interface AdMerchantSupplierCollectionService extends IService<AdMerchantSupplierCollectionEntity> {
    /**
     * 获取厂家被收藏次数，collect_statu=1
     * @param supplierId
     * @return
     */
    Integer getCountBySupId(long supplierId);

    /**
     * 商户取消或收藏该厂家
     * @param entity
     */
    void updateStatusByMerIdAndSupId(AdMerchantSupplierCollectionEntity entity);

    /**
     * 获取商户收藏的厂家ID
     * @param merId
     * @return
     */
    List<Object> getListByMerId(long merId);

    /**
     * 获取商户对应厂家的收藏状态
     * @param supplierId
     * @param merchantId
     * @return
     */
    AdMerchantSupplierCollectionEntity getStatusByMerchantId(Long supplierId,Long merchantId);

    /**
     * 获取商户对应厂家的收藏表信息
     * @param supplierId
     * @param merchantId
     * @return
     */
    AdMerchantSupplierCollectionEntity getOneByMerchantIdAndSupplier(Long supplierId,Long merchantId);
}

