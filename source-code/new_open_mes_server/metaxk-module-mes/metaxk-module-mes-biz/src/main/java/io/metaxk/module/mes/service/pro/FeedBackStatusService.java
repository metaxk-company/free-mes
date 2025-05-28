package io.metaxk.module.mes.service.pro;

import io.metaxk.module.mes.dal.dataobject.pro.FeedbackStatus;

/**
 * 报工状态 Service 接口
 * @author 万界星空
 */
public interface FeedBackStatusService {


    /**
     * 根据id删除报工状态
     * @param id
     */
    void removeFeedBackStatusById(Long id);

    /**
     * 新增报工状态
     * @param feedbackStatus
     * @return Integer
     */
    Integer save(FeedbackStatus feedbackStatus);


    FeedbackStatus findFeedBaskStatusByFeedBackId(Long id);
}
