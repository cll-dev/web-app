package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.entity.AdClientTagEntity;

import java.util.List;

/**
 * 标签和客户的关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
public interface AdClientTagService extends IService<AdClientTagEntity> {
    /**
     * 根据tagIds获取标签列表
     * @param tagIds
     * @return
     */
    List<AdClientTagEntity> getListByTagIds(List<Integer> tagIds);

    /**
     * 根据tagId删除标签下的所有用户
     * @param tagId
     * @return
     */
    boolean removeByTagId(Integer tagId);
    /**
     * 根据id添加或者删除
     * @param list
     * @return
     */
    boolean addByClientTag(List<AdClientTagEntity> list);

    /**
     * 根据用户Id获取所在商户的标签名子
     * @param clientId
     * @param merchantId
     * @return
     */
    AdClientEntity getNamesByClientIdAndMerchantId(Long clientId, Long merchantId);
    /**
     * 根据id添加或者删除
     * @param list
     * @param tagIds
     * @return
     */
    boolean addTags(List<AdClientTagEntity> list,List<Integer> tagIds,Long clientId);
    /**
     * 根据clientid删除所有标签
     * @param clientId
     * @param tagIds
     * @return
     */
    boolean delAllTagsByClientId(Long clientId,List<Integer> tagIds);
}

