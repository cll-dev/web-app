package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.RegxUtil;
import com.yitongyin.modules.ad.entity.AdMerchantDynamicEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantDynamicService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 商户动态表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-01 09:14:38
 */
@RestController
@RequestMapping("ad/dynamic")
public class AdMerchantDynamicController extends  AbstractController{
    @Autowired
    private AdMerchantDynamicService adMerchantDynamicService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("数据异常");
        }
        PageUtils page = adMerchantDynamicService.queryPage(params,tMerchantEntity.getMerchantid());
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AdMerchantDynamicEntity adMerchantDynamic = adMerchantDynamicService.getById(id);
		adMerchantDynamic.setViewCounts(adMerchantDynamic.getViewCounts()+1);
		adMerchantDynamicService.updateById(adMerchantDynamic);
        return R.ok().put("info", adMerchantDynamic);
    }

    /**
     * 保存
     */
    @ApiOperation("保存添加")
    @PostMapping("/saveOrUpd")
    public R save(@RequestBody AdMerchantDynamicEntity adMerchantDynamic){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("参数错误");
        }
        if(StringUtils.isBlank(adMerchantDynamic.getTitle())){
            return R.error("标题不能为空");
        }
         AdMerchantDynamicEntity getEntity= adMerchantDynamicService.getByTitle(adMerchantDynamic.getTitle(),tMerchantEntity.getMerchantid());
        if(adMerchantDynamic.getId()==null){
            if(getEntity!=null){
                return R.error("标题已存在");
            }
            adMerchantDynamic.setMerchantId(tMerchantEntity.getMerchantid());
            adMerchantDynamic.setCreateTime(new Date());
            adMerchantDynamic.setUpdateTime(new Date());
            adMerchantDynamicService.save(adMerchantDynamic);
            if(adMerchantDynamic.getContent()!=null){
                List<String> urls= RegxUtil.getUrl(adMerchantDynamic.getContent());
                if(urls!=null&&urls.size()>0){
                    adOssService.updateStatusByUrl(urls,1);
                }
            }
        }else{
            if(getEntity!=null&&!adMerchantDynamic.getId().equals(getEntity.getId())){
                return R.error("标题已存在");
            }
            AdMerchantDynamicEntity oldEntity= adMerchantDynamicService.getById(adMerchantDynamic.getId());
            adMerchantDynamic.setUpdateTime(new Date());
            adMerchantDynamicService.updateById(adMerchantDynamic);
            if(oldEntity.getContent()!=null){
                List<String> oldUrls= RegxUtil.getUrl(oldEntity.getContent());
                if(oldUrls!=null&&oldUrls.size()>0){
                    adOssService.updateStatusByUrl(oldUrls,2);
                }
            }
            if(adMerchantDynamic.getContent()!=null){
                List<String> urls= RegxUtil.getUrl(adMerchantDynamic.getContent());
                if(urls!=null&&urls.size()>0){
                    adOssService.updateStatusByUrl(urls,1);
                }
            }

        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    public R delete(Long id){
        AdMerchantDynamicEntity old=adMerchantDynamicService.getById(id);
        if(old==null){
            return R.error("数据异常");
        }
		if(adMerchantDynamicService.removeById(id)){
            if(old.getContent()!=null){
                List<String> oldUrls= RegxUtil.getUrl(old.getContent());
                if(oldUrls!=null&&oldUrls.size()>0){
                    adOssService.updateStatusByUrl(oldUrls,2);
                }
            }
        }
        return R.ok();
    }

}
