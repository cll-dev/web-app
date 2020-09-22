/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yitongyin.modules.ad.controller.cloud;


import com.yitongyin.config.CloudConfig;
import com.yitongyin.modules.ad.inti.SpringContextSupport;


/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
public final class OSSFactory {
//    private static SysConfigService sysConfigService;
//
//    static {
//        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
//    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudConfig cloud = SpringContextSupport.getBean(CloudConfig.class);
        CloudStorageConfig config = new CloudStorageConfig();
        config.setQiniuAccessKey(cloud.getAppkey());
        config.setQiniuSecretKey(cloud.getSecretkey());
        config.setQiniuBucketName(cloud.getBucketName());
        config.setQiniuPrefix(cloud.getPrefix());
        config.setQiniuDomain(cloud.getUrl());

        return new QiniuCloudStorageService(config);

    }

}
