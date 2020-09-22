package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdTagEntity;

import java.util.List;

/**
 * 标签表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
public interface AdTagService extends IService<AdTagEntity> {
    /**
     * 获取标签列表及相应的客户数量
     * @param merchantId
     * @return
     */
    List<AdTagEntity> getClientByMerchantId(Long merchantId);

    /**
     * 获取标签列表
     * @param merchantId
     * @return
     */
    List<AdTagEntity> getListByMerchantId(Long merchantId);
}

