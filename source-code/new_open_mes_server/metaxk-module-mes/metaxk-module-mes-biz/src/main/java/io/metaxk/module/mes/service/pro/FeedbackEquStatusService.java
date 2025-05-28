package io.metaxk.module.mes.service.pro;

import io.metaxk.module.mes.dal.dataobject.pro.FeedbackEquStatus;


/**
 * 报工设备状态  Service接口
 * @author 万界星空
 */
public interface FeedbackEquStatusService {


    /**
     * 新增报工设备状态
     * @param feedbackEquStatus
     * @return Integer
     */
    Integer save(FeedbackEquStatus feedbackEquStatus);

    /**
     * 根据id删除报工设备状态
     * @param id
     * @return Integer
     */
    Integer removeFeedbackEquStatusById(Long id);
}
