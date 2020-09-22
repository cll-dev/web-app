package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 场景主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Data
@TableName("ad_scene_project")
public class AdSceneProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long spid;
	/**
	 * 场景商家id
	 */
	private Long smid;
	/**
	 * 场景标题
	 */
	private String projectTitle;
	/**
	 * 场景描述
	 */
	private String projectDesc;
	/**
	 * 场景空间id
	 */
	private Long spaceStid;
	/**
	 * 主图id
	 */
	private Long mainPicId;
	/**
	 * 图片列表ids，用逗号分隔
	 */
	private String otherPicIds;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 排序号
	 */
	private Integer orderNumber;
	/**
	 * 点赞数
	 */
	private Integer thumbsUpNumber;
	/**
	 * 产品主图原图id
	 */
	private Long bigMainPicId;
	@TableField(exist = false)
	private String industryName;
	@TableField(exist = false)
	private Long bitid;
	@TableField(exist = false)
	private String merchantName;


}
