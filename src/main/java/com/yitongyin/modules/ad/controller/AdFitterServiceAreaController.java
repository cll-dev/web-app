package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdFitterServiceAreaEntity;
import com.yitongyin.modules.ad.service.AdFitterServiceAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 安装工对应的安装范围
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-30 09:23:07
 */
@RestController
@RequestMapping("ad/fitterservicearea")
public class AdFitterServiceAreaController {
    @Autowired
    private AdFitterServiceAreaService adFitterServiceAreaService;

//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = adFitterServiceAreaService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


}
