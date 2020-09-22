package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdGroupEntity;

import java.util.List;

/**
 * 客户组表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
public interface AdGroupService extends IService<AdGroupEntity> {
    /**
     * 获取客户等级列表及相应的客户数量
     * @param merchantId
     * @return
     */
    List<AdGroupEntity> getClientByMerchantId(Long merchantId);

    /**
     * 获取是否存在同名的等级
     * @param name
     * @param merchantId
     * @return
     */
    AdGroupEntity getOneByName(String name, Long merchantId);

    /**
     * 获取有客户的等级列表
     * @param merchantId
     * @return
     */
    List<AdGroupEntity> getListByMerchantId(Long merchantId);

    /**
     * 获取对应商户普通用户组
     * @param merchantId
     * @return
     */
    AdGroupEntity getDefaultByMerchantId(Long merchantId);
    /**
     * 获取对应商户游客组
     * @param merchantId
     * @return
     */
    AdGroupEntity getVisitorByMerchantId(Long merchantId);

    /**
     * 删除组及该组对应设置的产品倍数
     * @param groupId
     * @return
     */
    boolean deleteGroupAndProRateByGroupId(Integer groupId);

}

