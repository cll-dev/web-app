package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;


@RestController
@RequestMapping("mp/wechat")
@Slf4j
public class MpWechatController extends  AbstractController{

    @Autowired
    protected WxMpService wxMpService;


        /**
         * 获取微信签名
         * @param url
         * @return
         */
        @ApiOperation("获取微信签名")
        @RequestMapping(value = "/getSign", method = RequestMethod.POST)
        public R getSignature(String url) throws WxErrorException{
            url= StringUtils.replace(url,"&amp;","&");
            WxJsapiSignature sign = wxMpService.createJsapiSignature(url);
            return R.ok().put("data",sign);
        }
    /**
     * 1、用于公众号【服务器配置】验证——然后才有token（坑）
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @ApiOperation("验证微信签名")
    @RequestMapping(value = "/core", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public
    String authGet(@RequestParam("signature") String signature,
                   @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
                   @RequestParam("echostr") String echostr) {
        logger.info("接收到来自微信服务器的认证消息：[{},{},{},{}]", signature, timestamp, nonce, echostr);
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";

    }
}
