package io.metaxk.module.mes.service.impl.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassMember;
import io.metaxk.module.mes.dal.mysql.cla.ClassMemberMapper;
import io.metaxk.module.mes.service.cla.ClassMemberService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/6/27 16:07
 */
@Service
public class ClassMemberServiceImpl implements ClassMemberService {


    @Resource
    private ClassMemberMapper classMemberMapper;


    @Override
    public void saveClassMember(List<ClassMember> classMember) {
         classMemberMapper.insertBatch(classMember);
    }


    @Override
    public ClassMember findMemberByCodeAndName(String personCode, String userName) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getPersonCode,personCode);
        queryWrapperX.eq(ClassMember::getUserName,userName);
        return classMemberMapper.selectOne(queryWrapperX);
    }

    @Override
    public PageResult<ClassMember> selectClassMemberByCode(ClaMemberQueryVo classMemberPageReqVO) {
        PageResult<ClassMember> pageResult = classMemberMapper.selectClassMemberByCode(classMemberPageReqVO);
        return pageResult;
    }


    @Override
    public List<ClassMember> findMemberByCode(String code) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getTeamCode,code);
        queryWrapperX.eq(ClassMember::getJoinProgram,"N");
        return classMemberMapper.selectList(queryWrapperX);
    }



    @Override
    public List<ClassMember> findMemberByCodeAndTypeAndName(String teamCode, String teamType, String teamName) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getTeamCode,teamCode);
        queryWrapperX.eq(ClassMember::getTeamType,teamType);
        queryWrapperX.eq(ClassMember::getTeamName,teamName);
        return classMemberMapper.selectList(queryWrapperX);
    }



    @Override
    public ClassMember findMemberByPeopleIdAndName(Long peopleId, String teamPeople) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getPersonCode,peopleId);
        queryWrapperX.eq(ClassMember::getUserName,teamPeople);
        return classMemberMapper.selectOne(queryWrapperX);
    }



    @Override
    public Integer updateClassMember(ClassMember classMember) {
        return classMemberMapper.updateById(classMember);
    }

    @Override
    public Integer removeByPeopleId(List<Long> ids) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(ClassMember::getPersonCode,ids);
        return classMemberMapper.delete(queryWrapperX);
    }

    @Override
    public List<ClassMember> findNotArrangMemberByCode(String teamCode) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getTeamCode,teamCode);
        queryWrapperX.eq(ClassMember::getJoinProgram,"Y");
        return classMemberMapper.selectList(queryWrapperX);
    }

    @Override
    public List<ClassMember> findArrangMemberByCode(String teamCode) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getTeamCode,teamCode);
        return classMemberMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeById(Long id) {
        return classMemberMapper.deleteById(id);
    }

    @Override
    public ClassMember findMemberById(Long id) {
        return classMemberMapper.selectById(id);
    }

    @Override
    public List<ClassMember> findMemberByCodes(String teamCode) {
        LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassMember::getTeamCode,teamCode);
        return classMemberMapper.selectList(queryWrapperX);
    }


}
