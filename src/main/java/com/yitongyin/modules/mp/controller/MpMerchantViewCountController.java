package com.yitongyin.modules.mp.controller;


import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdMerchantViewCountEntity;
import com.yitongyin.modules.ad.entity.AdMerchantViewIpEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantViewCountService;
import com.yitongyin.modules.ad.service.AdMerchantViewIpService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 商户访客数量表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:05:24
 */
@RestController
@RequestMapping("mp/visitors")
public class MpMerchantViewCountController extends AbstractController{


    @Autowired
    private AdMerchantViewCountService adMerchantViewCountService;
    @Autowired
    private AdMerchantViewIpService adMerchantViewIpService;
    @Autowired
    private TMerchantService tMerchantService;
    @ApiOperation("进入用户前端首页增加对应商户访客量")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R getCountToday(AdMerchantViewCountEntity entity) {
        if(entity.getMerchantId()==null)
            return R.error("参数错误");
        TMerchantEntity tMerchantEntity = tMerchantService.getById(entity.getMerchantId());
        if(tMerchantEntity==null||tMerchantEntity.getUserId()==null)
            return R.error("参数错误");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        entity.setViewDate(new Date());
        entity.setTime(sdf.format(new Date()));
        Boolean isFirst=true;
        List<Object> viewIds=adMerchantViewCountService.findByMerId(entity.getMerchantId());
        AdMerchantViewIpEntity ipEntity =adMerchantViewIpService.getByIpAndMerIdS(viewIds,entity.getIp());
        if(ipEntity!=null) {
            isFirst = false;
        }
        if(adMerchantViewCountService.saveOrUpdByIpAndToday(entity)){
           return R.ok().put("isFirst",isFirst);
        }else{
            return R.error("服务错误");
        }
    }


}
