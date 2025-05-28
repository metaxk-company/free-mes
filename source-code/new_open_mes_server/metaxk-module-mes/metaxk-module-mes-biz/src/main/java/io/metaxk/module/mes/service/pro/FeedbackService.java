package io.metaxk.module.mes.service.pro;

import java.util.*;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.ProfeedBackVo;
import io.metaxk.module.mes.dal.dataobject.pro.Feedback;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 生产报工记录 Service 接口
 * @author 万界星空
 */
public interface FeedbackService {


    /**
     * 删除报工通过id
     * @param id
     */
    void removeFeedBackById(Long id);

    /**
     * 根据id查询报工
     * @param id
     * @return  Feedback
     */
    Feedback getFeedback(Long id);


    /**
     * 报工条件分页
     * @param page
     * @return   PageResult<Feedback>
     */
    PageResult<Feedback> getFeedbackPage(FeedbackQueryVo page);


    /**
     * 根据id删除
     * @param id
     * @return Integer
     */
    Integer removeById(Long id);

    /**
     * 新增报工
     * @param feedback
     * @return Integer
     */
    Integer insertFeedback(Feedback feedback);

    Integer saveFeedBack(ProfeedBackVo profeedBackVo);


    /**
     * 修改报工状态计算工时
     * @param proceedBackVo
     * @return
     * @throws Exception
     */
    CommonResult updateStatus(ProfeedBackVo proceedBackVo)throws Exception;

    /**
     * 根据id查询报工
     * @param id
     * @return  Feedback
     */
    Feedback findById(Long id);

    /**
     * 修改完工
     * @param proFeedback
     */
    void updateCommenCompletion(Feedback proFeedback);

    /**
     * 根据工单号查询报工
     * @param workerCode
     * @return List<Feedback>
     */
    List<Feedback> findByOrderCode(String workerCode);

    /**
     * 根据任务编号查询报工
     * @param taskCode
     * @return Feedback
     */
    Feedback findByTaskCode(String taskCode);

    /**
     * 查询未开工状态订单
     * @return List<Feedback>
     */
    List<Feedback> findUnStartOrders();

    /**
     * 查询完工状态订单
     * @return List<Feedback>
     */
    List<Feedback> findCompletedOrders();

    /**
     * 查询暂停状态订单
     * @return List<Feedback>
     */
    List<Feedback> findPausedOrders();

    /**
     * 根据工单号任务编号查询报工
     * @param workerCode
     * @param taskCode
     * @return Feedback
     */
    Feedback findByOrderCodeAndTaskCode(String workerCode, String taskCode);

    /**
     * 修改报工
     * @param feedback
     * @return  Integer
     */
    Integer updateFeedBack(Feedback feedback);

    /**
     * 根据工单号查询报工，条件分页查询
     * @param worker
     * @return PageResult<Feedback>
     */
    PageResult<Feedback>  findFeedBackByWorkOrderCode(FeedbackQueryVo worker);

    List<Feedback> findFeedBackByOrderId(Long orderId);

    List<Feedback> findFeedBackList();
}
