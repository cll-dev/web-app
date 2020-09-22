package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantRegionShowEntity;

import java.util.List;
import java.util.Map;

/**
 * 商户广告素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 10:26:24
 */
public interface AdMerchantRegionShowService extends IService<AdMerchantRegionShowEntity> {
    /**
     * 分页获取对应商户广告列表
     * @param params
     * @param merchantId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params,long merchantId);

    /**
     * 以region,list形式获取商户广告列表
     * @param merchantId
     * @return
     */
    Map<String, List<AdMerchantRegionShowEntity>>getByMerchantId(Long merchantId);

    /**
     * 同步后台广告
     * @param merchantId
     * @return
     */
    boolean syncHeaderquarter(Long merchantId);

    /**
     * 修改商户广告链接及图片
     * @param entity
     * @return
     */
    boolean updHref(AdMerchantRegionShowEntity entity);
}

