/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yitongyin.modules.mp.controller;


import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.cloud.OSSFactory;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.AdUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("smp/oss")
public class MpOssController {
	@Autowired
	private AdOssService adOssService;
	

	/**
	 * 上传文件
	 */
	@ApiOperation("上传图片")
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file)  {

		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
			AdOssEntity ossEntity = new AdOssEntity();
			ossEntity.setUrl(url);
			ossEntity.setCreateDate(new Date());
			ossEntity.setStatus(2);
			if(adOssService.save(ossEntity)){
				Map<String, Object> map = new HashMap<>();
				map.put("url", url);
				map.put("id",ossEntity.getId());
				return R.ok(map);
			}else{
				return R.error("上传错误");
			}
		}catch (Exception e){
			return R.error("上传超时");
		}

	}

}
