package io.metaxk.module.mes.service.impl.pro;

import io.metaxk.module.mes.dal.dataobject.pro.FeedbackEquStatus;
import io.metaxk.module.mes.dal.mysql.pro.FeedbackEquStatusMapper;
import io.metaxk.module.mes.service.pro.FeedbackEquStatusService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;



@Service
public class FeedbackEquStatusServiceImpl implements FeedbackEquStatusService {


    @Resource
    private FeedbackEquStatusMapper feedbackEquStatusMapper;



    @Override
    public Integer save(FeedbackEquStatus feedbackEquStatus) {
        return feedbackEquStatusMapper.insert(feedbackEquStatus);
    }

    @Override
    public Integer removeFeedbackEquStatusById(Long id) {
        return feedbackEquStatusMapper.deleteById(id);
    }
}
