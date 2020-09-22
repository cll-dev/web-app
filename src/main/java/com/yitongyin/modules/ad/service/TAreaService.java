package com.yitongyin.modules.ad.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.TAreaEntity;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-09 14:09:25
 */
public interface TAreaService extends IService<TAreaEntity> {

    /**
     * 获取所有省份列表
     * @return
     */
    List<TAreaEntity> findProvinceList();

    /**
     * 根据parentID获取其下的城市或者区域列表
     * @param parentId
     * @return
     */
    List<TAreaEntity> findChildListByParentId(Integer parentId);
}

