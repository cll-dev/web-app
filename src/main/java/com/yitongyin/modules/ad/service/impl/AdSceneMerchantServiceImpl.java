package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.dao.AdSceneMerchantDao;
import com.yitongyin.modules.ad.dao.AdSceneProjectDao;
import com.yitongyin.modules.ad.entity.AdSceneMerchantEntity;
import com.yitongyin.modules.ad.entity.AdSceneTypeEntity;
import com.yitongyin.modules.ad.service.AdSceneMerchantService;
import com.yitongyin.modules.ad.service.AdSceneProjectService;
import com.yitongyin.modules.ad.service.AdSceneTypeService;
import com.yitongyin.modules.mp.View.SceneMerchantSearch;
import com.yitongyin.modules.mp.View.SceneMerchantView;
import com.yitongyin.modules.mp.View.SceneProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("adSceneMerchantService")
public class AdSceneMerchantServiceImpl extends ServiceImpl<AdSceneMerchantDao, AdSceneMerchantEntity> implements AdSceneMerchantService {

    @Autowired
    AdSceneMerchantDao adSceneMerchantDao;
    @Autowired
    AdSceneProjectDao adSceneProjectDao;
    @Autowired
    AdSceneTypeService adSceneTypeService;
    @Autowired
    AdSceneProjectService adSceneProjectService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, SceneMerchantSearch search) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        String name=null;
        Long bitId=null;
        boolean isMostPro=false;
        boolean isMostLike=false;
        if(search!=null){
            name=search.getName();
            bitId=search.getBitId();
            isMostPro=search.getIsMostPro();
            isMostLike=search.getIsMostLike();
        }
        List<AdSceneMerchantEntity> list = adSceneMerchantDao.queryPage(name,bitId,isMostPro,isMostLike,start,limit);
        list.forEach(e->{
            String url = adSceneProjectService.getUrlOrderOneBySmId(e.getSmid());
            e.setUrl(url);
        });
        Integer count= adSceneMerchantDao.queryCountPage(name,bitId);
        PageUtils pageUtils = new PageUtils(list,count,limit,page);
        return pageUtils;
    }

    @Override
    public SceneMerchantView getInfoById(Map<String, Object> params) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        Long smId=Long.valueOf(params.get("id").toString());
        SceneMerchantView view = adSceneMerchantDao.getInfoById(smId);
        List<SceneProject> list = adSceneProjectDao.getListBySmId(smId,start,limit);
        list.forEach(e->{
            AdSceneTypeEntity space=adSceneTypeService.getById(e.getSpaceStId());
            if(space!=null){
             e.setSpace(space.getName());
            }
        });
        Integer count= adSceneProjectDao.getCountBySmId(smId);
        PageUtils pageUtils = new PageUtils(list,count,page,limit);
        view.setPage(pageUtils);
        List<AdSceneMerchantEntity> similarList=adSceneMerchantDao.getSimilarList(view.getBitId(),smId);
        similarList.forEach(e->{
            String url = adSceneProjectService.getUrlOrderOneBySmId(e.getSmid());
            e.setUrl(url);
        });
        view.setSimilarList(similarList);
        return view;
    }

}