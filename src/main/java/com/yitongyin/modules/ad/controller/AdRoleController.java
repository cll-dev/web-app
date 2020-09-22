package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdRoleEntity;
import com.yitongyin.modules.ad.service.AdRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad/role")
public class AdRoleController {
    @Autowired
    AdRoleService adRoleService;

    @ApiOperation("获取角色列表")
    @GetMapping("/list")
    public R list(){
        List<AdRoleEntity> list = adRoleService.getAllRoles();
        return R.ok().put("list",list);
    }

    @ApiOperation("add")
    @PostMapping("/add")
    public R add(@RequestBody AdRoleEntity role){
        adRoleService.insert(role);
        return R.ok();
    }
}
