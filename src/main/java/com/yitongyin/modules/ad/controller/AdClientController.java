package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.AdClientGroupChange;
import com.yitongyin.modules.ad.form.AdClientQuery;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 客户账号表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@RestController
@RequestMapping("ad/client")
public class AdClientController extends  AbstractController{
    @Autowired
    private AdClientService adClientService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdClientAddressService adClientAddressService;
    @Autowired
    private AdClientProductBrowseRecordService adClientProductBrowseRecordService;
    @Autowired
    private AdClientGroupService adClientGroupService;
    @Autowired
    private AdClientTagService adClientTagService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation("客户列表页")
    public R list(@RequestParam Map<String, Object> params, @RequestBody(required = false) AdClientQuery query){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("数据异常");
        }
        if(query!=null){
            query.setMerchantId(tMerchantEntity.getMerchantid());
            PageUtils page = adClientService.queryPage(params,query);
            return R.ok().put("page", page);
        }
        AdClientQuery queryNew = new AdClientQuery();
        queryNew.setMerchantId(tMerchantEntity.getMerchantid());
        PageUtils page = adClientService.queryPage(params,queryNew);
        return R.ok().put("page", page);
    }
    /**
     * 详情
     */
    @GetMapping("/info")
    @ApiOperation("客户详情页")
    public R getInfo(Long id){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("数据异常");
        }
        AdClientEntity entity = adClientService.getInfoByIdAndMerchantId(id,tMerchantEntity.getMerchantid());
        AdClientEntity tagClient = adClientTagService.getNamesByClientIdAndMerchantId(id,tMerchantEntity.getMerchantid());
        if(tagClient!=null&&StringUtils.isNotBlank(tagClient.getTagName())){
            List<String> nameList= UtilString.stringToList(tagClient.getTagName());
            List<String> tagList= UtilString.stringToList(tagClient.getTagIds());
            List<Long> tagIds=tagList.stream().map(e->Long.valueOf(e)).collect(Collectors.toList());
            entity.setTagNames(nameList);
            entity.setLongTagIds(tagIds);
        }
        List<AdClientAddressEntity> addressList= adClientAddressService.getListByClientid(id);
        List<AdClientProductBrowseRecordEntity> browList= adClientProductBrowseRecordService.getListByClientIdAndMerchantId(id,
                tMerchantEntity.getMerchantid());
        return R.ok().put("info",entity).put("address",addressList).put("pro",browList);
    }
    /**
     * 添加用户
     */
    @PostMapping("/group/add")
    @ApiOperation("商户组添加用户")
    public R add(@RequestBody List<AdClientGroupChange> list){
        if(adClientGroupService.addByClientIdAndGroupIds(list)){
            return R.ok();
        }
        return R.error();
    }
    /**
     * 删除用户
     */
    @PostMapping("/group/delete")
    @ApiOperation("商户组删除用户")
    public R delete(@RequestBody List<AdClientGroupChange> list){
        if(adClientGroupService.delByClientIdAndGroupIds(list)){
            return R.ok();
        }
        return R.error();
    }
    /**
     * 修改备注
     */
    @PostMapping("/group/noteUpd")
    @ApiOperation("修改备注")
    public R updGroupNote(@RequestBody AdClientGroupEntity entity){
        adClientGroupService.updateById(entity);
        return R.ok();
    }
}
