package com.yitongyin.modules.ad.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierComplaintEntity;

import java.util.Map;

/**
 * 商户对应厂家的投诉表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
public interface AdMerchantSupplierComplaintService extends IService<AdMerchantSupplierComplaintEntity> {

    /**
     * /提交反馈给厂家
     * @param entity
     */
    void subimtByMerIdAndSupId(AdMerchantSupplierComplaintEntity entity);
}

