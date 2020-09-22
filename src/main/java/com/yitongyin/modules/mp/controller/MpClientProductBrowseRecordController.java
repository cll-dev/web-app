package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.annotion.Login;
import com.yitongyin.common.annotion.LoginUser;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.entity.AdClientProductBrowseRecordEntity;
import com.yitongyin.modules.ad.form.SupSpecKeyValueMode;
import com.yitongyin.modules.ad.service.AdClientProductBrowseRecordService;
import com.yitongyin.modules.mp.View.SceneBrowSe;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 客户产品浏览记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@RestController
@RequestMapping("smp/browserecord")
public class MpClientProductBrowseRecordController {
    @Autowired
    private AdClientProductBrowseRecordService adClientProductBrowseRecordService;

    /**
     * 列表
     */
    @Login
    @GetMapping("/list")
    @ApiOperation("产品浏览记录表")
    public R list(@LoginUser AdClientEntity user, Long merchantId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        Date now = new Date();
        String endTime = sdf.format(now);
        cal.setTime(now);
        cal.add(Calendar.MONTH,-2);
        Date time=cal.getTime();
        String startTime =sdf.format(time);
        List<AdClientProductBrowseRecordEntity> list = adClientProductBrowseRecordService.getListPriceByClientIdAndMerchantId(user.getClientid()
                ,merchantId,startTime,endTime);
        Map<Date,List<AdClientProductBrowseRecordEntity>> map=list.stream().collect(Collectors.groupingBy(AdClientProductBrowseRecordEntity::getBrowseDate));
        List<SceneBrowSe> browSeList = new ArrayList<>();
        SceneBrowSe browSe = null;
        for (Date key: map.keySet()) {
            browSe = new SceneBrowSe();
            browSe.setTime(key);
            browSe.setList(map.get(key));
            browSeList.add(browSe);
        }
        List<SceneBrowSe> sortBrowSeList = browSeList.stream().sorted(Comparator.comparing(SceneBrowSe::getTime).reversed()).collect(Collectors.toList());
        return R.ok().put("list", sortBrowSeList);
    }
    /**
     * 产品浏览+1
     */
    @Login
    @GetMapping("/add")
    @ApiOperation("产品浏览记录表产品浏览+1")
    public R browse(@RequestAttribute("clientId") Long clientId, Long merProductId, Long merchantId){
        AdClientProductBrowseRecordEntity entity = new AdClientProductBrowseRecordEntity();
        entity.setClientId(clientId);
        entity.setMerchantProductId(merProductId);
        entity.setBrowseDate(new Date());
        entity.setMerchantId(merchantId);
        if(adClientProductBrowseRecordService.addOneByTime(entity)){
            return R.ok();
        }
        return R.error();
    }

}
