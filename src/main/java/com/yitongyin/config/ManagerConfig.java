package com.yitongyin.config;

import com.yitongyin.modules.ad.msg.PropertiesHelper;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class ManagerConfig {

    private String url;
    private String wxUrl;
    private String baiduAppId;
    private String baiduUrl;
    @PostConstruct
    public void init() {
        PropertiesHelper helper = PropertiesHelper.crateHelper("manager.properties");

        url = helper.getString("manager.url");
        wxUrl=helper.getString("manager.wxUrl");
        baiduAppId=helper.getString("manager.baiduAppId");
        baiduUrl=helper.getString("manager.baiduUrl");
    }
}
