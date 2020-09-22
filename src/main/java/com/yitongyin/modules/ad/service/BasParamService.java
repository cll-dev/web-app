package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BasParamEntity;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 17:55:19
 */
public interface BasParamService extends IService<BasParamEntity> {

   // PageUtils queryPage(Map<String, Object> params);
   BasParamEntity getNameByKey(String key);
}

