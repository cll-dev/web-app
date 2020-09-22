package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Data
@TableName("ad_tag")
public class AdTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer tagid;
	/**
	 * 商户id
	 */
	private Long merchantId;
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 标签描述
	 */
	private String tagDesc;
	//客户数量
	@TableField(exist = false)
	private Integer clientCount;
}
