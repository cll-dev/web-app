/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yitongyin.modules.ad.controller;

import cn.hutool.core.map.MapUtil;
import com.yitongyin.modules.ad.entity.AdUserEntity;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static cn.hutool.extra.servlet.ServletUtil.getParamMap;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected AdUserEntity getUser() {
		return (AdUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
	protected String getCurrentPath(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		Map<String, String> params = getParamMap(request);
		params.remove("code");
		String query = request.getQueryString();
		if (StringUtils.isBlank(query)) {
			return url;
		} else {
			if (params.isEmpty()) {
				return url;
			} else {
				query = MapUtil.join(MapUtil.sort(params), "&", "=", true);
			}
			return url + "?" + query;
		}
	}
}
