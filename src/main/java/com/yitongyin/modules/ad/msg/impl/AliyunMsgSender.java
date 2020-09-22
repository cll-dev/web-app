package com.yitongyin.modules.ad.msg.impl;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yitongyin.modules.ad.form.Project;
import com.yitongyin.modules.ad.msg.MsgSender;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 阿里云的短信发送实现
 * @author Administrator
 *
 */
@Component("AliyunMsgSender")
public class AliyunMsgSender implements MsgSender {
	@Autowired
	private Project project;
	
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
   // static final String accessKeyId = "yourAccessKeyId";
    //static final String accessKeySecret = "yourAccessKeySecret";
    
    private SendSmsResponse sendSms(String mobilePhone, String paramJson, String outId, String templateCode) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", project.getAccessKeyId(), project.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobilePhone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(project.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
       // request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        if(StringUtils.isNotBlank(paramJson)){
        	request.setTemplateParam(paramJson);
        }
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(outId);

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }
    

	@Override
	public boolean send(String mobilePhone, Map<String, String> paramMap) {
		String templateCode = paramMap.get("templateCode");
		String content = paramMap.get("content");
		try {
			System.out.println("tempCode:"+templateCode+",content:\n"+content);
			SendSmsResponse response = sendSms(mobilePhone,content,null,templateCode);
			if(response.getCode().equals("OK")){
				return true;
			}else{
				System.out.println("短信调用错误======start===========");
				System.out.println("Code=" + response.getCode());
		        System.out.println("Message=" + response.getMessage());
		        System.out.println("RequestId=" + response.getRequestId());
		        System.out.println("BizId=" + response.getBizId());
		        System.out.println("短信调用错误======end===========");
				return false;
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return false;
	}

}
