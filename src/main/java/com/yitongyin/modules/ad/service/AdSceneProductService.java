package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdSceneProductEntity;

import java.util.List;

/**
 * 场景产品表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
public interface AdSceneProductService extends IService<AdSceneProductEntity> {
    /**
     * 根据场景Id获取场景产品列表
     * @param spId
     * @return
     */
    List<AdSceneProductEntity> getListBySpId(Long spId);
}

