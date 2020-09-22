package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusSpecValueEntity;

import java.util.List;
import java.util.Map;

/**
 * 规格值表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 14:13:42
 */
public interface BusSpecValueService extends IService<BusSpecValueEntity> {
    /**
     * 分页获取
     * @param params
     * @param key
     * @return
     */
    PageUtils queryPage(Map<String, Object> params,String key);

    /**
     * 根据key获取所有value
     * @param key
     * @return
     */
    List<BusSpecValueEntity> getByKey(String key);
}

