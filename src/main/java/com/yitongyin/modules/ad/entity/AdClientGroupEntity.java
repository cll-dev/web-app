package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户和客户组关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Data
@TableName("ad_client_group")
public class AdClientGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 客户id
	 */
	private Long clientId;
	/**
	 * 客户组id
	 */
	private Integer groupId;
	/**
	 * 备注
	 */
	private String clientNote;
	@TableField(exist = false)
	private String name;
	@TableField(exist = false)
	private Integer showPrice;

}
