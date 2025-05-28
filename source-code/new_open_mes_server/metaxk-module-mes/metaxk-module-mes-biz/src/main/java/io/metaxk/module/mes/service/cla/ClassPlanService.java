package io.metaxk.module.mes.service.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.PlanTeamQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ScheduleMemberVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlan;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanExcelVo;
import java.util.List;

/**
 * 排版计划 Service
 * @author 万界星空
 * @time 2023/6/26 13:31
 */
public interface ClassPlanService {


    /**
     * 批量新增班组计划
     * @param calPlan
     */
    Integer saveCalPlan(ClassPlan calPlan);


    /**
     * 删除班组计划
     * @param ids
     * @return Integer
     */
    Integer removeCalPlanByIds(List<Long> ids);

    /**
     * 修改班组计划
     * @param calPlan
     * @return Integer
     */
    Integer updateCalPlan(ClassPlan calPlan);

    /**
     * 班组计划条件分页查询
     * @param calPlanPageReqVO
     * @return PageResult<CalPlan>
     */
    PageResult<ClassPlan> getCalPlanPage(ClaPlanQueryVo calPlanPageReqVO);

    /**
     * 班组计划详情
     * @param id
     * @return CalPlan
     */
    ClassPlan getCalPlanById(Long id);

    /**
     * 导出班组计划
     * @return List<CalPlanExcelVO>
     */
    List<ClaPlanExcelVo> exportData();

    /**
     * 根据编号查询班组计划
     * @param shiftNum
     * @return ClassPlan
     */
    ClassPlan findCalPlanByCode(String shiftNum);

    /**
     * 根据计划名称查询班组计划
     * @param shiftName
     * @return ClassPlan
     */
    ClassPlan findCalPlanByName(String shiftName);

    /**
     * 根据班组类型查询排班计划分页返回
     * @param calPlanPageReqVO
     * @return PageResult<ClassPlan>
     */
    PageResult<ClassPlan> findCalPlanByShiftType(ClaPlanQueryVo calPlanPageReqVO);


    /**
     * 根据id查询排班计划
     * @param id
     * @return ClassPlan
     */
    ClassPlan findCalPlanById(Long id);

    /**
     * 根据班组类型，编号，名称查询班组计划
     * @param teamType
     * @param teamCode
     * @param teamName
     * @return ClassPlan
     */
    ClassPlan findCalPlanByCondition(String teamType, String teamCode, String teamName);

    /**
     * 根据计划编号，班组编号，班组类型，班组名称查询班组计划
     * @param planCode1
     * @param code
     * @param teamType
     * @param teamName
     * @return ClassPlan
     */
    ClassPlan findPlanByCodeAndTypeAndName(String planCode1, String code, String teamType, String teamName);

    /**
     * 排班计划全部信息
     * @return List<ClassPlan>
     */
    List<ClassPlan> findClassPlanList();

    /**
     * 根据计划编号查询排班计划返回分页
     * @param planTeamPageVO
     * @return PageResult<ClassPlan>
     */
    PageResult<ClassPlan> findPlanByTime(PlanTeamQueryVo planTeamPageVO);

    /**
     * 根据编组编号以及轮班方式查询上班人员
     * @param teamCode
     * @param shiftWay
     * @return List<ScheduleMemberVO>
     */
    List<ScheduleMemberVo> findMemberByCodeAndWay(String teamCode, String shiftWay);
}
