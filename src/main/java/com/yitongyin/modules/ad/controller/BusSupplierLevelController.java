package com.yitongyin.modules.ad.controller;


import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusSupplierLevelEntity;
import com.yitongyin.modules.ad.service.BusSupplierLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 09:44:52
 */
@RestController
@RequestMapping("bus/bussupplierlevel")
public class BusSupplierLevelController {
    @Autowired
    private BusSupplierLevelService busSupplierLevelService;

    /**
     * 列表
     */
    @ApiOperation("获取厂家级别列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public R list(){

        List<BusSupplierLevelEntity> list=busSupplierLevelService.getList();
        return R.ok().put("list",list);
    }






}
