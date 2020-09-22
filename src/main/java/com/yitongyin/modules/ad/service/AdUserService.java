package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdUserEntity;

public interface AdUserService extends IService<AdUserEntity> {

    /**
     * 根据用户名，查询系统用户
     */
    AdUserEntity queryByUserName(String username);

    /**
     * 用户注册
     */
    void userReg(AdUserEntity userEntity);

    /**
     * 修改密码
     */
    void updatePwd(AdUserEntity userEntity);
    /**
     * 更换手机号
     */
    void updatePhone(AdUserEntity userEntity);
    /**
     * 根据openId查询数据
     */
    AdUserEntity queryBuOpenId(String openId);
    /**
     * 用户登录绑定openid
     */
    void bindOpenId(String mobile,String openId);
    /**
     * 用户退出消除opendId
     */
    void unbindOpenId(long userId);

    /**
     * 根据邮箱获取
     * @param email
     * @return
     */
    AdUserEntity getOneByEmail(String email);

}
