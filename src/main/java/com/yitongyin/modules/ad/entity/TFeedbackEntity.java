package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 17:20:54
 */
@Data
@TableName("t_feedback")
public class TFeedbackEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "feedbackId",type = IdType.INPUT)
	private Long feedbackid;
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 * 反馈时间
	 */
	private Date feedbackdate;
	/**
	 * 联系电话
	 */
	private String mobilephone;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 附近图片列表,以逗号分隔
	 */
	private String attachmentids;
	/**
	 * 商户id
	 */
	private Long merchantId;

}
