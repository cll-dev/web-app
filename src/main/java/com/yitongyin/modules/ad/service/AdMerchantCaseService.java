package com.yitongyin.modules.ad.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantCaseEntity;
import com.yitongyin.modules.ad.form.CaseConditions;
import com.yitongyin.modules.mp.View.CaseInfo;

import java.util.List;
import java.util.Map;

/**
 * 商户案例表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 11:34:13
 */
public interface AdMerchantCaseService extends IService<AdMerchantCaseEntity> {
    /**
     * 根据条件分页获取商户案例表
     * @param params
     * @param caseConditions
     * @param merchantId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, CaseConditions caseConditions,Long merchantId);

    /**
     * 根据多个Id获取案例attachment_ids
     * @param ids
     * @return
     */
    List<AdMerchantCaseEntity> getOssIdsByIds(List<Long> ids);

    /**
     * 根据商户ID获取对应产品的案例数量
     * @param proId
     * @param merchantId
     * @return
     */
    Integer countByProId(Long proId,Long merchantId);

    /**
     * 根据商户ID分页获取对应产品的案例列表
     * @param params
     * @param proId
     * @param merchantId
     * @return
     */
    PageUtils listByMerchantIdAndProId(Map<String, Object> params,Long proId,Long merchantId);

    /**
     * 根据产品ID和商户名称获取对应案例详情
     * @param proId
     * @param merchantId
     * @param proName
     * @return
     */
    CaseInfo getOneByMerchantIdAndProId(Long proId,Long merchantId,String proName);

    /**
     * 商户同步后台案例,只在已有案例上追加后台案例
     * @param list
     * @param merchantId
     * @return
     */
    Boolean synList(List<AdMerchantCaseEntity> list,Long merchantId);
}

