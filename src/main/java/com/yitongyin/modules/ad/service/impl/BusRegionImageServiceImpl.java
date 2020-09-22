package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusRegionImageDao;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.BusRegionImageEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import com.yitongyin.modules.ad.form.NameAndList;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.BusRegionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("busRegionImageService")
public class BusRegionImageServiceImpl extends ServiceImpl<BusRegionImageDao, BusRegionImageEntity> implements BusRegionImageService {

    @Autowired
    BusRegionImageDao busRegionImageDao;
    @Autowired
    private AdOssService adOssService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<TServiceTypeEntity> typeEntities =busRegionImageDao.pageByRegion(params.get("region"),
                params.get("keyWord"),start,limit );
        List<NameAndList> nameAndLists = new ArrayList<>();
        for (TServiceTypeEntity entity : typeEntities) {
            NameAndList nameAndList = new NameAndList();
            if(entity!=null) {
                nameAndList.setName(entity.getServicename());
            }
            List<BusRegionImageEntity> list =busRegionImageDao.getOssByReionAndType(params.get("region"),
                    params.get("keyWord"),entity==null?null:entity.getServicetypeid());
            nameAndList.setList(list);
            nameAndLists.add(nameAndList);
        }
        List<TServiceTypeEntity> countSize =busRegionImageDao.pageCountByRegion(params.get("region"),
                params.get("keyWord"));
        PageUtils pageutils = new PageUtils(nameAndLists,countSize.size(),limit,page);
        return pageutils;
    }

    @Override
    public PageUtils queryPageByType(Map<String, Object> params, Long serviceType) {
        QueryWrapper<BusRegionImageEntity> query = new QueryWrapper<>();
        if(serviceType!=null){
            query.eq("service_type_id",serviceType);
        }
        IPage<BusRegionImageEntity> page = this.page(
                new Query<BusRegionImageEntity>().getPage(params),
                query
        );

        return new PageUtils(page);
    }

}