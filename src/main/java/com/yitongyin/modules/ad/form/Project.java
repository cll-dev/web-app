package com.yitongyin.modules.ad.form;


import com.yitongyin.modules.ad.msg.PropertiesHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class Project {

	private String id;

	private String name;

	private String unit;

	private String loginLogo;

	private String version;

	private String logo;

	private String code;

	private String alias;

	private String ak;

	private Boolean debug;

	private String templateDir;

	private String resLocalPath;

	private String resNetPath;

	private String msgSender;

	private String accessKeyId;

	private String accessKeySecret;

	private String signName;

	private String registTemplateCode;

	private String setPayPwdTemplateCode;

	private Boolean msgDebug;

	private String baiduAppId;

	private Boolean enableDomainJump;

	private String chatPath;

	private Integer chatPort;

	@PostConstruct
	public void init() {
		PropertiesHelper helper = PropertiesHelper.crateHelper("project.properties");
		id = helper.getString("project.id");
		name = helper.getString("project.name");
		unit = helper.getString("project.unit");
		version = helper.getString("project.version");
		loginLogo = helper.getString("project.loginlogo");
		logo = helper.getString("project.logo");
		code = helper.getString("project.code");
		alias = helper.getString("project.alias");
		ak = helper.getString("project.ak");
		debug = helper.getBoolean("project.debug", false);
		templateDir = helper.getString("project.templatedir");
		resLocalPath = helper.getString("project.resLocalPath");
		if (StringUtils.isBlank(resLocalPath)) {
			// System.err.println("图片存放地址不能为空");
			// System.exit(-1);
		}
		File file = new File(resLocalPath);
		if (!file.exists()) {
			System.err.println("图片存放地址不存在");
			// System.exit(-1);
		}
		Config.WEB_RESOURCE = file.getAbsolutePath();
		resNetPath = helper.getString("project.resNetPath");
		if (StringUtils.isBlank(resNetPath)) {
			System.err.println("图片访问根网址不能为空");
			// .exit(-1);
		}
		Config.RES_NETPATH = resNetPath;
		msgSender = helper.getString("project.msgSender");
		accessKeyId = helper.getString("project.accessKeyId");
		accessKeySecret = helper.getString("project.accessKeySecret");
		signName = helper.getString("project.signName");
		registTemplateCode = helper.getString("project.registTemplateCode");
		setPayPwdTemplateCode = helper.getString("project.setPayPwdTemplateCode");
		msgDebug = helper.getBoolean("project.msgDebug", Boolean.FALSE);
		baiduAppId = helper.getString("project.baiduAppId");
		enableDomainJump = helper.getBoolean("project.enableDomainJump", Boolean.FALSE);

		chatPath = helper.getString("project.chatPath");
		chatPort = helper.getInt("project.chatPort");

	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getLoginLogo() {
		return loginLogo;
	}

	public void setLoginLogo(String loginLogo) {
		this.loginLogo = loginLogo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getResLocalPath() {
		return resLocalPath;
	}

	public void setResLocalPath(String resLocalPath) {
		this.resLocalPath = resLocalPath;
	}

	public String getResNetPath() {
		return resNetPath;
	}

	public void setResNetPath(String resNetPath) {
		this.resNetPath = resNetPath;
	}

	public String getMsgSender() {
		return msgSender;
	}

	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getRegistTemplateCode() {
		return registTemplateCode;
	}

	public void setRegistTemplateCode(String registTemplateCode) {
		this.registTemplateCode = registTemplateCode;
	}

	public String getSetPayPwdTemplateCode() {
		return setPayPwdTemplateCode;
	}

	public void setSetPayPwdTemplateCode(String setPayPwdTemplateCode) {
		this.setPayPwdTemplateCode = setPayPwdTemplateCode;
	}

	public String getBaiduAppId() {
		return baiduAppId;
	}

	public void setBaiduAppId(String baiduAppId) {
		this.baiduAppId = baiduAppId;
	}

	public Boolean getMsgDebug() {
		return msgDebug;
	}

	public Boolean getEnableDomainJump() {
		return enableDomainJump;
	}

	public void setEnableDomainJump(Boolean enableDomainJump) {
		this.enableDomainJump = enableDomainJump;
	}

	public void setMsgDebug(Boolean msgDebug) {
		this.msgDebug = msgDebug;
	}

	public String getChatPath() {
		return chatPath;
	}

	public void setChatPath(String chatPath) {
		this.chatPath = chatPath;
	}

	public Integer getChatPort() {
		return chatPort;
	}

	public void setChatPort(Integer chatPort) {
		this.chatPort = chatPort;
	}

}