package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.config.ManagerConfig;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.inti.SpringContextSupport;
import com.yitongyin.modules.ad.service.AdUserService;
import com.yitongyin.modules.ad.service.AdUserTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController extends  AbstractController{

    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    private AdUserService adUserService;
    @Autowired
    private AdUserTokenService adUserTokenService;

    @ApiOperation("一键登录授权连接")
    @GetMapping("/authorize")
    public R authorize(@RequestParam(value = "state",required = false) String state){
        ManagerConfig managerConfig = SpringContextSupport.getBean(ManagerConfig.class);
        String url = managerConfig.getWxUrl();
        String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(state));
        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);
        Map<String,Object> map=new HashMap<>();
        map.put("url",redirectURL);
        return R.ok(map);
    }

    @ApiOperation("获取用户openId")
    @GetMapping("/userInfo")
    public R userInfo(@RequestParam("code") String code,
                           @RequestParam(value = "state",required = false) String state) throws Exception {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        AdUserEntity user= adUserService.queryBuOpenId(openId);
        if(user!=null){
            R r = adUserTokenService.createToken(user.getUserId());
            return r.put("isLogin",true);
        }else{
            return R.ok().put("isLogin",false).put("openId",openId);
        }
    }

        /**
         * 获取微信签名
         * @param url
         * @return
         */
        @ApiOperation("获取微信签名")
        @RequestMapping(value = "/getSign", method = RequestMethod.POST)
        public R getSignature(String url) throws WxErrorException{
            WxJsapiSignature sign = wxMpService.createJsapiSignature(url);
            return R.ok().put("data",sign);
        }

}
