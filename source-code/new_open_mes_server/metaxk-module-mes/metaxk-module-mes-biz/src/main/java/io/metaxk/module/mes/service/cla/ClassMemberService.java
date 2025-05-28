package io.metaxk.module.mes.service.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassMember;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/6/27 16:07
 */
public interface ClassMemberService {

    /**
     * 新增班组人员
     * @param classMember
     */
    void saveClassMember(List<ClassMember> classMember);


    /**
     * 根据人员id，名称查询班组人员
     * @param personCode
     * @param userName
     * @return ClassMember
     */
    ClassMember findMemberByCodeAndName(String personCode, String userName);

    /**
     * 班组人员条件分页查询
     * @param classMemberPageReqVO
     * @return PageResult<ClassMember>
     */
    PageResult<ClassMember> selectClassMemberByCode(ClaMemberQueryVo classMemberPageReqVO);

    /**
     * 根据班组编号查询班组人员
     * @param code
     * @return List<ClassMember>
     */
    List<ClassMember> findMemberByCode(String code);


    /**
     * 根据班组类型，班组编号，班组名称查询班组成员
     * @param teamCode
     * @param teamType
     * @param teamName
     * @return List<ClassMember>
     */
    List<ClassMember> findMemberByCodeAndTypeAndName(String teamCode, String teamType, String teamName);

    /**
     * 根据人员id人员姓名查询班组成员
     * @param peopleId
     * @param teamPeople
     * @return ClassMember
     */
    ClassMember findMemberByPeopleIdAndName(Long peopleId, String teamPeople);

    /**
     * 修改班组成员
     * @param classMember
     * @return Integer
     */
    Integer updateClassMember(ClassMember classMember);

    /**
     * 批量删除班组成员
     * @param ids
     * @return Integer
     */
    Integer removeByPeopleId(List<Long> ids);

    /**
     * 根据班组编号查询未加入班组人员
     * @param teamCode
     * @return  List<ClassMember>
     */
    List<ClassMember> findNotArrangMemberByCode(String teamCode);

    /**
     * 根据班组编号查询已加入班组人员
     * @param teamCode
     * @return List<ClassMember>
     */
    List<ClassMember> findArrangMemberByCode(String teamCode);

    /**
     * 删除班组成员
     * @param id
     * @return Integer
     */
    Integer removeById(Long id);

    /**
     * 班组成员详情
     * @param id
     * @return ClassMember
     */
    ClassMember findMemberById(Long id);

    /**
     * 根据班组编号查询班组成员
     * @param teamCode
     * @return List<ClassMember>
     */
    List<ClassMember> findMemberByCodes(String teamCode);
}
