package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierApplyEntity;

import java.util.Map;

/**
 * 商户对应厂家信息的申请表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-27 14:35:52
 */
public interface AdMerchantSupplierApplyService extends IService<AdMerchantSupplierApplyEntity> {
    /**
     * 分页获取提交的商家list
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据商家名字获取提交的商家信息
     * @param supplierName
     * @return
     */
    AdMerchantSupplierApplyEntity findByName(String supplierName);
}

