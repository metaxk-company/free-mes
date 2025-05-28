package io.metaxk.module.mes.service.pro;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackHours;
import java.text.ParseException;
import java.util.List;



/**
 * 报工工时  Service接口
 * @author 万界星空
 */
public interface FeedbackHoursService {


    /**
     * 根据id删除报工工时
     * @param id
     */
    void removeFeedBackHoursById(Long id);

    /**
     * 报工工时条件分页查询
     * @param feedbackHoursVO
     * @return  PageResult<FeedbackHours>
     * @throws ParseException
     */
    PageResult<FeedbackHours> list(FeedbackHoursVo feedbackHoursVO) throws ParseException;


    /**
     * 导出报工工时
     * @param ids
     * @return List<FeedbackHoursExcelVO>
     */
    List<FeedbackHoursExcelVo> listData(List<Long> ids);



}
