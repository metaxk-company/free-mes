package io.metaxk.module.mes.service.impl.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanExcelVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.PlanTeamQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ScheduleMemberVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlan;
import io.metaxk.module.mes.dal.mysql.cla.ClassPlanMapper;
import io.metaxk.module.mes.service.cla.ClassPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 排版计划  实现类
 * @author 万界星空
 * @time 2023/6/26 13:31
 */
@Service
public class ClassPlanServiceImpl implements ClassPlanService {

    @Resource
    private ClassPlanMapper classPlanMapper;


    @Override
    public Integer saveCalPlan(ClassPlan calPlan) {
       return classPlanMapper.insert(calPlan);
    }



    @Override
    public Integer removeCalPlanByIds(List<Long> ids) {
        return classPlanMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateCalPlan(ClassPlan calPlan) {
        return classPlanMapper.updateById(calPlan);
    }

    @Override
    public PageResult<ClassPlan> getCalPlanPage(ClaPlanQueryVo calPlanPageReqVO) {
        return classPlanMapper.selectPage(calPlanPageReqVO);
    }

    @Override
    public ClassPlan getCalPlanById(Long id) {
        return classPlanMapper.selectById(id);
    }

    @Override
    public List<ClaPlanExcelVo> exportData() {
        List<ClassPlan> list = classPlanMapper.selectList();
        ArrayList<ClaPlanExcelVo> excelList = new ArrayList<>(list.size());
        list.forEach(dict -> {
            ClaPlanExcelVo excelDTO = new ClaPlanExcelVo();
            BeanUtils.copyProperties(dict, excelDTO);
            excelList.add(excelDTO);
        });
        return excelList;
    }

    @Override
    public ClassPlan findCalPlanByCode(String shiftNum) {
        LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlan::getPlanCode,shiftNum);
        return classPlanMapper.selectOne(queryWrapperX);
    }

    @Override
    public ClassPlan findCalPlanByName(String shiftName) {
        LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlan::getPlanName,shiftName);
        return classPlanMapper.selectOne(queryWrapperX);
    }

    @Override
    public PageResult<ClassPlan> findCalPlanByShiftType(ClaPlanQueryVo calPlanPageReqVO) {
        return classPlanMapper.findCalPlanByShiftType(calPlanPageReqVO);
    }



    @Override
    public ClassPlan findCalPlanById(Long id) {
        return classPlanMapper.selectById(id);
    }

    @Override
    public ClassPlan findCalPlanByCondition(String teamType, String teamCode, String teamName) {
        LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlan::getTeamType,teamType);
        queryWrapperX.eq(ClassPlan::getTeamCode,teamCode);
        queryWrapperX.eq(ClassPlan::getTeamName,teamName);
        return classPlanMapper.selectOne(queryWrapperX);
    }

    @Override
    public ClassPlan findPlanByCodeAndTypeAndName(String planCode1, String code, String teamType, String teamName) {
        LambdaQueryWrapperX<ClassPlan> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassPlan::getTeamType,teamType);
        queryWrapperX.eq(ClassPlan::getPlanCode,planCode1);
        queryWrapperX.eq(ClassPlan::getTeamCode,code);
        queryWrapperX.eq(ClassPlan::getTeamName,teamName);
        return classPlanMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<ClassPlan> findClassPlanList() {
        return classPlanMapper.selectList();
    }

    @Override
    public PageResult<ClassPlan> findPlanByTime(PlanTeamQueryVo planTeamPageVO) {
        return classPlanMapper.findPlanByTime(planTeamPageVO);
    }

    @Override
    public List<ScheduleMemberVo> findMemberByCodeAndWay(String teamCode, String shiftWay) {
        return classPlanMapper.findMemberByCodeAndWay(teamCode,shiftWay);
    }


}
