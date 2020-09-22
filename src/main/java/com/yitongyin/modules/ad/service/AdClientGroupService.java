package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdClientGroupEntity;
import com.yitongyin.modules.ad.form.AdClientGroupChange;

import java.util.List;

/**
 * 客户和客户组关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
public interface AdClientGroupService extends IService<AdClientGroupEntity> {

    /**
     * 根据groupId获取该等级下是否存在用户
     * @param groupId
     * @return
     */
    AdClientGroupEntity getOneByGroupId(Integer groupId);

    /**
     * 根据groupIds获取该及下的用户列表
     * @param groupIds
     * @return
     */
    List<AdClientGroupEntity> getListByGroupIds(List<Integer> groupIds);

    /**
     * 判断对应商户所有组下是否存在该用户
     * @param clientId
     * @param merchantId
     * @return
     */
    AdClientGroupEntity getOneByClientIdAndGroupIds(Long clientId, Long merchantId);
    /**
     * 删除对应商户组下的用户
     * @param list
     * @return
     */
    boolean delByClientIdAndGroupIds(List<AdClientGroupChange> list);
    /**
     * 添加对应商户组下的用户
     * @param list
     * @return
     */
    boolean addByClientIdAndGroupIds(List<AdClientGroupChange> list);
    /**
     * 获取用户对应商户所在组groupId
     * @param  clientId
     * @param  merchantId
     * @return
     */
    AdClientGroupEntity getGroupIdByClientIdAndMerchantId(Long clientId, Long merchantId);

    /**
     * 获取客户所有授权的商家
     * @param clientId
     * @return
     */
    List<Long> getMerchantIdsByClientIdAndGroup(Long clientId);


}

