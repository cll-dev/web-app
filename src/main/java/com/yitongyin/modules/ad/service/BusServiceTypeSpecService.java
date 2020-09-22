package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BusServiceTypeSpecEntity;
import com.yitongyin.modules.ad.entity.BusSpecEntity;

import java.util.List;

/**
 * 产品分类规格字段表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-16 15:15:20
 */
public interface BusServiceTypeSpecService extends IService<BusServiceTypeSpecEntity> {
   /**
    * 根据产品分类ID获取所有对应规格
    * @param serviceTypeId
    * @return
    */
   List<BusSpecEntity> getKeysByServiceTypeId(Long serviceTypeId);
}

