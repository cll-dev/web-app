package com.yitongyin.modules.ad.entity;

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
 * @date 2019-07-16 16:56:38
 */
@Data
@TableName("bus_product_comment")
public class BusProductCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long commentid;
	/**
	 * 是否已审核
	 */
	private Integer isverifyed;
	/**
	 * 审核时间
	 */
	private Date verifydate;
	/**
	 * 评论时间
	 */
	private Date commentdate;
	/**
	 * 评论用户
	 */
	private Long userid;
	/**
	 * 评论用户名称
	 */
	private String username;
	/**
	 * 商品id
	 */
	private Long productid;
	/**
	 * 满意程度
	 */
	private Integer satisfactionlevel;
	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 附近图片列表,以逗号分隔
	 */
	private String attachmentids;
	/**
	 * 是否置顶
	 */
	private Integer topable;
	/**
	 * 最近一次置顶时间
	 */
	private Date topdate;
	/**
	 * 第一条评论回复
	 */
	private Long replyid;
	/**
	 * 回复数量
	 */
	private Integer replynumber;
	/**
	 * 点击量
	 */
	private Integer hits;
	/**
	 * 七牛云附近图片列表,以逗号分隔
	 */
	private String ossids;

}
