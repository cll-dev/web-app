package com.yitongyin.modules.ad.controller;


import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.RegxUtil;
import com.yitongyin.common.utils.SHA1;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.Config;
import com.yitongyin.modules.ad.form.MerchantReport;
import com.yitongyin.modules.ad.form.RegForm;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 16:04:20
 */
@RestController
@RequestMapping("ad/merchant")
public class TMerchantController extends  AbstractController{
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private AdUserService adUserService;
    @Autowired
    private AdAuthCodeService adAuthCodeService;
    @Autowired
    private AdDesignerService adDesignerService;
    /**
     * 获取商户信息
     */
    @ApiOperation("获取商户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public R getInfo(

    ) {
        AdUserEntity userEntity = getUser();
        if(userEntity==null){
            return R.error("token已失效");
        }
        TMerchantEntity entity =tMerchantService.getInfoByUserId(userEntity.getUserId());
        entity.setEmail(userEntity.getEmail());
        Map<String, Object> map = new HashMap<>();
        if(entity.getDesignossid()!=null){
            AdOssEntity ossEntity =adOssService.findById(entity.getDesignossid());
            entity.setDesignUrl(ossEntity);
        }
        if(entity.getLogoossid()!=null){
            AdOssEntity ossEntity =adOssService.findById(entity.getLogoossid());
            entity.setLogoUrl(ossEntity);
        }
        if(entity.getSaleossid()!=null){
            AdOssEntity ossEntity =adOssService.findById(entity.getSaleossid());
            entity.setSaleUrl(ossEntity);
        }
        if(entity.getShopphotoid()!=null){
            AdOssEntity ossEntity =adOssService.findById(entity.getShopphotoid());
            entity.setShopphotoUrl(ossEntity);
        }
        map.put("info",entity);
        return R.ok(map);
    }
    /**
     * 保存商户信息
     */
    @ApiOperation("获取商户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R saveInfo(@RequestBody TMerchantEntity entity) {
       if (entity.getMerchantid() == null) {
           return R.error("参数Id不能为空");
       }
       tMerchantService.updateInfo(entity);
       return  R.ok();

    }
    /**
     * 保存商户经纬度
     */
    @ApiOperation("获取商户信息")
    @RequestMapping(value = "/saveLongitude", method = RequestMethod.POST)
    public R saveLongitude(@RequestBody TMerchantEntity entity) {
        if (entity.getMerchantid() == null) {
            return R.error("参数Id不能为空");
        }
        tMerchantService.updateById(entity);
        return  R.ok();
    }
    /**
     * 修改商户简介
     */
    @ApiOperation("修改商户简介")
    @RequestMapping(value = "/saveDes", method = RequestMethod.POST)
    public R updDes(@RequestBody TMerchantEntity entity) {
        if (entity.getMerchantid() == null) {
            return R.error("参数Id不能为空");
        }
        tMerchantService.updateDes(entity);
        return  R.ok();

    }
    /**
     * 切换邮箱
     */
    @ApiOperation("切换邮箱")
    @RequestMapping(value = "/email/update", method = RequestMethod.POST)
    @ResponseBody
    public R updateEmail(String email) {
        if(StrUtil.isBlank(email)){
            return R.error("邮箱不能为空");
        }
        if(!Validator.isEmail(email)){
            return R.error("邮箱格式不正确");
        }
        AdUserEntity userEntity = adUserService.getOneByEmail(email);
        if(userEntity != null){
            return R.error("邮箱已被占用");
        }
        AdUserEntity userEntity1 = new AdUserEntity();
        userEntity1.setUserId(getUserId());
        userEntity1.setEmail(email);
        adUserService.updateById(userEntity1);
        return  R.ok();

    }
    @ApiOperation("切换绑定手机")
    @PostMapping("/upPhone")
    public R upPhone(@RequestBody RegForm form) {
        System.out.println(form.getUsername());
        //表单校验
        if (StrUtil.isBlank(form.getUsername())) {
            return R.error("手机号不能为空");
        } else if (!Validator.isMobile(form.getUsername())) {
            return R.error("电话号码格式不正确");
        }else if (adUserService.queryByUserName(form.getUsername()) != null) {
            return R.error("该手机号已被绑定");
        }
        AdAuthCodeEntity code= adAuthCodeService.findLastByPhone(form.getUsername(),1);
        if(code==null){
            return R.error("请先获取短信验证码");
        }
        if(!code.getAuthCode().equalsIgnoreCase(form.getVefCode())){
            return  R.error("验证码错误");
        }else{
            if(code.getTimeoutDate().before(new Date())){
                return R.error("验证码已过期，请重新获取");
            }
        }

        AdUserEntity user=new AdUserEntity();
        user.setMobile(form.getUsername());
        user.setUserId(getUserId());
        adUserService.updatePhone(user);
        return R.ok();
    }
    @ApiOperation("修改密码")
    @PostMapping("/upPwd")
    public R upPwd(@RequestBody RegForm form) {
        //表单校验
        if (StrUtil.isBlank(form.getUsername())) {
            return R.error("手机号不能为空");
        } else if (!Validator.isMobile(form.getUsername())) {
            return R.error("电话号码格式不正确");
        }else if (adUserService.queryByUserName(form.getUsername()) == null) {
            return R.error("该手机号不存在");
        }else if (StrUtil.isBlank(form.getPassword())) {
            return R.error("密码不能为空");
        }else if (form.getPassword().length()<6) {
            return R.error("密码长度必须为6位以上");
        }

        AdAuthCodeEntity code= adAuthCodeService.findLastByPhone(form.getUsername(),2);
        if(code==null){
            return R.error("请先获取短信验证码");
        }

        if(!code.getAuthCode().equalsIgnoreCase(form.getVefCode())){
            return  R.error("验证码错误");
        }else{
            if(code.getTimeoutDate().before(new Date())){
                return R.error("验证码已过期，请重新获取");
            }
        }
        String checkPwd = "";
        try {
            checkPwd = SHA1.getDigestOfString(form.getPassword(), Config.PWD_KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AdUserEntity user=new AdUserEntity();
        user.setMobile(form.getUsername());
        user.setPassword(checkPwd);
        adUserService.updatePwd(user);
        return R.ok();
    }
    @ApiOperation("修改店铺颜色")
    @PostMapping("/updColor")
    public R updColor(String color) {
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantService.updColor(color,tMerchantEntity.getMerchantid())){
            return R.ok();
        }else{
           return R.error("保存失败");
        }
    }

    @ApiOperation("报表")
    @GetMapping("/report")
    public R getReport() {
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("数据异常");
        }
        MerchantReport report =tMerchantService.getReport(tMerchantEntity.getMerchantid());
        return R.ok().put("report",report);
    }
    /**
     * 信息
     */
    @ApiOperation("登录获取详情")
    @GetMapping("/design/info")
    public R getInfos(){
        AdDesignerEntity adDesigner = adDesignerService.findByUserId(getUserId());
        adDesigner.setEmail(getUser().getEmail());
        return R.ok().put("info", adDesigner);
    }
     /**
      * 设计师修改详情
     */
//    @ApiOperation("登录获取详情")
//    @PostMapping("/design/upd")
//    public R designUpd(){
//
//    }

}
