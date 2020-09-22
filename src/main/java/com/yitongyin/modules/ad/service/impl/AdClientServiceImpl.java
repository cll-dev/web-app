package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.utils.*;
import com.yitongyin.modules.ad.dao.AdClientDao;
import com.yitongyin.modules.ad.dao.AdClientProductCollectRecordDao;
import com.yitongyin.modules.ad.dao.AdClientSpecCollectRecordDao;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.AdClientQuery;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.ClientLogin;
import com.yitongyin.modules.mp.View.ProductPriceInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service("adClientService")
public class AdClientServiceImpl extends ServiceImpl<AdClientDao, AdClientEntity> implements AdClientService {

    @Autowired
    AdClientDao adClientDao;
    @Autowired
    AdGroupService adGroupService;
    @Autowired
    AdClientGroupService adClientGroupService;
    @Autowired
    AdTagService adTagService;
    @Autowired
    AdClientTagService adClientTagService;
    @Autowired
    AdClientSpecCollectRecordDao adClientSpecCollectRecordDao;
    @Autowired
    AdClientProductCollectRecordDao adClientProductCollectRecordDao;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdOssService adOssService;
    @Autowired
    AdClientProductBrowseRecordService adClientProductBrowseRecordService;
    @Autowired
    AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, AdClientQuery query) {
        Long merchantId=query.getMerchantId();
        Integer groupId=query.getGroupId();
        Integer noGroupId=query.getNoGroupId();
        Integer tagId=query.getTagId();
        Integer noTagId=query.getNoTagId();
        Integer isLastLogin=query.getIsLastLogin();
        final String name=query.getName();
        List<AdGroupEntity> groupList=adGroupService.getListByMerchantId(merchantId);
        List<Integer> groupIds = new ArrayList<>();
        if(groupId!=null){
            groupIds.add(groupId);
        }else {
            groupIds=groupList.stream().map(e->e.getGroupid()).collect(Collectors.toList());
            if(noGroupId!=null){
                groupIds.remove(noGroupId);
            }
        }
        List<AdClientGroupEntity> clientGroupList=adClientGroupService.getListByGroupIds(groupIds);
        for (AdClientGroupEntity groupEntity1: clientGroupList) {
            for (AdGroupEntity groupEntity: groupList) {
                if(groupEntity1.getGroupId().equals(groupEntity.getGroupid())){
                    groupEntity1.setName(groupEntity.getName());
                    break;
                }
            }
        }
        List<Long> clientIdsGroup = clientGroupList.stream().map(e->e.getClientId()).collect(Collectors.toList());

        List<AdTagEntity> tagList=adTagService.getListByMerchantId(merchantId);
        List<Integer> allTagIds=tagList.stream().map(e->e.getTagid()).collect(Collectors.toList());
        List<Integer> tagIds = new ArrayList<>();
        if(tagId!=null){
            tagIds.add(tagId);
        }else{
            tagIds=tagList.stream().map(e->e.getTagid()).collect(Collectors.toList());
            if(noTagId!=null){
                tagIds.remove(noTagId);
            }
        }
        List<AdClientTagEntity> aalClientTagList=adClientTagService.getListByTagIds(allTagIds);
        List<AdClientTagEntity> clientTagList=adClientTagService.getListByTagIds(tagIds);
        for (AdClientTagEntity adClientTagEntity:aalClientTagList) {
            List<String> strList= UtilString.stringToList(adClientTagEntity.getTagIds());
            List<Integer> longList= strList.stream().map(e->Integer.valueOf(e)).collect(Collectors.toList());
            List<String> tagNames=new ArrayList<>();
            for (Integer lg:longList) {
                for (AdTagEntity adTagEntity: tagList) {
                    if(lg.equals(adTagEntity.getTagid())){
                        tagNames.add(adTagEntity.getName());
                        break;
                    }
                }
            }
            adClientTagEntity.setTagNames(tagNames);
        }
        List<Long> clientIdsTag=clientTagList.stream().map(e->e.getClientId()).collect(Collectors.toList());
        List<Long> clientIds = new ArrayList<>();
        if(tagId!=null){
            clientIds=clientIdsGroup.stream().filter(item -> clientIdsTag.contains(item)).collect(Collectors.toList());
        }else{
            clientIds=clientIdsGroup;
            if(noTagId!=null){
                List<Integer> longList = new ArrayList<>();
                longList.add(noTagId);
                List<AdClientTagEntity> noClientTagList=adClientTagService.getListByTagIds(longList);
                List<Long> noClientIdsTag=noClientTagList.stream().map(e->e.getClientId()).collect(Collectors.toList());
                clientIds.removeAll(noClientIdsTag);
            }
        }
        QueryWrapper<AdClientEntity> queryWrapper = new QueryWrapper<>();
        if(clientIds==null||clientIds.size()==0){
            return new PageUtils(new ArrayList<>(),0,0,0);
        }else{
            queryWrapper.in("clientId",clientIds);
        }

        if(StringUtils.isNotBlank(name)){
            queryWrapper.and(i->i.like("name",name).or().like("mobile",name));

        }
        if(isLastLogin!=null){
            queryWrapper.orderByDesc("last_login_time");
        }
        IPage<AdClientEntity> pageUtils = this.page(
                new Query<AdClientEntity>().getPage(params),
                queryWrapper
        );
        List<AdClientEntity> clientList=pageUtils.getRecords();
        for (AdClientEntity clientEntity: clientList) {
            for (AdClientGroupEntity groupEntity: clientGroupList) {
                if(clientEntity.getClientid().equals(groupEntity.getClientId())){
                    clientEntity.setGroupName(groupEntity.getName());
                    clientEntity.setGroupId(groupEntity.getGroupId());
                    clientEntity.setClientGroupId(groupEntity.getId());
                    break;
                }
            }
            for (AdClientTagEntity tagEntity:aalClientTagList){
                if(clientEntity.getClientid().equals(tagEntity.getClientId())){
                    clientEntity.setTagNames(tagEntity.getTagNames());
                    clientEntity.setClientTagId(tagEntity.getId());
                    break;
                }
            }
            if(clientEntity.getAvatar()!=null){
                AdOssEntity ossEntity = adOssService.findById(clientEntity.getAvatar());
                if(ossEntity!=null){
                    clientEntity.setUrl(ossEntity.getUrl());
                }
            }
            AdClientProductBrowseRecordEntity browseRecordEntity = adClientProductBrowseRecordService.getMaxTimeByClientId(
                    clientEntity.getClientid(),merchantId
            );
            if(browseRecordEntity!=null){
                clientEntity.setLastLanTime(browseRecordEntity.getBrowseDate());
            }
        }
        return new PageUtils(pageUtils);
    }

    @Override
    public AdClientEntity getInfoByIdAndMerchantId(Long clientId,Long merchantId) {
        AdClientEntity entity =adClientDao.getInfoByIdAndMerchantId(merchantId,clientId);
        if(entity==null){
            throw new RRException("该用户暂未对此商家授权");
        }
        Integer specCount=adClientSpecCollectRecordDao.getCountByClientId(clientId,merchantId);
        entity.setSpecCollectCount(specCount);
        Integer proCount=adClientProductCollectRecordDao.getCountByClientId(clientId,merchantId);
        entity.setProductCollectCount(proCount);
//        List<ProductPriceInfo> list =adClientProductCollectRecordDao.getSimilarListByMerchantId(clientId,merchantId);
//        list.forEach(e->{
//            AdMerchantSpecGroupEntity groupEntity =adMerchantSpecGroupService.getLowestPriceByMerAndPro(merchantId,e.getProductId());
//            if(groupEntity!=null){
//                AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
//                        ,e.getId(),merchantId);
//                if(adGroupProductRateEntity!=null){
//                    if(adGroupProductRateEntity.getIsShow()==1&&e.getPrice()!=null){
//                        e.setPrice(e.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
//                    }
//                }else{
//                    e.setPrice(e.getPrice());
//                }
//            }
//        });
//        entity.setProCollectSimilarList(list);
        return entity;
    }

    @Override
    public AdClientEntity getByMobile(String mobile) {
        QueryWrapper<AdClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        return this.getOne(queryWrapper);
    }

    @Override
    public R addClientByGroupId(ClientLogin login) {
        AdGroupEntity groupEntity=adGroupService.getDefaultByMerchantId(login.getMerchantId());
        if(groupEntity==null){
            return R.error("数据错误");
        }
        AdClientEntity entity = new AdClientEntity();
        entity.setMobile(login.getMoblie());
        entity.setStatus(1);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setLastLoginTime(new Date());
        entity.setName("yxd"+login.getMoblie());
        if(this.save(entity)){
            AdClientGroupEntity clientGroupEntity = new AdClientGroupEntity();
            clientGroupEntity.setClientId(entity.getClientid());
            clientGroupEntity.setGroupId(groupEntity.getGroupid());
            adClientGroupService.save(clientGroupEntity);
            //账号注册成功同时增加随机浏览记录
//            List<AdMerchantProductEntity> productEntities = adMerchantProductService.mpGetAllShowListLimit(login.getMerchantId());
//            List<AdClientProductBrowseRecordEntity> browseList= productEntities.stream().map(e->{
//                AdClientProductBrowseRecordEntity browseRecordEntity = new AdClientProductBrowseRecordEntity();
//                browseRecordEntity.setClientId(entity.getClientid());
//                browseRecordEntity.setMerchantProductId(e.getId());
//                browseRecordEntity.setBrowseDate(new Date());
//                browseRecordEntity.setMerchantId(login.getMerchantId());
//                return browseRecordEntity;
//            }).collect(Collectors.toList());
//            adClientProductBrowseRecordService.saveBatch(browseList);

            List<Long> merchantIds=adClientGroupService.getMerchantIdsByClientIdAndGroup(entity.getClientid());
            //无效token
            String token = jwtUtils.generateToken(entity.getClientid());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            map.put("merchantIds",merchantIds);
            map.put("isCurrent",1);
            return R.ok(map);
        }
        return R.error();
    }

    @Override
    public boolean addClientToGroupByMerchantId(Long clientId,Long merchantId) {
        AdGroupEntity groupEntity=adGroupService.getDefaultByMerchantId(merchantId);
        if(groupEntity==null){
            throw new RRException("数据错误");
        }
        AdClientGroupEntity clientGroupEntity = new AdClientGroupEntity();
        clientGroupEntity.setClientId(clientId);
        clientGroupEntity.setGroupId(groupEntity.getGroupid());
        return   adClientGroupService.save(clientGroupEntity);
    }

    @Override
    public  PageUtils pageGetLikeMore(Map<String, Object> params, Long merchantId,Long clientId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;

        List<ProductPriceInfo> list =adClientProductCollectRecordDao.getSimilarListByMerchantId(clientId,merchantId,start,limit);
        list.forEach(e->{
            AdMerchantSpecGroupEntity groupEntity =adMerchantSpecGroupService.getLowestPriceByMerAndPro(merchantId,e.getProductId());
            if(groupEntity!=null){
                AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
                        ,e.getId(),merchantId);
                if(adGroupProductRateEntity!=null){
                    if(adGroupProductRateEntity.getIsShow()==1&&e.getPrice()!=null){
                        e.setPrice(e.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                    }
                }else{
                    e.setPrice(e.getPrice());
                }
            }
        });
        Integer count =adClientProductCollectRecordDao.getCountSimilarListByMerchantId(clientId,merchantId);
        return new PageUtils(list,count,limit,page);
    }

}