package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusSupplierSpecValueDao;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import com.yitongyin.modules.ad.service.BusSupplierSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busSupplierSpecValueService")
public class BusSupplierSpecValueServiceImpl extends ServiceImpl<BusSupplierSpecValueDao, BusSupplierSpecValueEntity> implements BusSupplierSpecValueService {
    @Autowired
    BusSupplierSpecValueDao busSupplierSpecValueDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusSupplierSpecValueEntity> page = this.page(
                new Query<BusSupplierSpecValueEntity>().getPage(params),
                new QueryWrapper<BusSupplierSpecValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BusSupplierSpecValueEntity> getListBySsgId(Long ssgId) {
        QueryWrapper<BusSupplierSpecValueEntity> query = new QueryWrapper<>();
        query.eq("ssgId",ssgId);
        return  this.list(query);
    }

    @Override
    public List<BusSupplierSpecValueEntity> getListBySupAndPro(Long supplierId, Long productId) {
        return busSupplierSpecValueDao.getValueAndNameList(supplierId,productId);
    }

}