package com.yitongyin.modules.ad.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yitongyin.common.utils.IdWorker;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.SHA1;
import com.yitongyin.modules.ad.entity.AdAuthCodeEntity;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.Config;
import com.yitongyin.modules.ad.form.RegForm;
import com.yitongyin.modules.ad.msg.MsgSenderHelper;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@RestController
@RequestMapping("/ad/user")
public class AdUserController extends AbstractController{
    @Autowired
    private AdUserService adUserService;
    @Autowired
    private AdAuthCodeService adAuthCodeService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private AdUserTokenService adUserTokenService;


    @ApiOperation("注册获取验证码")
    @RequestMapping(value = "/reg/getAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public R getAuthCodeJson(String mobilePhone) {
        //用户信息
        AdUserEntity user =adUserService.queryByUserName(mobilePhone);
        if (StrUtil.isBlank(mobilePhone)) {
            return R.error("电话号码不能为空");
        } else if (!Validator.isMobile(mobilePhone)) {
            return R.error("电话号码格式不正确");
        } else if (user !=null) {
            return R.error(508,"电话号码已被占用");
        }
            AdAuthCodeEntity entity = new AdAuthCodeEntity();
            entity.setAuthCode(RandomUtil.randomNumbers(6));
            entity.setTimeoutDate(DateUtil.offsetMinute(new Date(), 1));
            entity.setMobilePhone(mobilePhone);
            entity.setType(1);
            if (MsgSenderHelper.sendRegistMsgCode(entity.getMobilePhone(), entity.getAuthCode())) {
                adAuthCodeService.insert(entity);
                return R.ok();
            }else{
                return R.error("发送失败");
            }

    }
    @ApiOperation("注册/账号基本信息")
    @PostMapping("/reg/stepOne")
    public R register(@RequestBody RegForm form) {
        //表单校验
        if (StrUtil.isBlank(form.getUsername())) {
            return R.error("手机号不能为空");
        } else if (!Validator.isMobile(form.getUsername())) {
            return R.error("电话号码格式不正确");
        }else if (adUserService.queryByUserName(form.getUsername()) != null) {
            return R.error(508,"电话号码已注册");
        }else if (StrUtil.isBlank(form.getPassword())) {
            return R.error("密码不能为空");
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
        String checkPwd = "";
        try {
            checkPwd = SHA1.getDigestOfString(form.getPassword(), Config.PWD_KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AdUserEntity user=new AdUserEntity();
        user.setMobile(form.getUsername());
        user.setPassword(checkPwd);
        user.setStatus(1);
        user.setPostMerchantInfo(2);
        adUserService.userReg(user);
        return  R.ok().put("userId",user.getUserId());
    }
    @ApiOperation("注册/审核信息")
    @PostMapping("/reg/stepTwo")
    @ResponseBody
    public R registerAudit(TMerchantEntity form,Long userId) {
        if (StrUtil.isBlank(form.getMerchantname())) {
            return  R.error("商户名称不能为空");
        }
        if (form.getProvince()== null ) {
            return  R.error("省份不能为空");
        }
        if (form.getCity()== null ) {
            return  R.error("城市不能为空");
        }
        if (form.getCounty()== null ) {
            return  R.error("区县不能为空");
        }
        if (StrUtil.isBlank(form.getHousenumber())) {
            return  R.error("详细街道不能为空");
        }
        if (StrUtil.isBlank(form.getAddress())) {
            return  R.error("详细不能为空");
        }
       // AdUserEntity userEntity=adUserService.getById(userId);
        TMerchantEntity tMerchantEntity =tMerchantService.getOneByName(form.getMerchantname());
        if(tMerchantEntity!=null){
            return R.error("该用户名已存在");
        }
        form.setMerchantid(IdWorker.DEFAULT.nextId());
        form.setKind(1);
        form.setLinkname("未知");
        form.setLinkphone(adUserService.getById(userId).getMobile());
        form.setVerifystatus(2);
        form.setCreatedate(new Date());
        form.setUpdatedate(new Date());
        form.setUserId(userId);
        form.setDesignerContactNumber(0);
        form.setMaterialSendNumber(0);
        form.setFitterContactNumber(0);
        if(tMerchantService.save(form)){
            AdUserEntity user = new AdUserEntity();
            user.setUserId(userId);
            user.setPostMerchantInfo(1);
            adUserService.updatePhone(user);
        }
        if(form.getBusinessossid()!=null){
            adOssService.updateStatusById(form.getBusinessossid(),1);
        }
        return  R.ok();
    }
    @ApiOperation("修改密码获取验证码")
    @RequestMapping(value = "/upPwd/getAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public R getAuthCodeUpdPwd(String mobilePhone) {
        //用户信息
        AdUserEntity user =adUserService.queryByUserName(mobilePhone);
        if (StrUtil.isBlank(mobilePhone)) {
            return R.error("电话号码不能为空");
        } else if (!Validator.isMobile(mobilePhone)) {
            return R.error("电话号码格式不正确");
        } else if (user ==null) {
            return R.error("该手机号未注册");
        }
        AdAuthCodeEntity entity = new AdAuthCodeEntity();
        entity.setAuthCode(RandomUtil.randomNumbers(6));
        entity.setTimeoutDate(DateUtil.offsetMinute(new Date(), 5));
        entity.setMobilePhone(mobilePhone);
        entity.setType(2);
        if (MsgSenderHelper.sendRegistMsgCode(entity.getMobilePhone(), entity.getAuthCode())) {
            adAuthCodeService.insert(entity);
            return R.ok();
        }else{
            return R.error("发送失败");
        }

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

    @ApiOperation("WX绑定验证手机号是否注册")
    @RequestMapping(value = "/bindWx/ValidPhone", method = RequestMethod.POST)
    @ResponseBody
    public R validPhoneIsRegion(String phone) {
        AdUserEntity userEntity = adUserService.queryByUserName(phone);
        if (StrUtil.isBlank(phone)) {
            return R.error("电话号码不能为空");
        }
        if (!Validator.isMobile(phone)) {
            return R.error("电话号码格式不正确");
        }
        if (userEntity ==null) {
            return R.error(513,"该账号未注册");
        }
        //账号锁定
        if(userEntity.getStatus() == 0){
            return R.error("账号已被锁定,请联系管理员");
        }
        if(userEntity.getPostMerchantInfo() == 2){
            return R.error(510,"该账户未提交审核").put("userId",userEntity.getUserId());
        }
        TMerchantEntity tMerchantEntity=tMerchantService.getInfoByUserId(userEntity.getUserId());
        if(tMerchantEntity.getVerifystatus()==2){
            return  R.error(512,"该账号正在审核中");
        }
        if(tMerchantEntity.getVerifystatus()==4){
            return  R.error(511,"该账号未通过审核");
        }

        return R.ok();
    }
    @ApiOperation("绑定手机获取验证码")
    @RequestMapping(value = "/bindWx/getAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public R getAuthCodeBindWx(String mobilePhone) {

        AdAuthCodeEntity entity = new AdAuthCodeEntity();
        entity.setAuthCode(RandomUtil.randomNumbers(6));
        entity.setTimeoutDate(DateUtil.offsetMinute(new Date(), 5));
        entity.setMobilePhone(mobilePhone);
        entity.setType(3);
        if (MsgSenderHelper.sendRegistMsgCode(entity.getMobilePhone(), entity.getAuthCode())) {
            adAuthCodeService.insert(entity);
            return R.ok();
        }else{
            return R.error("发送失败");
        }

    }
    @ApiOperation("绑定手机")
    @PostMapping("/bindWx")
    public R bindWx(@RequestBody RegForm form) {
        AdAuthCodeEntity code= adAuthCodeService.findLastByPhone(form.getUsername(),3);
        if(code==null){
            return R.error("验证码不能为空");
        }

        if(!code.getAuthCode().equalsIgnoreCase(form.getVefCode())){
            return  R.error("验证码错误");
        }else{
            if(code.getTimeoutDate().before(new Date())){
                return R.error("验证码已过期，请重新获取");
            }
        }
        adUserService.bindOpenId(form.getUsername(),form.getOpenId());
        AdUserEntity user=adUserService.queryBuOpenId(form.getOpenId());
        if(user==null)return R.error("数据错误");
        R r = adUserTokenService.createToken(user.getUserId());
        return r;
    }
    @ApiOperation("解绑微信")
    @PostMapping("/unBindWx")
    public R unBindWx() {
        AdUserEntity userEntity = adUserService.getById(getUserId());
        if(userEntity.getOpenId()==null){
            return R.error("该账号未绑定微信");
        }
        adUserService.unbindOpenId(getUserId());
        return R.ok();
    }
    @ApiOperation("获取是否绑定微信")
    @GetMapping("/getBindWxSta")
    public R getBindWxSta() {
        AdUserEntity userEntity = adUserService.getById(getUserId());
        if(userEntity.getOpenId()==null){
            return R.ok().put("state",0);
        }else {
            return R.ok().put("state",1);
        }

    }

}
