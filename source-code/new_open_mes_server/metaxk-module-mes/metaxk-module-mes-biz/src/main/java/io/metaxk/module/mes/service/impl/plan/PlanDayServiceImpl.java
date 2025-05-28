package io.metaxk.module.mes.service.impl.plan;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanDayQuery;
import io.metaxk.module.mes.controller.admin.plan.vo.request.InsertPlanDayReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.request.UpdatePlanDayReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.response.SelectPlanDayResVo;
import io.metaxk.module.mes.dal.dataobject.plan.PlanDay;
import io.metaxk.module.mes.dal.dataobject.plan.PlanMonth;
import io.metaxk.module.mes.dal.mysql.plan.PlanDayMapper;
import io.metaxk.module.mes.dal.mysql.plan.PlanMonthMapper;
import io.metaxk.module.mes.service.plan.PlanDayService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * io.metaxk.module.mes.dal.service.impl.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:24
 */

@Service
public class PlanDayServiceImpl implements PlanDayService {

    @Resource
    private PlanDayMapper planDayMapper;

    @Resource
    private PlanMonthMapper planMonthMapper;

    @Override
    public Integer savePlanDay(InsertPlanDayReqVo insertPlanDayReqVo) {
        //vo -> bean
        PlanDay planDay = new PlanDay();
        BeanUtils.copyProperties(insertPlanDayReqVo, planDay);

        PlanMonth upPlanMonth = planMonthMapper.selectOne(Wrappers.lambdaQuery(PlanMonth.class).eq(PlanMonth::getNumber, planDay.getMonthNumber()));

        if (planDayMapper.selectOne(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getNumber, planDay.getNumber())) != null
                || planDayMapper.selectOneFromDeleted(planDay.getNumber()) != null)
            // -1 代表数据库中已经存在了该日计划编号或被废弃
            return -1;
        else if (upPlanMonth == null)
            // -2 代表无关联的月计划编号
            return -2;

        //车间和生产线和月计划需一致
        planDay.setWorkshop(upPlanMonth.getWorkshop()).setProductionLine(upPlanMonth.getProductionLine());
        //同步月计划的计划数量
        Double upPlanQty = upPlanMonth.getPlanQty();
        planMonthMapper.updateById(upPlanMonth.setPlanQty(upPlanQty + planDay.getPlanQty()));

        return planDayMapper.insert(planDay);
    }

    @Override
    public Integer removePlanDayByIds(List<Long> ids) {
        Double deleteQty = 0.0;
        Set<String> monthNumberSet = new HashSet<>();
        PlanDay planDay = new PlanDay();
        //foreach过程中return被视为continue，故使用增强for循环
        for (Long id : ids) {
            //-1 代表删除列表中有id是不存在的
            if (planDayMapper.selectOne(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getId, id)) == null)
                return -1;
            planDay = planDayMapper.selectOne(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getId, id));
            Objects.requireNonNull(monthNumberSet).add(planDay.getMonthNumber());
            //-2 代表删除列表中所有的PlanDay对应的PlanMonth必须是一样的
            if (monthNumberSet.size() > 1)
                return -2;
            deleteQty += planDay.getPlanQty();
        }
        //拿到关联的月计划
        PlanMonth upPlanMonth = planMonthMapper.selectOne(Wrappers.lambdaQuery(PlanMonth.class).eq(PlanMonth::getNumber, planDay.getMonthNumber()));
        //更新月计划的计划数量
        planMonthMapper.updateById(upPlanMonth.setPlanQty(upPlanMonth.getPlanQty() - deleteQty));
        return planDayMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updatePlanDayById(UpdatePlanDayReqVo updatePlanDayReqVo) {
        //判断修改的日计划id是否存在
        if (planDayMapper.selectById(updatePlanDayReqVo.getId()) == null)
            return -1;
        PlanDay planDay = new PlanDay();
        BeanUtils.copyProperties(updatePlanDayReqVo, planDay);
        //取monthNumber
        planDay.setMonthNumber(planDayMapper.selectById(planDay.getId()).getMonthNumber());

        Double oldDayQty = planDayMapper.selectById(updatePlanDayReqVo.getId()).getPlanQty();
        if (planDay.getPlanQty() != null && !planDay.getPlanQty().equals(oldDayQty)) {
            //拿到关联的月计划
            PlanMonth upPlanMonth = planMonthMapper.selectOne(Wrappers.lambdaQuery(PlanMonth.class).eq(PlanMonth::getNumber, planDay.getMonthNumber()));
            //更新月计划的计划数量
            planMonthMapper.updateById(upPlanMonth.setPlanQty(upPlanMonth.getPlanQty() + (planDay.getPlanQty() - oldDayQty)));
        }

        return planDayMapper.updateById(planDay);
    }

    @Override
    public SelectPlanDayResVo selectPlanDayById(Long id) {
        //bean -> vo
        SelectPlanDayResVo selectPlanDayResVo = new SelectPlanDayResVo();
        //避免id不存在时的空指针
        try {
            BeanUtils.copyProperties(planDayMapper.selectById(id), selectPlanDayResVo);
        } catch (Exception e) {
            //报空指针时直接返回null
            System.out.println("errors: " + e);
            return null;
        }
        return selectPlanDayResVo;
    }

    @Override
    public PageResult<SelectPlanDayResVo> selectPlanDayList(SelectPlanDayQuery selectPlanDayQuery) {
        //bean list -> vo list
        List<SelectPlanDayResVo> selectPlanDayResVoList = BeanCopyUtil.copyListProperties(planDayMapper.pageQuery(selectPlanDayQuery).getList(), SelectPlanDayResVo::new);
        return new PageResult<>(selectPlanDayResVoList, (long) selectPlanDayResVoList.size());
    }

}
