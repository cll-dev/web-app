package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 09:58:57
 */
@Data
@TableName("ad_merchant_service_type")
public class AdMerchantServiceTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long typeid;
	/**
	 * 父级服务类别id，值为1是表示父级类型
	 */
	private Long parentTypeId;
	/**
	 * 总后台服务类型id
	 */
	private Long serviceTypeId;
	/**
	 * 商户id
	 */
	private Long merchantId;
	/**
	 * 排序号
	 */
	private Integer adShow;
	/**
	 * 是否显示 0：false,1:true
	 */
	private Integer mpShow;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	/**
	 * 更新日期
	 */
	@TableField(exist = false)
	private Integer childNumber;
	@TableField(exist = false)
	private String typeName;
	@TableField(exist = false)
	private Integer orderNumber;
	@TableField(exist = false)
	private String iconPath;
	@TableField(exist = false)
	private Integer wxShow;
	@TableField(exist = false)
	private String  descr;
	@TableField(exist = false)
	private String  productName;
	@TableField(exist = false)
	private String  ossUrl;
	@TableField(exist = false)
	private Long  coverResId;

}
