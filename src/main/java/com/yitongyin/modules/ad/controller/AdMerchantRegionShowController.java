package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantRegionShowEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.RegionShow;
import com.yitongyin.modules.ad.service.AdMerchantRegionShowService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 商户广告素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 10:26:24
 */
@RestController
@RequestMapping("ad/merchantRegionShow")
public class AdMerchantRegionShowController extends AbstractController{
    @Autowired
    private AdMerchantRegionShowService adMerchantRegionShowService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 列表
     */
    @ApiOperation("根据广告位分页获取列表")
    @PostMapping("/list/region")
    public R list(@RequestParam Map<String, Object> params){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        PageUtils page = adMerchantRegionShowService.queryPage(params,tMerchantEntity.getMerchantid());
        List<AdMerchantRegionShowEntity> adMerchantRegionShowEntities = (List<AdMerchantRegionShowEntity>)page.getList();
        List<RegionShow> showList = adMerchantRegionShowEntities.stream().map(entity -> {
                   RegionShow regionShow = new RegionShow();
                   regionShow.setId(entity.getId());
                   regionShow.setHits(entity.getHits());
                   regionShow.setTitle(entity.getTitle());
                   if(entity.getCoverResId()!=null&&entity.getCoverResId()!=0){
                       AdOssEntity oss=adOssService.findById(entity.getCoverResId());
                           regionShow.setCoverUrl(oss==null?null:oss.getUrl());
                   }else {
                       regionShow.setCoverUrl(null);
                   }
                   return regionShow;
        }).collect(Collectors.toList());
        page.setList(showList);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation("广告位详情")
    @GetMapping("/info")
    public R info(Long id){
		AdMerchantRegionShowEntity adMerchantRegionShow = adMerchantRegionShowService.getById(id);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		adMerchantRegionShow.setUpdateDate(new Date());
		if(adMerchantRegionShow.getCoverResId()!=null){
		    AdOssEntity ossEntity = adOssService.findById(adMerchantRegionShow.getCoverResId());
		    adMerchantRegionShow.setCoverUrl(ossEntity==null?null:ossEntity.getUrl());
        }
        if(StringUtils.isNotBlank(adMerchantRegionShow.getProductHref())&&!adMerchantRegionShow.getProductHref().equals("#")){
            String prefix=StringUtils.substring(adMerchantRegionShow.getProductHref(),0,3);
            String a= StringUtils.substringAfterLast(adMerchantRegionShow.getProductHref(),"=");
            if(prefix.equals("/mp")) {
                try {
                    if (StringUtils.isNotBlank(a)) {
                        adMerchantRegionShow.setProId(Long.valueOf(a));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return R.error("数据异常");
                }
            }else{
                adMerchantRegionShow.setDynamicId(Long.valueOf(a));
            }
        }
        return R.ok().put("adMerchantRegionShow", adMerchantRegionShow);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    public R update(@RequestBody AdMerchantRegionShowEntity adMerchantRegionShow){
        adMerchantRegionShow.setHits(0);//修改之后，点击次数清0
        AdMerchantRegionShowEntity oldEntity=adMerchantRegionShowService.getById(adMerchantRegionShow.getId());
		adMerchantRegionShowService.updHref(adMerchantRegionShow);
		if(oldEntity.getCoverResId()!=null){
            adOssService.updateStatusById(oldEntity.getCoverResId(),2);
        }
		if(adMerchantRegionShow.getCoverResId()!=null)
        adOssService.updateStatusById(adMerchantRegionShow.getCoverResId(),1);
        return R.ok();
    }

    /**
     * 同步
    @ApiOperation("同步官网广告")     */

    @GetMapping("/syn")
    public R syncHeadquarter(){
        TMerchantEntity merchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(merchantEntity==null)
            return R.error("用户不存在");
        boolean res = adMerchantRegionShowService.syncHeaderquarter(merchantEntity.getMerchantid());
        if(res){
            return R.ok();
        }else{
            return R.error("同步失败");
        }
    }

}
