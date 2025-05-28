package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackStatus;
import io.metaxk.module.mes.dal.mysql.pro.FeedBackStatusMapper;
import io.metaxk.module.mes.service.pro.FeedBackStatusService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;



@Service
public class FeedbackStatusServiceImpl implements FeedBackStatusService {


    @Resource
    private FeedBackStatusMapper feedBackStatusMapper;

    @Override
    public void removeFeedBackStatusById(Long id) {
        QueryWrapper<FeedbackStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("feedback_id",id);
        feedBackStatusMapper.delete(queryWrapper);
    }

    @Override
    public Integer save(FeedbackStatus feedbackStatus) {
        return feedBackStatusMapper.insert(feedbackStatus);
    }

    @Override
    public FeedbackStatus findFeedBaskStatusByFeedBackId(Long id) {
        LambdaQueryWrapperX<FeedbackStatus> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(FeedbackStatus::getFeedbackId,id);
        return feedBackStatusMapper.selectOne(queryWrapperX);
    }
}
