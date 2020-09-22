package com.yitongyin.modules.ad.controller;

import cn.hutool.core.util.StrUtil;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.SHA1;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.Config;
import com.yitongyin.modules.ad.form.UserLoginForm;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class AdLoginController extends AbstractController{
    @Autowired
    private AdUserService adUserService;
    @Autowired
    private AdUserTokenService adUserTokenService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdUserRoleService adUserRoleService;
    @Autowired
    private AdRoleService adRoleService;
    @Autowired
    private AdRoleMenuService adRoleMenuService;

    /**
     * 登录
     */
    @ApiOperation("登录")
    @PostMapping("/ad/login")
    public Map<String, Object> login(@RequestBody UserLoginForm form)throws IOException {

        if (StrUtil.isBlank(form.getUsername())) {
            return R.error("手机号不能为空");
        } else if (StrUtil.isBlank(form.getPassword())) {
            return R.error("密码不能为空");
        }

        //用户信息
        AdUserEntity user =adUserService.queryByUserName(form.getUsername());

        //账号不存在、密码错误
        if(user == null ) {
            return R.error("手机号不存在");
        }
        if(!user.getPassword().equals(SHA1.getDigestOfString(form.getPassword(), Config.PWD_KEY))){
            return R.error("密码不正确");
        }
        AdRoleEntity normalRole = adRoleService.getByName("普通商户");
        AdRoleEntity testRole = adRoleService.getByName("试用商户");
        AdUserRoleEntity userRoleEntity = adUserRoleService.getByUserId(user.getUserId());
        AdRoleEntity roleEntity = adRoleService.getById(userRoleEntity.getRoleId());

        //账号锁定
        if(user.getStatus() == 0){
            return R.error("账号已被锁定,请联系管理员");
        }
        if(user.getPostMerchantInfo() == 2){
            return R.error(510,"该账户未提交审核").put("userId",user.getUserId());
        }
        TMerchantEntity tMerchantEntity=tMerchantService.getInfoByUserId(user.getUserId());
        if(userRoleEntity.getRoleId().equals(normalRole.getRoleid())||userRoleEntity.getRoleId().equals(testRole.getRoleid())){
            if(tMerchantEntity.getVerifystatus()==4){
                return  R.error(511,"该账号未通过审核");
            }
            if(tMerchantEntity.getVerifystatus()==2){
                return  R.error(512,"该账号正在审核中");
            }
        }

        R r = adUserTokenService.createTokenByExpireTime(user.getUserId());
        List<AdMenuEntity> menuList = adRoleMenuService.getMenuByRole(userRoleEntity.getRoleId());
        r.put("menu",menuList).put("roleName",roleEntity.getRoleName());
        return r;
    }
    /**
     * 退出
     *
     */
    @ApiOperation("退出")
    @PostMapping("/ad/logout")
    public R logout() {
        //解除openid
        adUserTokenService.logout(getUserId());
        //adUserService.unbindOpenId(getUserId());
        return R.ok();
    }
    /**
     * 修改最后一次登录时间
     *
     */
    @ApiOperation("修改最后一次登录时间")
    @PostMapping("/upd/loginTime")
    public R loginTime() {
        AdUserEntity user = getUser();
        user.setLastLoginTime(new Date());
        adUserService.updateById(user);
       return R.ok();
    }
}
