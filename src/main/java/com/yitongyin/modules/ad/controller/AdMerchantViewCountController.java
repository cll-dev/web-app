package com.yitongyin.modules.ad.controller;


import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantViewCountEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantViewCountService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 商户访客数量表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:05:24
 */
@RestController
@RequestMapping("ad/visitors")
public class AdMerchantViewCountController extends AbstractController{


    @Autowired
    private AdMerchantViewCountService adMerchantViewCountService;
    @Autowired
    private TMerchantService tMerchantService;

    @ApiOperation("获取商家当天访客数")
    @RequestMapping(value = "/viewToday", method = RequestMethod.GET)
    @ResponseBody
    public R getCountToday() {
        TMerchantEntity merchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(merchantEntity==null)
        return R.error("该商户账号不存在") ;
        AdMerchantViewCountEntity entity=adMerchantViewCountService.findLastCount(merchantEntity.getMerchantid());
        if(entity==null)return  R.ok().put("countToday",0);
        return R.ok().put("countToday",entity.getViewCount());
    }
    @ApiOperation("获取商家一周访客数")
    @RequestMapping(value = "/viewWeek", method = RequestMethod.GET)
    @ResponseBody
    public R getCountWeek() throws ParseException{
        TMerchantEntity merchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(merchantEntity==null) return R.error("该商户账号不存在") ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        List<AdMerchantViewCountEntity> valList= new ArrayList<>();
        for (int i=6;i>=0;i--) {
            AdMerchantViewCountEntity entity = new AdMerchantViewCountEntity();
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DATE,-i);
            Date tm=cal.getTime();
            String time =sdf.format(tm);
            entity.setMerchantId(merchantEntity.getMerchantid());
            entity.setViewCount(0);
            entity.setViewDate(sdf.parse(time));
            valList.add(entity);
        }
        String startTime=sdf.format(d);
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-6);
        Date time=cal.getTime();
        String endTime =sdf.format(time);
        List<AdMerchantViewCountEntity>  entityList=adMerchantViewCountService.findWeekCount(merchantEntity.getMerchantid(),endTime,startTime);
        entityList.addAll(valList);
        List<AdMerchantViewCountEntity> list = entityList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdMerchantViewCountEntity::getViewDate))), ArrayList::new)
        );
        return R.ok().put("countWeek",list);
    }

}
