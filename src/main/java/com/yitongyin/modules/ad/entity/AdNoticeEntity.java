package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 17:20:13
 */
@Data
@TableName("ad_notice")
public class AdNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	private String title;
	/**
	 * 外部链接
	 */
	private String outLink;
	/**
	 *
	 */
	private String intro;
	/**
	 *
	 */
	private String content;
	/**
	 *
	 */
	private Integer noticeType;
	/**
	 *
	 */
	private Date publishTime;
	/**
	 *
	 */
	private Long thumbnailResId;
	/**
	 *
	 */
	private Integer viewCounts;
	@TableField(exist = false)
	String url;

}
