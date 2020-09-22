package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.TFeedbackDao;
import com.yitongyin.modules.ad.entity.TFeedbackEntity;
import com.yitongyin.modules.ad.service.TFeedbackService;
import org.springframework.stereotype.Service;


@Service("tFeedbackService")
public class TFeedbackServiceImpl extends ServiceImpl<TFeedbackDao, TFeedbackEntity> implements TFeedbackService {


}