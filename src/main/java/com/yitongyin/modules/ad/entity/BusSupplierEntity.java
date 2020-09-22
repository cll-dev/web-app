package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 16:13:13
 */
@Data
@TableName("bus_supplier")
public class BusSupplierEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "supplierId",type = IdType.INPUT)
	private Long supplierid;
	/**
	 * 供应商名称
	 */
	@NotBlank(message = "厂家名称不能为空")
	private String suppliername;
	/**
	 * 供应商首字母
	 */
	private String supplierpinyin;
	/**
	 * 对接联系人
	 */
	private String contacts;
	/**
	 * 对接联系方式
	 */
	@NotBlank(message = "联系方式不能为空")
	private String contactway;
	/**
	 * 简介
	 */
	@TableField(value = "`into`")
	private String into;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 审核时间
	 */
	private Date verifydate;
	/**
	 * 审核人
	 */
	private Long verifyuserid;
	/**
	 * 审核人名称
	 */
	private String verifyusername;
	/**
	 * 审核备注
	 */
	private String verifyremark;
	/**
	 * 供应商分组等级
	 */
	private String grouplevel;
	/**
	 * 创建日期
	 */
	private Date crtdate;
	/**
	 * 更新日期
	 */
	private Date upddate;
	/**
	 * 营业执照
	 */
	private Long businesslicenseresid;
	/**
	 * 所在省
	 */
	@NotNull(message = "厂家省份ID不能为空")
	private Integer province;
	/**
	 * 所在市
	 */
	@NotNull(message = "厂家所在城市ID不能为空")
	private Integer city;
	/**
	 * 所在区县
	 */
	@NotNull(message = "厂家所在区ID不能为空")
	private Integer county;
	/**
	 * 街道
	 */
	private Integer town;
	/**
	 * 楼幢门牌地址
	 */
	@NotBlank(message = "厂家所在详细地址不能为空")
	private String housenumber;
	/**
	 * 省市区镇地址,已“/”分隔
	 */
	@NotBlank(message = "省市区地址不能为空")
	private String address;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 维度
	 */
	private String latitude;
	/**
	 * 结算方式
	 */
	private String settlementmodes;
	/**
	 * 结算方式是月结时,每月的结算日
	 */
	private Integer dayofeachmonth;
	/**
	 * 相关报价id
	 */
	private String relevantquotationresids;
	/**
	 * 附件id
	 */
	private String attachresids;
	/**
	 * 网址
	 */
	private String webpath;
	/**
	 * 其他聊天工具及联系方式
	 */
	private String otherlinkinfo;
	/**
	 * 
	 */
	private String settlementaccount;
	/**
	 * 厂商级别Id
	 */
	private Long supplierLevelId;
	/**
	 * 公司电话
	 */
	private String telNo;
	/**
	 * 公司手机
	 */
	private String mobileNo;
	/**
	 * 送货方式
	 */
	private String deliverymethod;
	@TableField(exist = false)
	private Integer collectAmount;
	@TableField(exist = false)
	private double distance;




}
