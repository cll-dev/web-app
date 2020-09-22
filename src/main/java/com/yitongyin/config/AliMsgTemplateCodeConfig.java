package com.yitongyin.config;


import com.yitongyin.modules.ad.msg.PropertiesHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 阿里的短信模板Code
 * 
 * @author Administrator
 *
 */
@Component
public class AliMsgTemplateCodeConfig {
	/**
	 * 短信签名
	 */
	private String signName;

	/**
	 * 注册验证码
	 */
	private String msgCodeTemplateCode;
	/**
	 * 支付成功时
	 */
	private String paySuccessTemplateCode;
	/**
	 * 订单完成时
	 */
	private String orderFinishTemplateCode;
	/**
	 * 修改绑定的手机号码
	 */
	private String changeMobileTemplateCode;
	/**
	 * 订单配送时
	 */
	private String orderDeliveryingTemplateCode;
	/**
	 * 订单取消时
	 */
	private String orderCancleTemplateCode;
	/**
	 * 询价通知
	 */
	private String enquiryTemplateCode;
	/**
	 * 知信币剩余量通知
	 */
	private String balanceTempateCode;
	/**
	 * 用户成功询价后通知
	 */
	private String userEnquiryTemplateCode;
	/**
	 * 企业注册成功后通知
	 */
	private String enterpriseRegistTemplateCode;
	/**
	 * 企业审核成功后通知
	 */
	private String enterpriseVerifyPassTemplateCode;

	@PostConstruct
	public void init() {
		PropertiesHelper helper = PropertiesHelper.crateHelper("alimsg.properties");
		// signName = helper.getString("alimsg.signName");
		msgCodeTemplateCode = helper.getString("alimsg.msgCodeTemplateCode");
		paySuccessTemplateCode = helper.getString("alimsg.paySuccessTemplateCode");
		orderFinishTemplateCode = helper.getString("alimsg.orderFinishTemplateCode");
		changeMobileTemplateCode = helper.getString("alimsg.changeMobileTemplateCode");
		orderDeliveryingTemplateCode = helper.getString("alimsg.orderDeliveryingTemplateCode");
		orderCancleTemplateCode = helper.getString("alimsg.orderCancleTemplateCode");
		enquiryTemplateCode = helper.getString("alimsg.enquiryTemplateCode");
		balanceTempateCode = helper.getString("alimsg.balanceTempateCode");
		userEnquiryTemplateCode = helper.getString("alimsg.userEnquiryTemplateCode");
		enterpriseRegistTemplateCode = helper.getString("alimsg.enterpriseRegistTemplateCode");
		enterpriseVerifyPassTemplateCode = helper.getString("alimsg.enterpriseVerifyPassTemplateCode");
	}

	public String getMsgCodeTemplateCode() {
		return msgCodeTemplateCode;
	}

	public void setMsgCodeTemplateCode(String msgCodeTemplateCode) {
		this.msgCodeTemplateCode = msgCodeTemplateCode;
	}

	public String getPaySuccessTemplateCode() {
		return paySuccessTemplateCode;
	}

	public void setPaySuccessTemplateCode(String paySuccessTemplateCode) {
		this.paySuccessTemplateCode = paySuccessTemplateCode;
	}

	public String getOrderFinishTemplateCode() {
		return orderFinishTemplateCode;
	}

	public void setOrderFinishTemplateCode(String orderFinishTemplateCode) {
		this.orderFinishTemplateCode = orderFinishTemplateCode;
	}

	public String getChangeMobileTemplateCode() {
		return changeMobileTemplateCode;
	}

	public void setChangeMobileTemplateCode(String changeMobileTemplateCode) {
		this.changeMobileTemplateCode = changeMobileTemplateCode;
	}

	public String getOrderDeliveryingTemplateCode() {
		return orderDeliveryingTemplateCode;
	}

	public void setOrderDeliveryingTemplateCode(String orderDeliveryingTemplateCode) {
		this.orderDeliveryingTemplateCode = orderDeliveryingTemplateCode;
	}

	public String getOrderCancleTemplateCode() {
		return orderCancleTemplateCode;
	}

	public void setOrderCancleTemplateCode(String orderCancleTemplateCode) {
		this.orderCancleTemplateCode = orderCancleTemplateCode;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getEnquiryTemplateCode() {
		return enquiryTemplateCode;
	}

	public void setEnquiryTemplateCode(String enquiryTemplateCode) {
		this.enquiryTemplateCode = enquiryTemplateCode;
	}

	public String getBalanceTempateCode() {
		return balanceTempateCode;
	}

	public void setBalanceTempateCode(String balanceTempateCode) {
		this.balanceTempateCode = balanceTempateCode;
	}

	public String getUserEnquiryTemplateCode() {
		return userEnquiryTemplateCode;
	}

	public void setUserEnquiryTemplateCode(String userEnquiryTemplateCode) {
		this.userEnquiryTemplateCode = userEnquiryTemplateCode;
	}

	public String getEnterpriseRegistTemplateCode() {
		return enterpriseRegistTemplateCode;
	}

	public void setEnterpriseRegistTemplateCode(String enterpriseRegistTemplateCode) {
		this.enterpriseRegistTemplateCode = enterpriseRegistTemplateCode;
	}

	public String getEnterpriseVerifyPassTemplateCode() {
		return enterpriseVerifyPassTemplateCode;
	}

	public void setEnterpriseVerifyPassTemplateCode(String enterpriseVerifyPassTemplateCode) {
		this.enterpriseVerifyPassTemplateCode = enterpriseVerifyPassTemplateCode;
	}

}
