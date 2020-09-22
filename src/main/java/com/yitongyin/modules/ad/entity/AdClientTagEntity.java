package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 标签和客户的关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@Data
@TableName("ad_client_tag")
public class AdClientTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 客户ID
	 */
	private Long clientId;
	/**
	 * 标签ID
	 */
	private Long tagId;
	@TableField(exist = false)
	private String tagIds;
	@TableField(exist = false)
	private List<String> tagNames;

}
