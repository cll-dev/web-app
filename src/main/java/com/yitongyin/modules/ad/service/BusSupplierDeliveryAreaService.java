package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BusSupplierDeliveryAreaEntity;


/**
 * 厂商对应的配送范围
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:23:45
 */
public interface BusSupplierDeliveryAreaService extends IService<BusSupplierDeliveryAreaEntity> {
    /**
     * 根据city获取所有可配送的厂家
     * @param cityId
     * @return
     */
    //List<BusSupplierDeliveryAreaEntity> getSupIdsByAreaIds(Integer cityId);
}

