package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdAuthCodeEntity;

public interface AdAuthCodeService extends IService<AdAuthCodeEntity> {
    /**
     * 插入验证码
     * @param authCode
     */
    void insert(AdAuthCodeEntity authCode);

    /**
     * 查询同一手机号最新的验证码
     */
    AdAuthCodeEntity findLastByPhone(String phone,Integer type);

}
