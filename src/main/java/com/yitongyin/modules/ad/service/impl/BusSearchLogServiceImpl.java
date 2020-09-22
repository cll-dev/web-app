package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.IdWorker;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusSearchLogDao;
import com.yitongyin.modules.ad.entity.BusSearchLogEntity;
import com.yitongyin.modules.ad.service.BusSearchLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("busSearchLogService")
public class BusSearchLogServiceImpl extends ServiceImpl<BusSearchLogDao, BusSearchLogEntity> implements BusSearchLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusSearchLogEntity> page = this.page(
                new Query<BusSearchLogEntity>().getPage(params),
                new QueryWrapper<BusSearchLogEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveOrUpd(BusSearchLogEntity entity,Boolean isMatch) {
        BusSearchLogEntity busSearchLogEntity= findByTitle(entity.getSarchtitle());
        if(busSearchLogEntity==null){
            Long id=IdWorker.DEFAULT.nextId();
            entity.setLogid(id.toString());
            entity.setSearchtimes(1l);
            entity.setFirstsearchdate(new Date());
            entity.setLastsarchdate(new Date());
            entity.setSearchrecommend(0);
            if(isMatch){
                entity.setMatchedtimes(1l);
            }else{
                entity.setMatchedtimes(0l);
            }
            return this.save(entity);
        }else{
            if(isMatch){
                entity.setMatchedtimes(busSearchLogEntity.getMatchedtimes()+1);
            }
            entity.setSearchtimes(busSearchLogEntity.getSearchtimes()+1);
            entity.setLastsarchdate(new Date());
            UpdateWrapper<BusSearchLogEntity> update = new UpdateWrapper<>();
            update.eq("logId", busSearchLogEntity.getLogid());
            return this.update(entity,update);
        }
    }

    @Override
    public BusSearchLogEntity findByTitle(String title) {
        QueryWrapper<BusSearchLogEntity> query = new QueryWrapper<>();
        query.eq("sarchTitle",title.trim());
        return this.getOne(query);
    }

    @Override
    public List<BusSearchLogEntity> getListByIsSearchRecommend() {
        QueryWrapper<BusSearchLogEntity> query = new QueryWrapper<>();
        query.select("sarchTitle");
        query.eq("searchRecommend", 1);
        query.orderByDesc("lastSarchDate");
        query.last("limit 0,10");
        return this.list(query);
    }

//    @Override
//    public Boolean updMatchTime(BusSearchLogEntity entity) {
//        BusSearchLogEntity busSearchLogEntity = findByTitle(entity.getSarchtitle());
//        if(busSearchLogEntity==null)
//
//        entity.setMatchedtimes(busSearchLogEntity.getMatchedtimes()+1);
//        UpdateWrapper<BusSearchLogEntity> update = new UpdateWrapper<>();
//        update.eq("logId", busSearchLogEntity.getLogid());
//        return this.update(entity,update);
//    }

}