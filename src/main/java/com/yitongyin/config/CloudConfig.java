package com.yitongyin.config;

import com.yitongyin.modules.ad.msg.PropertiesHelper;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class CloudConfig {

    /**
     * 七牛云公钥
     */
    private String appkey;
    /**
     * 七牛私钥
     */
    private String secretkey;
    /**
     * 七牛云域名
     */
    private String url;
    /**
     * 七牛云域名前缀
     */
    private String prefix;
    /**
     * 七牛空间
     */
    private String bucketName;

    @PostConstruct
    public void init() {
        PropertiesHelper helper = PropertiesHelper.crateHelper("qiniucloud.properties");
        // signName = helper.getString("qiniu.appkey");
        appkey = helper.getString("qiniu.appkey");
        secretkey = helper.getString("qiniu.secret");
        url = helper.getString("qiniu.url");
        prefix = helper.getString("qiniu.prefix");
        bucketName = helper.getString("qiniu.bucketName");
    }
}
