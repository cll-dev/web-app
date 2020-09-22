package com.yitongyin.modules.ad.msg;

import javax.annotation.Resource;
import java.util.Map;

@Resource
public interface MsgSender {
	
	/**
	 * 
	 * @param mobilePhone
	 * @param paramMap
	 * @return 是否发送成功
	 */
	 boolean send(String mobilePhone, Map<String, String> paramMap);

}
