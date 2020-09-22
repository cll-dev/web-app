/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yitongyin.modules.ad.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.utils.QRCodeUtilss;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.SHA1;
import com.yitongyin.modules.ad.controller.cloud.OSSFactory;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.form.Config;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.AdUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("ad/oss")
public class AdOssController {
	@Autowired
	private AdOssService adOssService;
    @Autowired
	private AdUserService adUserService;
	

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
	/**
	 * 上传营业执照
	 */
	@ApiOperation("上传营业执照")
	@PostMapping("/busUpload")
	public R busUpload(@RequestParam("file") MultipartFile file,String phone,String pwd) throws Exception {

		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		if (StrUtil.isBlank(phone)) {
			return R.error("手机号不能为空");
		} else if (StrUtil.isBlank(pwd)) {
			return R.error("密码不能为空");
		}
		//用户信息
		AdUserEntity user =adUserService.queryByUserName(phone);

		//账号不存在、密码错误
		if(user == null ) {
			return R.error("手机号不存在");
		}
		if(!user.getPassword().equals(SHA1.getDigestOfString(pwd, Config.PWD_KEY))){
			return R.error("密码不正确");
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
		/**
		 * 返回url
		 */
		@ApiOperation("上传图片")
		@PostMapping("/returnUrl")
		public R returnUrl(Long id) throws Exception {
			 if(id==null||id==0){
				 return R.error("参数未传");
			 }
             AdOssEntity ossEntity =adOssService.findById(id);
             if(ossEntity==null){
             	return R.error("该图片已删除");
			 }
			return  R.ok().put("url",ossEntity.getUrl());
		}
	/**
	 * 生成二维码base64编码
	 */
	@ApiOperation("生成二维码base64编码")
	@GetMapping("/get/QRCode")
	public R getQRCode(String url)throws IOException {
		String baseCode= QRCodeUtilss.generalQRCode(url);
		return R.ok().put("data",baseCode);
	}


}
