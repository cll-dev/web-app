package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.annotion.Login;
import com.yitongyin.common.annotion.LoginUser;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdClientAddressEntity;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.service.AdClientAddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 客户收货地址表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@RestController
@RequestMapping("smp/address")
public class MpClientAddressController {
    @Autowired
    private AdClientAddressService adClientAddressService;

    /**
     * 列表
     */
    @Login
    @GetMapping("/list")
    @ApiOperation("客户收货地址表")
    public R list(@LoginUser AdClientEntity user){
        List<AdClientAddressEntity> list = adClientAddressService.getListByClientid(user.getClientid());
        return R.ok().put("list", list);
    }
    /**
     * 信息
     */
    @Login
    @GetMapping("/info")
    @ApiOperation("详情")
    public R info(Long id){
        AdClientAddressEntity address = adClientAddressService.getById(id);
        return R.ok().put("info", address);
    }
    /**
     * 删除
     */
    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(Long id){
        adClientAddressService.removeById(id);
        return R.ok();
    }
    /**
     * 编辑或者添加
     */
    @Login
    @PostMapping("/updOrSave")
    @ApiOperation("编辑或者添加")
    public R updOrSave(@RequestBody AdClientAddressEntity entity, @LoginUser AdClientEntity user){
        if(entity.getId()!=null){
            entity.setUpdateTime(new Date());
            if(entity.getIsDefault().equals(1)){
                adClientAddressService.updIsDefaultByClientId(user.getClientid());
            }
            adClientAddressService.updateById(entity);
        }else{
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            entity.setClientId(user.getClientid());
            if(entity.getIsDefault().equals(1)){
                adClientAddressService.updIsDefaultByClientId(user.getClientid());
            }
            adClientAddressService.save(entity);
        }
        return R.ok();
    }

}
