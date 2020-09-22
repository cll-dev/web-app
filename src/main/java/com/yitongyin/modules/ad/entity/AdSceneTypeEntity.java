package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 场景标签表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Data
@TableName("ad_scene_type")
public class AdSceneTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long stid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 父级id
	 */
	private Long parentId;
	/**
	 * 子节点个数
	 */
	private Integer childNumber;
	/**
	 * 排序号
	 */
	private Integer orderNumber;

}
