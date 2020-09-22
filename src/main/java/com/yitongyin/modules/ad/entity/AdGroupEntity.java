package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户组表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Data
@TableName("ad_group")
public class AdGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer groupid;
	/**
	 * 商户id
	 */
	private Long merchantId;
	/**
	 * 组名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String groupDesc;
	/**
	 * 是否是默认组,默认组不能删除
	 */
	private Integer defaultGroup;
	/**
	 * 是否能加入客户，否则不能加入客户到组中
	 */
	private Integer joinClient;
	/**
	 * 用户端是否显示价格 1:是，0:否
	 */
	private Integer showPrice;
	/**
	 * 价格倍数
	 */
	private BigDecimal priceRate;
	//客户数量
	@TableField(exist = false)
	private Integer clientCount;

}
