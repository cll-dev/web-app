package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.IdWorker;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.RegxUtil;
import com.yitongyin.modules.ad.entity.BusProductEntity;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.BusProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:01:45
 */
@RestController
@RequestMapping("ad/busproduct")
public class BusProductController {
    @Autowired
    private BusProductService busProductService;
    @Autowired
    private AdOssService adOssService;

    @ApiOperation("添加产品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public R save(BusProductEntity busProduct){
        busProduct.setProductid(IdWorker.DEFAULT.nextId());
		busProductService.save(busProduct);
		if(busProduct.getOssid()!=null){
            adOssService.updateStatusById(busProduct.getOssid(),1);
        }
		if(busProduct.getProductcontent()!=null){
            List<String> urls= RegxUtil.getUrl(busProduct.getProductcontent());
            if(urls!=null&&urls.size()>0){
                adOssService.updateStatusByUrl(urls,1);
            }
        }
        return R.ok();
    }


}
