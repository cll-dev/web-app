package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.AdMerchantFeedbackEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.CaseSubmit;
import com.yitongyin.modules.ad.service.AdMerchantFeedbackService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 商户反馈表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-02 10:22:06
 */
@RestController
@RequestMapping("ad/feedback")
public class AdMerchantFeedbackController extends AbstractController{
    @Autowired
    private AdMerchantFeedbackService adMerchantFeedbackService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;

    /**
     * 反馈
     */
    @ApiOperation("商户反馈")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R save(@RequestBody CaseSubmit caseSubmit){
        TMerchantEntity tMerchantEntity=tMerchantService.getInfoByUserId(getUserId());
        AdMerchantFeedbackEntity tFeedbackEntity=new AdMerchantFeedbackEntity();
        if(caseSubmit.getImgIds()!=null&&caseSubmit.getImgIds().size()!=0){
            List<String> idList= caseSubmit.getImgIds().stream().map(s -> String.valueOf(s)).collect(Collectors.toList());
            String strIds= UtilString.listToString(idList,",");
            tFeedbackEntity.setMediaResIds(strIds);
        }else{
            tFeedbackEntity.setMediaResIds(null);
        }
        tFeedbackEntity.setContent(caseSubmit.getContent());

        tFeedbackEntity.setMerchantId(tMerchantEntity.getMerchantid());
        tFeedbackEntity.setCreateTime(new Date());
        if(adMerchantFeedbackService.save(tFeedbackEntity)){
            if(caseSubmit.getImgIds()!=null&&caseSubmit.getImgIds().size()!=0) {
                List<Long> ids =caseSubmit.getImgIds().stream().map(e->Long.valueOf(e)).collect(Collectors.toList());
                adOssService.updateStatusByIds(ids,1);
            }
            return R.ok();
        }else{
            return R.error("提交失败");
        }
    }

}
