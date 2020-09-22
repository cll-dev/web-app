package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantProductDeliveryEntity;

import java.util.List;

public interface AdMerchantProductDeliveryService extends IService<AdMerchantProductDeliveryEntity> {
     /**
      * 根据商户ID和商户产品ID获取对应运费模板
      * @param merchantId
      * @param proId
      * @return
      */
     List<AdMerchantProductDeliveryEntity> getByProId(Long merchantId, Long proId);
     Boolean updStatus (Long merchantId,Long merProId,Integer status);
     AdMerchantProductDeliveryEntity getOneByProId(Long merchantId,Long proId);
}
