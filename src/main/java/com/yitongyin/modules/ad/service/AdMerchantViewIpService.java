package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdMerchantViewIpEntity;

import java.util.List;

/**
 * 商户访客的ip表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-06 17:46:12
 */
public interface AdMerchantViewIpService extends IService<AdMerchantViewIpEntity> {
    /**
     * 根据ip和viewId获取商户访客ip信息
     * @param viewId
     * @param ip
     * @return
     */
    AdMerchantViewIpEntity getByIpAndTime(Long viewId,String ip);

    /**
     * 根据ip和多个viewId获取商户访客ip信息
     * @param viewIds
     * @param ip
     * @return
     */
    AdMerchantViewIpEntity getByIpAndMerIdS(List<Object> viewIds,String ip);
}

