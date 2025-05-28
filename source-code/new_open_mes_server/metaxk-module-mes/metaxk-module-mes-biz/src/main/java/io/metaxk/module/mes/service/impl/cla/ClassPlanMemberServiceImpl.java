package io.metaxk.module.mes.service.impl.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlanMember;
import io.metaxk.module.mes.dal.mysql.cla.ClassPlanMemberMapper;
import io.metaxk.module.mes.service.cla.ClassPlanMemberService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/6/30 13:48
 */
@Service
public class ClassPlanMemberServiceImpl implements ClassPlanMemberService {

    @Resource
    private ClassPlanMemberMapper classPlanMemberMapper;



    @Override
    public Integer saveClassPlanMember(ClassPlanMember classPlanMember) {
        return classPlanMemberMapper.insert(classPlanMember);
    }


    @Override
    public PageResult<ClassPlanMember> classPlanMemberPage(ClaPlanMemberQueryVo classPlanMemberPageReqVO) {
        return classPlanMemberMapper.findClassPlanMemberPage(classPlanMemberPageReqVO);
    }

    @Override
    public Integer removeByPlanCode(String planCode) {
        LambdaQueryWrapperX<ClassPlanMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(ClassPlanMember::getPlanCode,planCode);
        return classPlanMemberMapper.delete(queryWrapperX);
    }

    @Override
    public ClassPlanMember findByPeopleIdAndName( String planCode,Long peopleId, String teamPeople) {
        LambdaQueryWrapperX<ClassPlanMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlanMember::getPlanCode,planCode);
        queryWrapperX.eq(ClassPlanMember::getPeopleId,peopleId);
        queryWrapperX.eq(ClassPlanMember::getPlanPeopleName,teamPeople);
        return classPlanMemberMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<ClassPlanMember> findAll() {
        return classPlanMemberMapper.selectList();
    }

    @Override
    public List<ClassPlanMember> findByTypeAndCodeAndName(String teamType, String teamCode, String teamName) {
        LambdaQueryWrapperX<ClassPlanMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlanMember::getTeamType,teamType);
        queryWrapperX.eq(ClassPlanMember::getTeamCode,teamCode);
        queryWrapperX.eq(ClassPlanMember::getTeamName,teamName);
        return classPlanMemberMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeClassPlanMemberById(Long memberId) {
        return classPlanMemberMapper.deleteById(memberId);
    }

    @Override
    public List<ClassPlanMember> findClassPlanMember(Long id) {
        LambdaQueryWrapperX<ClassPlanMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlanMember::getId,id);
        return classPlanMemberMapper.selectList(queryWrapperX);
    }

    @Override
    public ClassPlanMember findPlanMemberByPeopleId(Long memberId) {
        LambdaQueryWrapperX<ClassPlanMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlanMember::getPeopleId,memberId);
        return classPlanMemberMapper.selectOne(queryWrapperX);
    }

    @Override
    public Integer updateClassPlanMember(ClassPlanMember classPlanMember1) {
        return classPlanMemberMapper.updateById(classPlanMember1);
    }


}
