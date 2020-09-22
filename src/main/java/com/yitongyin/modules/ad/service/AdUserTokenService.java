package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdUserTokenEntity;

public interface AdUserTokenService extends IService<AdUserTokenEntity> {

    /**
     * 生成token
     * @param userId  用户ID
     */
    R createToken(long userId);
    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    void logout(long userId);
    /**
     * 过期时间内只生成一次token
     * @param userId  用户ID
     */
    R createTokenByExpireTime(long userId);

    AdUserTokenEntity getByToken(String token);
}
