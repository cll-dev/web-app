package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdClientTagDao;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.entity.AdClientTagEntity;
import com.yitongyin.modules.ad.service.AdClientTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("adClientTagService")
public class AdClientTagServiceImpl extends ServiceImpl<AdClientTagDao, AdClientTagEntity> implements AdClientTagService {

    @Autowired
    AdClientTagDao adClientTagDao;
    @Override
    public List<AdClientTagEntity> getListByTagIds(List<Integer> tagIds) {

        if(tagIds==null||tagIds.size()==0){
            return new ArrayList<>();
        }
        return adClientTagDao.getListByTagIds(tagIds);
    }

    @Override
    public boolean removeByTagId(Integer tagId) {
        QueryWrapper<AdClientTagEntity> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("tag_id",tagId);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean addByClientTag(List<AdClientTagEntity> list) {
        list.forEach(e->{
            e.setId(null);
        });
        return this.saveBatch(list);
    }

    @Override
    public AdClientEntity getNamesByClientIdAndMerchantId(Long clientId, Long merchantId) {

        return adClientTagDao.getTagNamesByClientId(clientId,merchantId);
    }

    @Transactional
    @Override
    public boolean addTags(List<AdClientTagEntity> list,List<Integer> tagIds,Long clientId) {
        if(delAllTagsByClientId(clientId,tagIds)){
            if(list!=null){
                list.forEach(e->{
                    if(e.getId()!=null){
                        list.remove(e);
                    }
                });
                return this.saveBatch(list);
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delAllTagsByClientId(Long clientId, List<Integer> tagIds) {
        if(tagIds==null||tagIds.size()==0)return true;
        QueryWrapper<AdClientTagEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id",clientId);
        queryWrapper.in("tag_id",tagIds);
        return this.remove(queryWrapper);
    }


}