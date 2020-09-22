package com.yitongyin.modules.ad.controller;

import com.alibaba.fastjson.JSON;
import com.yitongyin.common.utils.ExcelUtils;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.BusMaterialEntity;
import com.yitongyin.modules.ad.form.BusMaterialConditions;
import com.yitongyin.modules.ad.form.BusMaterialView;
import com.yitongyin.modules.ad.form.MaterialIM;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.BusMaterialService;
import com.yitongyin.nosql.elasticsearch.document.EsMaterial;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 09:48:32
 */
@RestController
@RequestMapping("bus/material")
public class BusMaterialController {
    @Autowired
    private BusMaterialService busMaterialService;
    @Autowired
    private AdOssService adOssService;
    @Resource
    Environment environment;

    /**
     * 列表
     */
    @ApiOperation("获取素材列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R list(@RequestParam Map<String, Object> params, @RequestBody BusMaterialConditions conditions) {
        PageUtils page = busMaterialService.queryPage1(params, conditions);
        List<EsMaterial> entities = (List<EsMaterial>) page.getList();
        List<BusMaterialView> views = entities.stream().map(busMaterialEntity -> {
            BusMaterialView view = new BusMaterialView();
            view.setId(busMaterialEntity.getId());
            view.setTitle(busMaterialEntity.getTitle());
            if(busMaterialEntity.getCoverResId()!=null){
                AdOssEntity ossEntity = adOssService.findById(busMaterialEntity.getCoverResId());
                view.setImgUrl(ossEntity==null?"":ossEntity.getUrl());
            }
            view.setType(busMaterialEntity.getFileType());
            switch (busMaterialEntity.getFileType()){
                case 1: view.setTypeStr("PSD");
                       break;
                case 2: view.setTypeStr("AI");
                        break;
                case 3: view.setTypeStr("CDR");
                        break;
                case 4: view.setTypeStr("EPS");
                     break;
                case 5: view.setTypeStr("TIF");
                    break;
                case 6: view.setTypeStr("JPG");
                    break;
                case 7: view.setTypeStr("PDF");
                    break;
                 default: view.setTypeStr("未知");
            }
            return view;
        }).collect(Collectors.toList());
        page.setList(views);
        return R.ok().put("page", page);
    }

    /**
     * 每天0点同步素材到ES
     * @return
     */
//    @Scheduled(cron ="0 0 0 * * *")
//    public R importAllList(){
//        busMaterialService.deleteAll();
//        int count = busMaterialService.importAll();
//        return R.ok().put("count", count);
//    }

    @ApiOperation(value = "导入ES所有的素材数据")
    @RequestMapping(value="/importAllListEs",method = RequestMethod.POST)
    public R importAllListEs(){
        busMaterialService.deleteAll();
        int count = busMaterialService.importAll();
        return R.ok().put("count", count);
    }

    @ApiOperation(value = "删除ES所有的素材数据")
    @RequestMapping(value="/deleteAll",method = RequestMethod.POST)
    public R deleteAll(){
        busMaterialService.deleteAll();
        return R.ok();
    }

    @ApiOperation("导入素材Excel行业")
    @RequestMapping(value = "/importMaterialExlIndustry", method = RequestMethod.POST)
    @ResponseBody
    public void importMaterialExlIndustry(MultipartFile file) throws Exception {
        long t1 = System.currentTimeMillis();
        List<MaterialIM> list = ExcelUtils.readExcel("", MaterialIM.class, file);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        list.forEach(
                b -> System.out.println(JSON.toJSONString(b))
        );
        busMaterialService.importMaterialIndustrySync(list);
        System.out.println("同步导入完成");
    }

    @ApiOperation("导入素材Excel")
    @RequestMapping(value = "/importMaterialExl", method = RequestMethod.POST)
    @ResponseBody
    public void importMaterialExl(MultipartFile file) throws Exception {
        long t1 = System.currentTimeMillis();
        List<MaterialIM> list = ExcelUtils.readExcel("", MaterialIM.class, file);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        list.forEach(
                b -> System.out.println(JSON.toJSONString(b))
        );
        //busMaterialService.importMaterial(list);
        busMaterialService.importMaterialSync(list);
        System.out.println("同步导入完成");
    }

    @ApiOperation("查看未导入的数据")
    @RequestMapping(value="/checkImportExcel",method = RequestMethod.POST)
    @ResponseBody
    public void checkImportExcel(MultipartFile file) throws Exception{
        List<MaterialIM> list = ExcelUtils.readExcel("", MaterialIM.class, file);
        for(MaterialIM im:list){
            List<BusMaterialEntity> entity = busMaterialService.findByTitle(im.getTagKey());
            if(entity==null||entity.size()<=0){
                System.out.println("未导入项："+im.getTagKey());
            }
        }
    }

    @ApiOperation("查看不存在的文件")
    @RequestMapping(value="/checkExistFileForExcel",method = RequestMethod.POST)
    @ResponseBody
    public void checkExistFileForExcel(MultipartFile file){
        List<MaterialIM> list = ExcelUtils.readExcel("", MaterialIM.class, file);
        String uploadPrefix = environment.getProperty("material-import-path");
        for(MaterialIM im:list){
            String filePath = uploadPrefix + im.getFilePath();
            String coverPath = uploadPrefix + im.getCoverImgPath();
            File diskFile = new File(filePath);
            if(!diskFile.exists()){
                System.out.println(filePath);
            }
            File coverFile = new File(coverPath);
            if(!coverFile.exists()){
                System.out.println(coverPath);
            }
        }
    }

    @ApiOperation("查看配置material-import-path")
    @RequestMapping(value = "/testEnv", method = RequestMethod.POST)
    @ResponseBody
    public void testProperty(){
        String property = environment.getProperty("material-import-path");
        System.out.println("2-1. 通过注入Environment获取值: " + property);
    }


}
