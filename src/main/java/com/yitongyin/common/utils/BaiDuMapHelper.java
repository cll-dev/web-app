package com.yitongyin.common.utils;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.yitongyin.config.ManagerConfig;
import com.yitongyin.modules.ad.inti.SpringContextSupport;

public class BaiDuMapHelper {

    public static String[] getLatLng(String address) {
        ManagerConfig managerConfig = SpringContextSupport.getBean(ManagerConfig.class);
        String ak = managerConfig.getBaiduAppId();
        String path = String.format("http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s&location=showLocation", address, ak);
        String ss =HttpUtil.get(path);
        System.out.println(ss);
        JSONObject result = JSONObject.parseObject(HttpUtil.get(path));
        String[] res = new String[2];
        if (result.getInteger("status") == 0) {
            JSONObject location = result.getJSONObject("result").getJSONObject("location");
            if (null != location) {
                res[0] = String.valueOf(location.getFloat("lat"));
                res[1] = String.valueOf(location.getFloat("lng"));
            }
        }
        return res;
    }
}
