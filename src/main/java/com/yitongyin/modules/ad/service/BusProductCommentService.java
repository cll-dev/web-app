package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BusProductCommentEntity;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 16:56:38
 */
public interface BusProductCommentService extends IService<BusProductCommentEntity> {

    /**
     * 获取所有案例
     * @return
     */
    List<BusProductCommentEntity> getList();
}

