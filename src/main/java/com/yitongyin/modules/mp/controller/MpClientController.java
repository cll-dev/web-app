package com.yitongyin.modules.mp.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yitongyin.common.annotion.Login;
import com.yitongyin.common.annotion.LoginUser;
import com.yitongyin.common.utils.JwtUtils;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.SHA1;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.Config;
import com.yitongyin.modules.ad.form.SpecGroup;
import com.yitongyin.modules.ad.msg.MsgSenderHelper;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.ClientLogin;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 客户账号表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@RestController
@RequestMapping("smp/client")
public class MpClientController extends AbstractController {

    @Autowired
    private AdClientService adClientService;
    @Autowired
    private AdAuthCodeService adAuthCodeService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AdClientGroupService adClientGroupService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;
    @Autowired
    private AdMerchantServiceTypeService adMerchantServiceTypeService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private AdMerchantSpecValueService adMerchantSpecValueService;
    @Autowired
    private AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private  AdGroupService adGroupService;

    /**
     * 详情
     */
    @Login
    @GetMapping("/info")
    @ApiOperation("用户中心")
    public R getInfo(@LoginUser AdClientEntity user, Long merchantId){
        AdClientEntity entity = adClientService.getInfoByIdAndMerchantId(user.getClientid(),merchantId);
        return R.ok().put("info",entity);
    }
    /**
     * 设置密码
     */
    @Login
    @PostMapping("/setPassword")
    @ApiOperation("设置密码")
    public R setPassword(@LoginUser AdClientEntity user, @RequestBody ClientLogin entity){
        if(StrUtil.isBlank(entity.getPassword())){
            return R.error("密码不能为空");
        }
        AdAuthCodeEntity code= adAuthCodeService.findLastByPhone(entity.getMoblie(),5);
        if(code==null){
            return R.error("请先获取短信验证码");
        }

        if(!code.getAuthCode().equalsIgnoreCase(entity.getCode())){
            return  R.error("验证码错误");
        }else{
            if(code.getTimeoutDate().before(new Date())){
                return R.error("验证码已过期，请重新获取");
            }
        }
        String checkPwd = "";
        try {
            checkPwd = SHA1.getDigestOfString(entity.getPassword(), Config.PWD_KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user.setPassword(checkPwd);
        adClientService.updateById(user);
        return R.ok();
    }

    /**
     * 编辑
     */
    @Login
    @PostMapping("/upd")
    @ApiOperation("编辑资料")
    public R updInfo(@RequestAttribute("clientId") Long clientId, @RequestBody AdClientEntity entity){
        AdClientEntity oldEntity = adClientService.getById(clientId);
        entity.setClientid(oldEntity.getClientid());
        adClientService.updateById(entity);
        if(oldEntity.getAvatar()!=null){
            adOssService.updateStatusById(oldEntity.getAvatar(),2);
        }
        if (entity.getAvatar() != null) {
            this.adOssService.updateStatusById(entity.getAvatar(),1);
        }
        return R.ok();
    }
    /**
     * 验证码注册登录
     */
    @PostMapping("/login/code")
    @ApiOperation("验证码注册登录")
    public R codeLogin(@RequestBody ClientLogin login){
        if(StrUtil.isBlank(login.getMoblie())){
            return R.error("手机号不能为空");
        }
        if(!Validator.isMobile(login.getMoblie())){
            return R.error("手机号码格式不正确");
        }
        AdAuthCodeEntity code= adAuthCodeService.findLastByPhone(login.getMoblie(),4);
        if(code==null){
            return R.error("请先获取短信验证码");
        }

        if(!code.getAuthCode().equalsIgnoreCase(login.getCode())){
            return  R.error("验证码错误");
        }else{
            if(code.getTimeoutDate().before(new Date())){
                return R.error("验证码已过期，请重新获取");
            }
        }
        AdClientEntity entity = adClientService.getByMobile(login.getMoblie());
        if(entity==null){
           return adClientService.addClientByGroupId(login);
        }
        AdClientGroupEntity clientGroupEntity =adClientGroupService.getOneByClientIdAndGroupIds(entity.getClientid(),
                login.getMerchantId());
        List<Long> merchantIds=adClientGroupService.getMerchantIdsByClientIdAndGroup(entity.getClientid());
        //生成token
        String token = jwtUtils.generateToken(entity.getClientid());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        map.put("merchantIds",merchantIds);
        if(clientGroupEntity==null){
            AdGroupEntity groupEntity=adGroupService.getDefaultByMerchantId(login.getMerchantId());
            if(groupEntity==null){
                return R.error("数据错误");
            }
            AdClientGroupEntity addClientGroupEntity = new AdClientGroupEntity();
            addClientGroupEntity.setClientId(entity.getClientid());
            addClientGroupEntity.setGroupId(groupEntity.getGroupid());
            adClientGroupService.save(addClientGroupEntity);
        }
        return R.ok(map);
    }
    /**
     * 密码登录
     */
    @PostMapping("/login/password")
    @ApiOperation("密码登录")
    public R pwdLogin(@RequestBody ClientLogin login) throws IOException {
        if(StrUtil.isBlank(login.getMoblie())){
            return R.error("手机号不能为空");
        }
        if(!Validator.isMobile(login.getMoblie())){
            return R.error("手机号码格式不正确");
        }
        if(StrUtil.isBlank(login.getPassword())){
            return R.error("密码不能为空");
        }
        AdClientEntity entity = adClientService.getByMobile(login.getMoblie());
        if(entity==null){
            return R.error("手机号未注册");
        }
        if(StrUtil.isBlank(entity.getPassword())){
            return R.error("该用户暂未设置密码");
        }
        if(!entity.getPassword().equals(SHA1.getDigestOfString(login.getPassword(), Config.PWD_KEY))){
            return R.error("密码不正确");
        }
        AdClientGroupEntity clientGroupEntity =adClientGroupService.getOneByClientIdAndGroupIds(entity.getClientid(),
                login.getMerchantId());
        //生成token
        String token = jwtUtils.generateToken(entity.getClientid());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        if(clientGroupEntity==null){
            AdGroupEntity groupEntity=adGroupService.getDefaultByMerchantId(login.getMerchantId());
            if(groupEntity==null){
                return R.error("数据错误");
            }
            AdClientGroupEntity addClientGroupEntity = new AdClientGroupEntity();
            addClientGroupEntity.setClientId(entity.getClientid());
            addClientGroupEntity.setGroupId(groupEntity.getGroupid());
            adClientGroupService.save(addClientGroupEntity);
        }
        return R.ok(map);
    }
    /**
     * 用户登录注册获取验证码
     * @param mobile
     * @return
     */
    @ApiOperation("注册获取验证码")
    @RequestMapping(value = "/region/getAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public R getAuthCodeJson(String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return R.error("电话号码不能为空");
        }
        if(!Validator.isMobile(mobile)) {
            return R.error("电话号码格式不正确");
        }
        AdAuthCodeEntity entity = new AdAuthCodeEntity();
        entity.setAuthCode(RandomUtil.randomNumbers(6));
        entity.setTimeoutDate(DateUtil.offsetMinute(new Date(), 1));
        entity.setMobilePhone(mobile);
        entity.setType(4);
        if (MsgSenderHelper.sendRegistMsgCode(entity.getMobilePhone(), entity.getAuthCode())) {
            adAuthCodeService.insert(entity);
            return R.ok();
        }else{
            return R.error("发送失败");
        }

    }

    /**
     * 修改密码获取验证码
     * @param mobile
     * @return
     */
    @ApiOperation("修改密码获取验证码")
    @RequestMapping(value = "/updPassword/getAuthCode", method = RequestMethod.POST)
    public R updPwdgetAuthCodeJson(String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return R.error("电话号码不能为空");
        }
        if(!Validator.isMobile(mobile)) {
            return R.error("电话号码格式不正确");
        }
        AdAuthCodeEntity entity = new AdAuthCodeEntity();
        entity.setAuthCode(RandomUtil.randomNumbers(6));
        entity.setTimeoutDate(DateUtil.offsetMinute(new Date(), 1));
        entity.setMobilePhone(mobile);
        entity.setType(5);
        if (MsgSenderHelper.sendRegistMsgCode(entity.getMobilePhone(), entity.getAuthCode())) {
            adAuthCodeService.insert(entity);
            return R.ok();
        }else{
            return R.error("发送失败");
        }

    }
    /**
     * 授权加入用户组
     * @param merchantId
     * @return
     */
    @Login
    @ApiOperation("授权加入用户组")
    @RequestMapping(value = "/accredit", method = RequestMethod.POST)
    public R authorization(@RequestAttribute("clientId") Long clientId, Long merchantId) {
        adClientService.addClientToGroupByMerchantId(clientId,merchantId);
        return R.ok();
    }
    /**
     * 已登录的用户是否在当前商户用户组中
     * @param merchantId
     * @return
     */
    @Login
    @ApiOperation("已登录的用户是否在当前商户用户组中")
    @RequestMapping(value = "/judge", method = RequestMethod.POST)
    public R isCurrentGroup(@RequestAttribute("clientId") Long clientId, Long merchantId) {
        AdClientGroupEntity clientGroupEntity =adClientGroupService.getOneByClientIdAndGroupIds(clientId,
                merchantId);
        if(clientGroupEntity!=null){
            return R.ok().put("isCurrent",1);
        }else{
            return R.ok().put("isCurrent",0);
        }
    }
    /**
     * 获取当前用户所浏览商铺的产品价格倍数
     * @param merProductId
     * @param merchantId
     * @return
     */
    @Login
    @ApiOperation("获取当前用户所浏览商铺的产品价格倍数")
    @RequestMapping(value = "/core/rate", method = RequestMethod.GET)
    public R getPriceRate(@RequestAttribute("clientId") Long clientId, Long merProductId, Long merchantId) {
        AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
                ,merProductId,merchantId);

        return R.ok().put("rate",adGroupProductRateEntity==null?null:adGroupProductRateEntity.getPriceRate());
    }
    /**
     * 获取产品列表
     */
    @Login
    @ApiOperation("登录获取产品列表")
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params, Long typeId,@RequestAttribute("clientId") Long clientId){
        AdMerchantServiceTypeEntity typeEntity =adMerchantServiceTypeService.getById(typeId);
        if(typeEntity==null)
            return R.ok().put("page",null);
        PageUtils page=adMerchantProductService.getRateListByConditions(params,typeEntity.getServiceTypeId(),typeEntity.getMerchantId(),null,clientId);
        return R.ok().put("page", page);
    }
    /**
     * 登录获取规格键值列表
     */
    @Login
    @ApiOperation("登录获取规格键值列表")
    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public R groupList(@RequestAttribute("clientId") Long clientId,Long proId){

        AdMerchantProductEntity productEntity = adMerchantProductService.getById(proId);
        if(productEntity==null){
            return R.error("数据错误");
        }
        Long merchantId = productEntity.getMerchantId();
        Long merchantProId = productEntity.getId();
        List<AdMerchantSpecGroupEntity> list =adMerchantSpecGroupService.getMpListByProAndMerchant(
                productEntity.getMerchantId(),productEntity.getProductId());
        if(list==null){
            return R.ok().put("list",null);
        }
        List<SpecGroup> groups=list.stream().map(e->{
            SpecGroup specGroup = new SpecGroup();
            specGroup.setDeliveryDay(e.getDeliveryDay());
            AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
                    ,merchantProId,merchantId);
            if(adGroupProductRateEntity!=null){
                if(adGroupProductRateEntity.getIsShow()==1&&e.getPrice()!=null){
                    specGroup.setPrice(e.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                }
                specGroup.setIsShowPrice(adGroupProductRateEntity.getIsShow());
            }else{
                specGroup.setPrice(e.getPrice());
            }
            specGroup.setSpecNote(e.getSpecNote());
            specGroup.setUrl(e.getUrl());
            List<AdMerchantSpecValueEntity> values = adMerchantSpecValueService.getListByMsgId(e.getMsgid());
            List<Long> valueIds= values.stream().map(c->c.getSpecValueId()).collect(Collectors.toList());
            specGroup.setSpecValueIds(valueIds);
            return specGroup;
        }).collect(Collectors.toList());
        return R.ok().put("list",groups);
    }
    /**
     * 登录获取规格键值列表
     */
    @Login
    @ApiOperation("登录退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public R loginOut(@RequestAttribute("clientId") Long clientId){
        jwtUtils.generateToken(clientId);
        return R.ok();
    }

    /**
     * 更多推荐
     */
    @Login
    @GetMapping("/likeMore")
    @ApiOperation("更多推荐")
    public R likeMore(@RequestAttribute("clientId") Long clientId,@RequestParam Map<String, Object> params, Long merchantId){
        PageUtils pageUtils=adClientService.pageGetLikeMore(params,merchantId,clientId);
        return R.ok().put("page",pageUtils);
    }
}
