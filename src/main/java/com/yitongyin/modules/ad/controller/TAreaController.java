package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.TAreaEntity;
import com.yitongyin.modules.ad.service.TAreaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-09 14:09:25
 */
@RestController
@RequestMapping("ad/area")
public class TAreaController {
    @Autowired
    private TAreaService tAreaService;

    /**
     * 省份列表
     */
    @ApiOperation("获取省份列表")
    @GetMapping("/list")
    @ResponseBody
    public R list(){

        List<TAreaEntity> list =tAreaService.findProvinceList();
        return R.ok().put("list",list);
    }


    /**
     * 获取子级城市列表
     */
    @ApiOperation("获取子级城市列表")
    @GetMapping("/list/child")
    @ResponseBody
    public R childList(Integer parentId){

        List<TAreaEntity> list =tAreaService.findChildListByParentId(parentId);
        return R.ok().put("list",list);
    }






}
