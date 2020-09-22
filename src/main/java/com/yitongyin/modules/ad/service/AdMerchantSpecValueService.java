package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.ad.form.SpecInCommon;
import com.yitongyin.modules.mp.View.SpecGroupVM;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
public interface AdMerchantSpecValueService extends IService<AdMerchantSpecValueEntity> {
    /**
     * 分页获取商户规格值列表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 删除所有规格值
     * @return
     */
    boolean deleteAll();

    /**
     * 根据msg根据获取所有list
     * @param msgId
     * @return
     */
    List<AdMerchantSpecValueEntity> getListByMsgId(Long msgId);

    /**
     * 根据多个msg获取所有list
     * @param msgIds
     * @return
     */
    List<SpecGroupVM> getSpecGroupByMsgId(List<Long > msgIds);

    /**
     * 根据msg删除
     * @param ids
     * @return
     */
    boolean deleteByMsgId(List<Long> ids);
    /**
     * 根据atr开关常用规格
     * @param atr
     * @return
     */
    boolean updInCommon(SpecInCommon atr,List<Long> curMsgIds);
}

