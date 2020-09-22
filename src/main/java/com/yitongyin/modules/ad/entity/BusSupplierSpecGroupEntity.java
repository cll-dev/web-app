package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 厂家对应产品的规格价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-30 10:40:45
 */
@Data
@TableName("bus_supplier_spec_group")
public class BusSupplierSpecGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long ssgid;
	/**
	 * 厂商ID
	 */
	private Long supplierId;
	/**
	 * 总后台商品ID
	 */
	private Long productId;
	/**
	 * 总后台分类ID
	 */
	private Long serviceTypeId;
	/**
	 * 规格值组合的价格
	 */
	private BigDecimal price;
	/**
	 * 到货天数
	 */
	private Integer deliveryDay;
	/**
	 * 厂家产品规格顺序号
	 */
	private Integer specOrder;
	/**
	 * 备注
	 */
	private String specNote;
	/**
	 * 门店价格
	 */
	private BigDecimal salePrice;
	/**
	 * 自定义规格组id,用于商户关联查询
	 */
	/**
	 * 规格组合图片id
	 */
	private Long specImageId;
	private Long groupId;
	@TableField(exist = false)
	private List<BusSupplierSpecValueEntity> headSpecValues;
	@TableField(exist = false)
	private List<BusSupplierSpecValueEntity> footSpecValues;
	@TableField(exist = false)
	private Integer orderNumber;
	@TableField(exist = false)
	private  List<BusSupplierSpecGroupEntity> list;
	@TableField(exist = false)
	private  Integer isShow;
	@TableField(exist = false)
	private  Long msgId;
	@TableField(exist = false)
	private  String valueId;
	@TableField(exist = false)
	private  String url;

}
