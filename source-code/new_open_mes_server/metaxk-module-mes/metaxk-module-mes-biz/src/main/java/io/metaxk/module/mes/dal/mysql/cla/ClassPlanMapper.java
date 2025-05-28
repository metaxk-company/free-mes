package io.metaxk.module.mes.dal.mysql.cla;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.PlanTeamQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ScheduleMemberVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlan;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * 排班计划 Mapper接口
 * @author 万界星空
 * @time 2023/6/26 13:31
 */
@Mapper
public interface ClassPlanMapper extends BaseMapperX<ClassPlan> {


    /**
     * 排版计划条件分页查询
     * @param calPlanPageReqVO
     * @return PageResult<CalPlan>
     */
    default PageResult<ClassPlan> selectPage(ClaPlanQueryVo calPlanPageReqVO) {
        LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotBlank(calPlanPageReqVO.getTeamType())) {
            queryWrapperX.eq(ClassPlan::getTeamType, calPlanPageReqVO.getTeamType());
        }

        if (StringUtils.isNotBlank(calPlanPageReqVO.getPlanCode())) {
            queryWrapperX.eq(ClassPlan::getPlanCode, calPlanPageReqVO.getPlanCode());
        }

        if (StringUtils.isNotBlank(calPlanPageReqVO.getPlanName())) {
            queryWrapperX.eq(ClassPlan::getPlanName, calPlanPageReqVO.getPlanName());
        }

        if (StringUtils.isNotBlank(calPlanPageReqVO.getStartDate())) {
            LocalDate startDate = LocalDate.parse(calPlanPageReqVO.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.like(ClassPlan::getStartDate, startDate);
        }

        if (StringUtils.isNotBlank(calPlanPageReqVO.getEndDate())) {
            LocalDate endDate = LocalDate.parse(calPlanPageReqVO.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.like(ClassPlan::getEndDate, endDate);
        }


        if (StringUtils.isBlank(calPlanPageReqVO.getTeamType()) && StringUtils.isBlank(calPlanPageReqVO.getPlanCode()) && StringUtils.isBlank(calPlanPageReqVO.getPlanName()) && StringUtils.isBlank(calPlanPageReqVO.getStartDate()) && StringUtils.isBlank(calPlanPageReqVO.getEndDate())) {
            queryWrapperX.isNotNull(ClassPlan::getId);
        }
        return selectPage(calPlanPageReqVO, queryWrapperX);
    }




    /**
     *  根据班组类型查询返回分页
     * @param calPlanPageReqVO
     * @return PageResult<ClassPlan>
     */
     default   PageResult<ClassPlan> findCalPlanByShiftType(ClaPlanQueryVo calPlanPageReqVO){
     LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
     if(StringUtils.isNotBlank(calPlanPageReqVO.getTeamType())){
              queryWrapperX.eq(ClassPlan::getTeamType,calPlanPageReqVO.getTeamType());
     }
     return  selectPage(calPlanPageReqVO,queryWrapperX);

 }


    /**
     *  根据时间查询排班计划中班组的信息
     * @param planTeamPageVO
     * @return PageResult<ClassPlan>
     * 第一个条件：开始时间早于等于传入时间且结束时间晚于等于传入时间的记录。
     * 第二个条件：开始时间早于等于传入时间的下一天且结束时间晚于等于传入时间的下一天的记录。
     */
    default PageResult<ClassPlan> findPlanByTime(PlanTeamQueryVo planTeamPageVO) {
        LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotBlank(planTeamPageVO.getTime())) {
            LocalDate startDate = LocalDate.parse(planTeamPageVO.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = startDate.plusDays(1);
            queryWrapperX.and(
            wrapper -> wrapper
            .or(
              subWrapper -> subWrapper
               .le(ClassPlan::getStartDate, startDate)
               .ge(ClassPlan::getEndDate, startDate))
             .or(
               subWrapper -> subWrapper
               .le(ClassPlan::getStartDate, endDate)
               .ge(ClassPlan::getEndDate, endDate))
            );
        }
        return selectPage(planTeamPageVO, queryWrapperX);
    }






    /**
     *  查询排班计划人员的信息
     * @param teamCode
     * @param shiftWay
     * @return List<ScheduleMemberVO>
     */
    List<ScheduleMemberVo> findMemberByCodeAndWay(@Param("teamCode") String teamCode, @Param("shiftWay") String shiftWay);
}

