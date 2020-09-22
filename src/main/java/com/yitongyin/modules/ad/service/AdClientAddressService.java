package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdClientAddressEntity;

import java.util.List;

/**
 * 客户收货地址表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
public interface AdClientAddressService extends IService<AdClientAddressEntity> {
    /**
     * 根据客户ID获取收货地址列表
     * @param clientId
     * @return
     */
   List<AdClientAddressEntity> getListByClientid(Long clientId);
    /**
     * 修改默认地址
     * @param clientId
     * @return
     */
    boolean updIsDefaultByClientId(Long clientId);
}

