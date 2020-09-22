package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商户对应厂家信息的申请表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-27 14:35:52
 */
@Data
@TableName("ad_merchant_supplier_apply")
public class AdMerchantSupplierApplyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商户ID
     */
    @NotNull(message = "商户id不能为空")
    private Long merchantId;
    /**
     * 厂家名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String supplierName;
    /**
     * 厂家联系方式
     */
    @NotBlank(message = "联系方式不能为空")
    private String supplierContact;
    /**
     * 厂家地址
     */
    @NotBlank(message = "地址不能为空")
    private String supplierAddress;
    /**
     * 厂家简介
     */
    private String supplierIntro;
    /**
     * 提交日期
     */
    private Date createTime;
    /**
     * 修改日期
     */
    private Date updateTime;
    /**
     * 审核状态，0：未审核，1：已审核
     */
    private Integer applyState;
    /**
     * 成功审核返回的厂家id
     */
    private Long applySupplierId;
    /**
     * 省份
     */
    @NotNull(message = "省份id不能为空")
    private Integer province;
    /**
     * 城市
     */
    @NotNull(message = "城市id不能为空")
    private Integer city;
    /**
     * 区
     */
    @NotNull(message = "区域id不能为空")
    private Integer county;
    /**
     * 门牌号码
     */
    @NotBlank(message = "门牌号不能为空")
    private String supplierHouseNumber;

}