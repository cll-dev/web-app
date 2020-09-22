package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;

import java.util.Map;

/**
 * 设计师表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 17:14:43
 */
public interface AdDesignerService extends IService<AdDesignerEntity> {
    /**
     * 分页查询列表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据ID查询信息
     * @param id
     * @return
     */
    AdDesignerEntity findById(Long id);
    /**
     * 根据userID查询信息
     * @param userId
     * @return
     */
    AdDesignerEntity findByUserId(Long userId);
}

