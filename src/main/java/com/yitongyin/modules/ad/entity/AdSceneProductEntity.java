package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 场景产品表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Data
@TableName("ad_scene_product")
public class AdSceneProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 场景主表id
	 */
	private Long spid;
	/**
	 * 场景产品标题
	 */
	private String productTitle;
	/**
	 * 场景产品描述
	 */
	private String productDesc;
	/**
	 * 主图id
	 */
	private Long mainPicId;
	/**
	 * 图片列表ids，用逗号分隔
	 */
	private String otherPicIds;
	/**
	 * 产品关联id
	 */
	private Long productId;
	/**
	 * 产品管联链接
	 */
	private String productHref;
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
	 * 场景位置id
	 */
	private Long positionStid;
	/**
	 * 产品主图原图id
	 */
	private Long bigMainPicId;
	@TableField(exist = false)
	private String productName;
	@TableField(exist = false)
	private String serviceName;

}
