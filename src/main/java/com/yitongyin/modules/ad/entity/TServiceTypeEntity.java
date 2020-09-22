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
 * @date 2019-08-13 09:23:04
 */
@Data
@TableName("t_service_type")
public class TServiceTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long servicetypeid;
	/**
	 * 服务类型名称
	 */
	private String servicename;
	/**
	 * 父级服务类型Id
	 */
	private Long parentservicetypeid;
	/**
	 * 数据层级
	 */
	private Integer level;
	/**
	 * 服务简介
	 */
	private String descr;
	/**
	 * 参数说明
	 */
	private String paramexplain;
	/**
	 * 封面类型
	 */
	private Integer covertype;
	/**
	 * 封面资源id
	 */
	private Long coverresid;
	/**
	 * 封面图标地址
	 */
	private String iconpath;
	/**
	 * 创建日期
	 */
	private Date createdate;
	/**
	 * 更新日期
	 */
	private Date updatedate;
	/**
	 * 孩子数量
	 */
	private Integer childnumber;
	/**
	 * 排序号
	 */
	private Integer ordernumber;
	/**
	 * 是否微信端显示
	 */
	private Integer wxshowable;
	/**
	 * 附近图片列表,以逗号分隔
	 */
	private String attachmentids;
	/**
	 * 默认供应商
	 */
	private Long supplierid;
	/**
	 * 素材链接
	 */
	private String materialurl;
	@TableField(exist = false)
	private List<TServiceTypeEntity> childList;
	@TableField(exist = false)
	private List<BusProductEntity> productList;

}
