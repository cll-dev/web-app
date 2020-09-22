package com.yitongyin.modules.ad.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BusSupplierLevelEntity;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 09:44:52
 */
public interface BusSupplierLevelService extends IService<BusSupplierLevelEntity> {

    /**
     * 获取厂家级别列表
     * @return
     */
    List<BusSupplierLevelEntity> getList();
}

