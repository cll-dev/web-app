package com.yitongyin.modules.ad.controller;

import com.yitongyin.modules.ad.service.BusMaterialIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 素材与行业对应关系表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
@RestController
@RequestMapping("bus/busMaterialIndustry")
public class BusMaterialIndustryController {
    @Autowired
    private BusMaterialIndustryService busMaterialIndustryService;


}
