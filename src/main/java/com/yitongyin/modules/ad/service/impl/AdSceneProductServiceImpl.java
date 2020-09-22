package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdSceneProductDao;
import com.yitongyin.modules.ad.entity.AdSceneProductEntity;
import com.yitongyin.modules.ad.service.AdSceneProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adSceneProductService")
public class AdSceneProductServiceImpl extends ServiceImpl<AdSceneProductDao, AdSceneProductEntity> implements AdSceneProductService {

    @Autowired
    AdSceneProductDao adSceneProductDao;
    @Override
    public List<AdSceneProductEntity> getListBySpId(Long spId) {
        return adSceneProductDao.getListBySpId(spId);
    }
}