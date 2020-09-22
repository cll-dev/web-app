package com.yitongyin.modules.ad.controller;



import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierComplaintEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantSupplierComplaintService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 商户对应厂家的投诉表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
@RestController
@RequestMapping("ad/complaint")
public class AdMerchantSupplierComplaintController extends AbstractController{
    @Autowired
    private AdMerchantSupplierComplaintService adMerchantSupplierComplaintService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 提交反馈给厂家
     */
    @ApiOperation("提交反馈给厂家")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R update(@RequestBody AdMerchantSupplierComplaintEntity adMerchantSupplierComplaint){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        adMerchantSupplierComplaint.setMerchantId(tMerchantEntity.getMerchantid());
        if(adMerchantSupplierComplaint.getImgIds()!=null&&
        adMerchantSupplierComplaint.getImgIds().size()!=0){
          String ossIds=  UtilString.listToString(adMerchantSupplierComplaint.getImgIds());
          adMerchantSupplierComplaint.setOssIds(ossIds);
        }
		adMerchantSupplierComplaintService.subimtByMerIdAndSupId(adMerchantSupplierComplaint);
        return R.ok();
    }


}
