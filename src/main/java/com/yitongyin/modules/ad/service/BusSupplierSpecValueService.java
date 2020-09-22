package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-30 10:40:48
 */
public interface BusSupplierSpecValueService extends IService<BusSupplierSpecValueEntity> {

    /**
     * 分页获取
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据ssgId获取厂家规格值列表
     * @param ssgId
     * @return
     */
    List<BusSupplierSpecValueEntity> getListBySsgId(Long ssgId);
    /**
     * 根据产品对应厂家的规格值列表
     * @param supplierId
     * @param productId
     * @return
     */
    List<BusSupplierSpecValueEntity> getListBySupAndPro(Long supplierId,Long productId);
}

