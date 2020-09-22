package com.yitongyin.modules.ad.msg;


import com.google.gson.JsonObject;
import com.yitongyin.config.AliMsgTemplateCodeConfig;
import com.yitongyin.modules.ad.inti.SpringContextSupport;


import java.util.HashMap;
import java.util.Map;

public class MsgSenderHelper {
	/**
	 * 注册验证码
	 * 
	 * @param mobilePhone
	 * @param msgCode
	 * @return
	 */
	public static boolean sendRegistMsgCode(String mobilePhone, String msgCode) {
		AliMsgTemplateCodeConfig config = SpringContextSupport.getBean(AliMsgTemplateCodeConfig.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("templateCode", config.getMsgCodeTemplateCode());
		JsonObject json = new JsonObject();
		json.addProperty("code", msgCode);
		map.put("content", json.toString());
		return MsgSenderFactory.getInstance().getSender().send(mobilePhone, map);
	}
	/**
	 *修改密碼验证码
	 *
	 */
	public static boolean sendPwdMsg(String mobilePhone,String account,String pwd){
		AliMsgTemplateCodeConfig config = SpringContextSupport.getBean(AliMsgTemplateCodeConfig.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("templateCode", "SMS_165411092");
		JsonObject json = new JsonObject();
		json.addProperty("account", account);
		json.addProperty("pwd",pwd);
		map.put("content", json.toString());
		return MsgSenderFactory.getInstance().getSender().send(mobilePhone, map);
	}

}
