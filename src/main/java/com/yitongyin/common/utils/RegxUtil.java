package com.yitongyin.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegxUtil {

    public static  List<String> getUrl(String htmlStr){
            List<String> urls=new ArrayList<>();
            String img = "";
            Pattern p_image;
            Matcher m_image;
            //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            p_image = Pattern.compile
                    (regEx_img, Pattern.CASE_INSENSITIVE);
            m_image = p_image.matcher(htmlStr);
            while (m_image.find()) {
                // 得到<img />数据
                img = m_image.group();
                // 匹配<img>中的src数据
                Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
                while (m.find()) {
                    urls.add(m.group(1));
                }
            }
            return urls;
    }
}
