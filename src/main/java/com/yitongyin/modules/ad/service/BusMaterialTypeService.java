package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BusMaterialTypeEntity;

import java.util.List;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-31 10:22:41
 */
public interface BusMaterialTypeService extends IService<BusMaterialTypeEntity> {
    /**
     * 获取所素材分类
     * @return
     */
    List<BusMaterialTypeEntity> getAllListAndChild();
}

