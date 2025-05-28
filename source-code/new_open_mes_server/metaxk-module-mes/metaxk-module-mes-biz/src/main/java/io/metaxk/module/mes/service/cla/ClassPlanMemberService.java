package io.metaxk.module.mes.service.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlanMember;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/6/30 13:48
 */
public interface ClassPlanMemberService {

    /**
     * 新增排班计划人员
     * @param classPlanMember
     * @return Integer
     */
    Integer saveClassPlanMember(ClassPlanMember classPlanMember);

    /**
     * 排班计划人员分页查询
     * @param classPlanMemberPageReqVO
     * @return PageResult<ClassPlanMember>
     */
    PageResult<ClassPlanMember> classPlanMemberPage(ClaPlanMemberQueryVo classPlanMemberPageReqVO);

    /**
     * 根据计划编号删除排班计划
     * @param planCode
     * @return Integer
     */
    Integer removeByPlanCode(String planCode);

    /**
     * 根据计划编号，人员编号，人员名称查询排班计划人员
     * @param planCode
     * @param peopleId
     * @param teamPeople
     * @return ClassPlanMember
     */
    ClassPlanMember findByPeopleIdAndName( String planCode,Long peopleId, String teamPeople);

    /**
     * 所有排班计划人员信息
     * @return  List<ClassPlanMember>
     */
    List<ClassPlanMember> findAll();

    /**
     * 根据班组类型，班组编号，班组名称查询排班计划人员信息
     * @param teamType
     * @param teamCode
     * @param teamName
     * @return List<ClassPlanMember>
     */
    List<ClassPlanMember> findByTypeAndCodeAndName(String teamType, String teamCode, String teamName);

    /**
     * 删除排班计划人员
     * @param memberId
     * @return Integer
     */
    Integer removeClassPlanMemberById(Long memberId);

    /**
     * 根据id查询排班计划人员信息
     * @param id
     * @return  List<ClassPlanMember>
     */
    List<ClassPlanMember> findClassPlanMember(Long id);

    /**
     * 根据人员id查询排班计划人员信息
     * @param memberId
     * @return ClassPlanMember
     */
    ClassPlanMember findPlanMemberByPeopleId(Long memberId);

    /**
     * 修改排班计划人员信息
     * @param classPlanMember1
     * @return Integer
     */
    Integer updateClassPlanMember(ClassPlanMember classPlanMember1);
}
