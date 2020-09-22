package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusSearchLogEntity;
import com.yitongyin.modules.ad.service.BusSearchLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-15 16:43:57
 */
@RestController
@RequestMapping("mp/searchLog")
public class MpSearchLogController {
    @Autowired
    private BusSearchLogService busSearchLogService;


    /**
     * 获取推荐搜索列表
     */
    @ApiOperation("获取推荐搜索列表")
    @GetMapping("/list")
    public R listIsHot(){
		List<BusSearchLogEntity> list=busSearchLogService.getListByIsSearchRecommend();
        return R.ok().put("list",list);
    }



}
