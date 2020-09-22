package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 安装工表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-30 09:23:07
 */
@Data
@TableName("ad_fitter")
public class AdFitterEntity implements Serializable {
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
	 * 头像图片id
	 */
	private Long headImgId;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 态度评分
	 */
	private Integer attitudeScore;
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
	 * 个人简介
	 */
	private String resume;
	/**
	 * 是否买个人保险
	 */
	private Integer buyInsurance;
	/**
	 * 证件照片ids,多张用逗号分隔
	 */
	private String certificatePhotos;
	/**
	 * 案例展示
	 */
	private String worksShow;
	/**
	 * 闲忙状态
	 */
	private Integer status;
	/**
	 * 被联系次数
	 */
	private Integer contactedNumber;
	/**
	 * ad_user表用户id
	 */
	private Long userid;
	@TableField(exist = false)
	private String headUrl;
	@TableField(exist = false)
	private List<String> certificateUrls;

}
