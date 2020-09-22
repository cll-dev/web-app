package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2019-07-10 11:14:58
 */
@Data
@TableName("bus_product")
public class BusProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "productId",type = IdType.INPUT)
	private Long productid;
	/**
	 * 商品名称
	 */
	private String productname;
	/**
	 * 简要说明
	 */
	private String productdescr;
	/**
	 * 详情
	 */
	private String productcontent;
	/**
	 * 标签
	 */
	private String tags;
	/**
	 * 创建者
	 */
	private Long createuserid;
	/**
	 * 创建日期
	 */
	private Date createdate;
	/**
	 * 封面资源id
	 */
	private Long coverresid;
	/**
	 * 默认产品目录
	 */
	private Long servicetypeid;
	/**
	 * 发布时间
	 */
	private Date publishdate;
	/**
	 * 发布状态
	 */
	private Integer publishable;
	/**
	 * 发布者
	 */
	private Long publishuserid;
	/**
	 * 下架时间
	 */
	private Date descenddate;
	/**
	 * 下架者
	 */
	private Long descenduserid;
	/**
	 * 评论数
	 */
	private Long commentnumber;
	/**
	 * 更新日期
	 */
	private Date updatedate;
	/**
	 * 更新者
	 */
	private Long updateuserid;
	/**
	 * 排序号
	 */
	private Integer orderno;
	/**
	 * 
	 */
	private Integer indexable;
	/**
	 * 所在组
	 */
	private String group;
	/**
	 * 复制来源id
	 */
	private Long copyfromid;
	/**
	 * 是否跳转外链
	 */
	private Integer outable;
	/**
	 * 外链地址
	 */
	private String outlink;
	/**
	 * 0：未审核  1：已审核
	 */
	private Integer approved;
	/**
	 * 封面七牛云资源ID
	 */
	private Integer ossid;
	/**
	 * 是否定义厂家规格配置
	 */
	private Integer supplierSpecConf;
	/**
	 * 素材链接
	 */
	private String materialurl;
	/**
	 * 设计费配置json串
	 */
	private String designfee;
	@TableField(exist = false)
	private Integer supCount;
	@TableField(exist = false)
	private Integer caseCount;
	@TableField(exist = false)
	private Long merchantProductId;


}
