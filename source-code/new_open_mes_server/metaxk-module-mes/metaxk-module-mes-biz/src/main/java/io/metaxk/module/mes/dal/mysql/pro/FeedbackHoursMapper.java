package io.metaxk.module.mes.dal.mysql.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackHours;
import io.metaxk.module.mes.utils.DateUtils;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import java.text.ParseException;
import java.util.Date;


/**
 * 报工工时 Mapper
 * @author 万界星空
 */
@Mapper
public interface FeedbackHoursMapper extends BaseMapperX<FeedbackHours> {

    /**
     * 报工工时条件分页查询
     * @param feedbackHoursVO
     * @return
     * @throws ParseException
     */
   default PageResult<FeedbackHours> list(FeedbackHoursVo feedbackHoursVO) throws ParseException {
       LambdaQueryWrapperX<FeedbackHours> queryWrapper  = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(feedbackHoursVO.getWorkstationCode())){
           queryWrapper.eq(FeedbackHours::getWorkstationCode,feedbackHoursVO.getWorkstationCode());
       }
       if(StringUtils.isNotBlank(feedbackHoursVO.getWorkshopCode())){
           queryWrapper.eq(FeedbackHours::getWorkshopCode,feedbackHoursVO.getWorkshopCode());
       }
       if(StringUtils.isNotBlank(feedbackHoursVO.getProcessCode())){
           queryWrapper.eq(FeedbackHours::getProcessCode,feedbackHoursVO.getProcessCode());
       }
       if(StringUtils.isNotBlank(feedbackHoursVO.getUserName())){
           queryWrapper.eq(FeedbackHours::getUserName,feedbackHoursVO.getUserName());
       }
       if(StringUtils.isNotBlank(feedbackHoursVO.getMachineryCode())){
           queryWrapper.like(FeedbackHours::getEquipmentCode,feedbackHoursVO.getMachineryCode());
       }

       if(StringUtils.isNotBlank(feedbackHoursVO.getFeedbackTime())){
           Date feedbackDate = DateUtils.parseDate(feedbackHoursVO.getFeedbackTime(), "yyyy-MM-dd");
           queryWrapper.apply("DATE(feedback_time) = {0}", feedbackDate);
       }
       if(StringUtils.isNotBlank(feedbackHoursVO.getFinishedTime())){
           Date finishedDate = DateUtils.parseDate(feedbackHoursVO.getFinishedTime(), "yyyy-MM-dd");
           queryWrapper.apply("DATE(worker_finished_time) = {0}", finishedDate);
       }
       if(StringUtils.isBlank(feedbackHoursVO.getWorkstationCode()) && StringUtils.isBlank(feedbackHoursVO.getWorkshopCode())
       && StringUtils.isBlank(feedbackHoursVO.getProcessCode())&& StringUtils.isBlank(feedbackHoursVO.getUserName())
       && StringUtils.isBlank(feedbackHoursVO.getMachineryCode()) && StringUtils.isBlank(feedbackHoursVO.getFeedbackTime())
       && StringUtils.isBlank(feedbackHoursVO.getFinishedTime())){
           queryWrapper.isNotNull(FeedbackHours::getId);
       }
       queryWrapper.orderByAsc(FeedbackHours::getTaskCode);
       return  selectPage(feedbackHoursVO,queryWrapper);
   }


    /**
     * 根据报工编号查询报工工时
     * @param feedbackCode
     * @return FeedbackHours
     */
  default FeedbackHours selectFeedbackHours(Long feedbackCode){
      LambdaQueryWrapperX<FeedbackHours> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(FeedbackHours::getFeedbackId,feedbackCode);
      return selectOne(queryWrapperX);
  }

    /**
     * 根据id查询报工工时
     * @param id
     * @return FeedbackHours
     */
  default FeedbackHours findByFeedBackId(Long id){
      LambdaQueryWrapperX<FeedbackHours> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(FeedbackHours::getFeedbackId,id);
      return  selectOne(queryWrapperX);
  }
}
