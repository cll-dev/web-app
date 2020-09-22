package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantProductDesignFeeEntity;
import com.yitongyin.modules.mp.View.DesignFee;

import java.util.List;

/**
 * 商户产品设计价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 16:53:52
 */
public interface AdMerchantProductDesignFeeService extends IService<AdMerchantProductDesignFeeEntity> {
    /**
     * 根据商户ID和商户产品ID获取对应设计费模板
     * @param merchantId
     * @param proId
     * @return
     */
    List<AdMerchantProductDesignFeeEntity> getListByProId(Long merchantId,Long proId);
    Boolean updStatus(Long merchantId,Long merProId,Integer status);
    AdMerchantProductDesignFeeEntity getOneByProId(Long merchantId,Long proId);
    List<AdMerchantProductDesignFeeEntity> getAdListByProId(Long merchantId,Long proId);
    List<DesignFee> getServiceConfigByProId(Long proId);
    boolean removeAll(Long merchantId,Long proId);
}

