package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.BusSupplierLevelDao;
import com.yitongyin.modules.ad.entity.BusSupplierLevelEntity;
import com.yitongyin.modules.ad.service.BusSupplierLevelService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("busSupplierLevelService")
public class BusSupplierLevelServiceImpl extends ServiceImpl<BusSupplierLevelDao, BusSupplierLevelEntity> implements BusSupplierLevelService {
    @Override
    public List<BusSupplierLevelEntity> getList() {
        QueryWrapper<BusSupplierLevelEntity> query = new QueryWrapper<>();
        return this.list(query);
    }

}