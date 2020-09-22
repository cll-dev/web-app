package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import io.swagger.models.auth.In;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 17:16:15
 */
public interface AdOssService extends IService<AdOssEntity> {
    /**
     * 根据id获取oss
     * @param id
     * @return
     */
    AdOssEntity findById(long id);

    /**
     * 根据id修改oss状态为status
     * @param id
     * @param status
     */
    void updateStatusById(long id,Integer status);

    /**
     * 根据多个id修改oss状态为status
     * @param ids
     * @param status
     */
    void updateStatusByIds(List<Long> ids,Integer status);

    /**
     * 根据多个ID获取oss
     * @param ids
     * @return
     */
    List<AdOssEntity> findByIds(List<Long> ids);

    /**
     * 根据url修改oss状态为status
     * @param url
     * @param status
     */
    void updateStatusByUrl(List<String> url, Integer  status);
}

