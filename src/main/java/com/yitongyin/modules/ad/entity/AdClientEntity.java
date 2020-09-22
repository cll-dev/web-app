package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yitongyin.modules.mp.View.ProductPriceInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户账号表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Data
@TableName("ad_client")
public class AdClientEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long clientid;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 头像图片id
	 */
	private Long avatar;
	/**
	 * 状态 1：正常，0：禁用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	@TableField(exist = false)
	private String groupName;
	@TableField(exist = false)
	private List<String> tagNames;
	@TableField(exist = false)
	private Integer productCollectCount;
	@TableField(exist = false)
	private Integer specCollectCount;
	@TableField(exist = false)
	private String url;
	@TableField(exist = false)
	private Integer groupId;
	@TableField(exist = false)
	private Long clientGroupId;
	@TableField(exist = false)
	private Long clientTagId;
	@TableField(exist = false)
	private Date lastLanTime;
	@TableField(exist = false)
	private String tagName;
	@TableField(exist = false)
	private String specNote;
	@TableField(exist = false)
	private String tagIds;
	@TableField(exist = false)
	private List<Long> longTagIds;
	@TableField(exist = false)
	private List<ProductPriceInfo> proCollectSimilarList;
	@TableField(exist = false)
	private BigDecimal rate;

}
