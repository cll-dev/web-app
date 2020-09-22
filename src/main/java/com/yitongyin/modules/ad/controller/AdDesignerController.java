package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdDesignerService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 设计师表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 17:14:43
 */
@RestController
@RequestMapping("ad/designer")
public class AdDesignerController extends AbstractController{
    @Autowired
    private AdDesignerService adDesignerService;
    @Autowired
    private TMerchantService tMerchantService;
    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("设计师列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adDesignerService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		//AdDesignerEntity adDesigner = adDesignerService.getById(id);
        AdDesignerEntity adDesigner = adDesignerService.findById(id);
        if(adDesigner!=null){
            adDesigner.setViewCounts(adDesigner.getViewCounts()+1);
            adDesignerService.updateById(adDesigner);
        }
        return R.ok().put("adDesigner", adDesigner);
    }

    /**
     * 增加联系次数
     */
    @GetMapping("/count")
    @ApiOperation("增加联系次数")
    public R addCount(Long id,Long merchantId){
        if(merchantId==null){
            return R.ok();
        }
        AdDesignerEntity designerEntity = adDesignerService.getById(id);
        if(designerEntity==null){
            return R.error("数据异常");
        }
        designerEntity.setContactedNumber(designerEntity.getContactedNumber()+1);
        if(adDesignerService.updateById(designerEntity)){
            TMerchantEntity tMerchantEntity= new TMerchantEntity();
            if(merchantId!=null){
                tMerchantEntity  = tMerchantService.getById(merchantId);
            }
            if(tMerchantEntity==null){
                return R.error("数据异常");
            }
            tMerchantEntity.setDesignerContactNumber(tMerchantEntity.getDesignerContactNumber()+1);
            tMerchantService.updateById(tMerchantEntity);
            return R.ok();
        }
        return R.error("服务异常");
    }
    /**
     * 信息
     */
    @ApiOperation("登录获取详情")
    @GetMapping("/getInfo")
    public R getInfo(){
        AdDesignerEntity adDesigner = adDesignerService.findByUserId(getUserId());
        adDesigner.setEmail(getUser().getEmail());
        return R.ok().put("info", adDesigner);
    }
}
