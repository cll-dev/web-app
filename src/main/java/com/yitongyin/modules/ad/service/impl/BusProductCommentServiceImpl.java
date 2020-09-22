package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.BusProductCommentDao;
import com.yitongyin.modules.ad.entity.BusProductCommentEntity;
import com.yitongyin.modules.ad.service.BusProductCommentService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("busProductCommentService")
public class BusProductCommentServiceImpl extends ServiceImpl<BusProductCommentDao, BusProductCommentEntity> implements BusProductCommentService {
    @Override
    public List<BusProductCommentEntity> getList() {
        QueryWrapper<BusProductCommentEntity> query = new QueryWrapper<>();
        query.select("commentId","content","ossIds","productId");
        query.eq("isVerifyed",1).eq("topable",1);
        return this.list(query);
    }

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<BusProductCommentEntity> page = this.page(
//                new Query<BusProductCommentEntity>().getPage(params),
//                new QueryWrapper<BusProductCommentEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}