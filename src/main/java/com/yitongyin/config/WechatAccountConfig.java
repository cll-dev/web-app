package com.yitongyin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//将application.yml的wechat配置转换为Bean
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    private String myAppId;

    private String myAppSecret;

    private String myToken;

    private String myAesKey;
}
