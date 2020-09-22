package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.form.AdClientQuery;
import com.yitongyin.modules.mp.View.ClientLogin;
import com.yitongyin.modules.mp.View.ProductPriceInfo;

import java.util.List;
import java.util.Map;

/**
 * 客户账号表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
public interface AdClientService extends IService<AdClientEntity> {

    PageUtils queryPage(Map<String, Object> params, AdClientQuery query);

    /**
     * 获取用户在对应的商户店铺下信息
     * @param clientId
     * @param merchantId
     * @return
     */
    AdClientEntity getInfoByIdAndMerchantId(Long clientId, Long merchantId);

    /**
     * 根据手机号获取是否已存在该用户
     * @param mobile
     * @return
     */
    AdClientEntity getByMobile(String mobile);

    /**
     * 注册用户
     * @param login
     * @return
     */
    R addClientByGroupId(ClientLogin login);
    /**
     * 授权用户加入当前商户组
     * @param clientId
     * @param merchantId
     * @return
     */
    boolean addClientToGroupByMerchantId(Long clientId, Long merchantId);
    PageUtils pageGetLikeMore(Map<String, Object> params,Long merchantId,Long clientId);



}

