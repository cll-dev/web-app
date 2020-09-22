package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusMaterialTypeEntity;
import com.yitongyin.modules.ad.service.BusMaterialTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-31 10:22:41
 */
@RestController
@RequestMapping("mp/materialType")
public class MpMaterialTypeController {
    @Autowired
    private BusMaterialTypeService busMaterialTypeService;

    /**
     * 列表
     */
    @ApiOperation("素材分类列表")
    @GetMapping ("/list")
    public R list(){
        List<BusMaterialTypeEntity> list=busMaterialTypeService.getAllListAndChild();

        return R.ok().put("list", list);
    }




}
