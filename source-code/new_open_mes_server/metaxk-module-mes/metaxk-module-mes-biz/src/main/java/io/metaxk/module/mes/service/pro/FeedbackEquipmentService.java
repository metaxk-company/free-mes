package io.metaxk.module.mes.service.pro;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentStatusVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackEquipment;
import java.util.List;


/**
 * 报工设备  Service接口
 * @author 万界星空
 */
public interface FeedbackEquipmentService {

    /**
     * 根据id查询报工设备信息
     * @param id
     * @return  FeedbackEquipment
     */
    FeedbackEquipment findFeedbackEquipmentById(Long id);

    /**
     * 修改报工设备信息
     * @param proFeedbackEquipment
     * @return  Integer
     */
    Integer updateFeedbackEquipment(FeedbackEquipment proFeedbackEquipment);

    /**
     * 根据id删除报工设备信息
     * @param id
     * @return
     */
    Integer removeFeedBackEquipmentById(Long id);

    /**
     * 根据id查询报工设备信息
     * @param id
     * @return FeedbackEquipment
     */
    FeedbackEquipment selectById(Long id);

    /**
     * 查询报工设备集合
     * @param pageReqVO
     * @return   List<FeedBackEquipmentVo>
     */
    List<FeedBackEquipmentVo> list(FeedBackEquipmentQueryVo pageReqVO);

    /**
     * 修改报工设备信息计算设备工时
     * @param feedBackEquipmentStatusVo Exception
     * @return CommonResult
     */
    CommonResult updateFeedbackEquipmentStatus(FeedBackEquipmentStatusVo feedBackEquipmentStatusVo) throws Exception;

    /**
     * 根据id查询报工设备信息
     * @param id
     * @return FeedbackEquipment
     */
    FeedbackEquipment findEquipmentCodeById(Long id);

 //   Integer removeFeedBackEquipmentId(Long id);

    /**
     * 新增报工设备信息
     * @param feedbackEquipment
     * @return Integer
     */
    Integer save(FeedbackEquipment feedbackEquipment);

  //  Integer removeById(Long id);
}
