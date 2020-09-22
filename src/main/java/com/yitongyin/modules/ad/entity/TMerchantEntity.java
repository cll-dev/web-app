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
 * @date 2019-07-08 17:37:48
 */
@Data
@TableName("t_merchant")
public class TMerchantEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.INPUT)
	private Long merchantid;
	/**
	 * 商家名称
	 */
	private String merchantname;
	/**
	 * 普通商户
	 */
	private Integer kind;
	/**
	 * 所在省
	 */
	private Integer province;
	/**
	 * 所在市
	 */
	private Integer city;
	/**
	 * 所在区县
	 */
	private Integer county;
	/**
	 * 街道
	 */
	private Integer town;
	/**
	 * 门牌号
	 */
	private String housenumber;
	/**
	 * 详细地址,已逗号分隔
	 */
	private String address;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 维度
	 */
	private String latitude;
	/**
	 * 联系人
	 */
	private String linkname;
	/**
	 * 联系人手机
	 */
	private String linkphone;
	/**
	 * 联系人微信
	 */
	private String linkwechat;
	/**
	 * 联系人QQ
	 */
	private String linkqq;
	/**
	 * 商户介绍
	 */
	private String descr;
	/**
	 * 审核状态
	 */
	private Integer verifystatus;
	/**
	 * 审核备注
	 */
	private String verifyremark;
	/**
	 * 审核时间
	 */
	private Date verifydate;
	/**
	 * 审核人
	 */
	private Long verifyuserid;
	/**
	 * 审核人
	 */
	private String verifyusername;
	/**
	 * 创建时间
	 */
	private Date createdate;
	/**
	 * 修改时间
	 */
	private Date updatedate;
	/**
	 * 营业执照
	 */
	private Long businesslicenseresid;
	/**
	 * 商户LOGO
	 */
	private Long logoresid;
	/**
	 * 收款码
	 */
	private Long receivingqrcodeimgresid;
	/**
	 * 设计师微信二维码
	 */
	private Long designerresid;
	/**
	 * 业务员微信二维码
	 */
	private Long salesmanresid;
	/**
	 * 用户注册时密码
	 */
	private String userpwd;
	/**
	 * 关联ad_user表的用户id
	 */
	private Long userId;
	/**
	 * 联系人座机
	 */
	private String linktel;
	/**
	 * 设计师图片表ID
	 */
	private Long designossid;
	/**
	 * 客服图片表id
	 */
	private Integer saleossid;
	/**
	 * logo图片表id
	 */
	private Integer logoossid;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 营业执照图片表id
	 */
	private Integer businessossid;
	/**
	 * 主题颜色
	 */
	private String themeColor;
	/**
	 * 联系设计师次数
	 */
	private Integer designerContactNumber;
	/**
	 * 素材下载次数
	 */
	private Integer materialSendNumber;
	/**
	 * 门店照片
	 */
	private Integer shopphotoid;
	/**
	 * 联系安装工次数
	 */
	private Integer fitterContactNumber;
	/**
	 * 是否显示用户端Logo商标
	 */
	private Integer showmplogo;
	/**
	 * 营业时间
	 */
	private String openingTime;
	@TableField(exist = false)
	private AdOssEntity designUrl;
	@TableField(exist = false)
	private AdOssEntity saleUrl;
	@TableField(exist = false)
	private AdOssEntity logoUrl;
	@TableField(exist = false)
	private AdOssEntity shopphotoUrl;

}
