package com.yitongyin.modules.ad.controller;


import com.yitongyin.common.utils.IdWorker;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.TFeedbackEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.TFeedbackService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 17:20:54
 */
@RestController
@RequestMapping("ad/tfeedback")
public class TFeedbackController extends  AbstractController{
    @Autowired
    private TFeedbackService tFeedbackService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 反馈
     */
    @ApiOperation("反馈")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public R save(Long[] ids,String content){
		List<String> idList= Arrays.asList(ids).stream().map(s -> String.valueOf(s)).collect(Collectors.toList());
        String strIds=UtilString.listToString(idList,",");
        TFeedbackEntity tFeedbackEntity=new TFeedbackEntity();
        tFeedbackEntity.setFeedbackid(IdWorker.DEFAULT.nextId());
        tFeedbackEntity.setContent(content);
        tFeedbackEntity.setAttachmentids(strIds);
        TMerchantEntity tMerchantEntity=tMerchantService.getInfoByUserId(1);
        tFeedbackEntity.setMerchantId(tMerchantEntity.getMerchantid());
        tFeedbackEntity.setLinkman(tMerchantEntity.getLinkname());
        tFeedbackEntity.setMobilephone(tMerchantEntity.getLinkphone());
        tFeedbackEntity.setFeedbackdate(new Date());
        tFeedbackService.save(tFeedbackEntity);
        return R.ok();
    }



}
