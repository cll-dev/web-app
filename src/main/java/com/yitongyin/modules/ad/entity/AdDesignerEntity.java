package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 设计师表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 17:14:43
 */
@Data
@TableName("ad_designer")
public class AdDesignerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 头像
	 */
	private Long headImgId;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 态度评分
	 */
	private Integer attitudeScore;
	/**
	 * 作品评分
	 */
	private Integer workScore;
	/**
	 * 擅长
	 */
	private String expert;
	/**
	 * 从业年限
	 */
	private Integer yearsOfWork;
	/**
	 * 查看次数
	 */
	private Integer viewCounts;
	/**
	 * 设计师级别
	 */
	private String levelType;
	/**
	 * 设计师自述说
	 */
	private String ownWords;
	/**
	 * 个人简介
	 */
	private String resume;
	/**
	 * 作品展示
	 */
	private String worksShow;
	/**
	 * 闲忙状态
	 */
	private Integer status;
	/**
	 * userid
	 */
	private Long userid;
	/**
	 * 被联系次数
	 */
	private Integer contactedNumber;
	/**
	 * 素材下载次数
	 */
	private Integer materialSendNumber;
	@TableField(exist = false)
	private String headImgUrl;
	@TableField(exist = false)
	private String logoUrl;
	@TableField(exist = false)
	private String email;

}
