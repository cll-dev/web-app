package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 素材表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 09:48:32
 */
@Data
@TableName("bus_material")
public class BusMaterialEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;

	private Long bmtid;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 封面图片资源id
	 */
	private Long coverResId;
	/**
	 * 素材文件类型
	 */
	private Integer fileType;
	/**
	 * 素材文件资源id
	 */
	private Long fileResId;
	/**
	 * 关键字
	 */
	private String tagKey;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;

	private Integer viewCounts;

	private Integer sendCounts;

}
