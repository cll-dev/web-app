package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.annotion.Login;
import com.yitongyin.common.annotion.LoginUser;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.entity.AdClientProductBrowseRecordEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdClientProductBrowseRecordService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 客户产品浏览记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@RestController
@RequestMapping("ad/browserecord")
public class AdClientProductBrowseRecordController extends  AbstractController{
    @Autowired
    private AdClientProductBrowseRecordService adClientProductBrowseRecordService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 列表
     */
    @Login
    @GetMapping("/list")
    @ApiOperation("用户详情中产品浏览记录")
    public R list(@RequestParam Map<String, Object> params,Long clientId){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("数据异常");
        }
        PageUtils page = adClientProductBrowseRecordService.queryPage(params,clientId,tMerchantEntity.getMerchantid());
        return R.ok().put("page", page);
    }


}
