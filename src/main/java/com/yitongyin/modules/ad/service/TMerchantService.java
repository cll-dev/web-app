package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.MerchantReport;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 16:04:20
 */
public interface TMerchantService extends IService<TMerchantEntity> {
      /**
       * 根据id获取商户信息
       * @param id
       * @return
       */
      TMerchantEntity getInfoById(long id);

      /**
       * 根据id修改商户信息
       * @param entity
       */
      void updateInfo(TMerchantEntity entity);

      /**
       * 根据userID获取商户信息
       * @param userId
       * @return
       */
      TMerchantEntity getInfoByUserId(long userId);

      /**
       * 根据email获取商户信息
       * @param email
       * @return
       */
      TMerchantEntity getInfoByEmail(String email);

      /**
       * 根据id修改商户店铺颜色
       * @param color
       * @param merchanId
       * @return
       */
      Boolean updColor(String color,Long merchanId);

      /**
       * 根据商户名称获取商户信息
       * @param name
       * @return
       */
      TMerchantEntity getOneByName(String name);
      /**
       * 获取商户报表
       * @param merchantId
       * @return
       */
      MerchantReport getReport(Long merchantId);
      /**
       * 根据id修改商户简介
       * @param entity
       */
      void updateDes(TMerchantEntity entity);


}

