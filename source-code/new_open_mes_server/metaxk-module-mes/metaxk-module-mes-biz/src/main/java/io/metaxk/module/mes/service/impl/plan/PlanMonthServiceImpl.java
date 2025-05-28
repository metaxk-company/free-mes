package io.metaxk.module.mes.service.impl.plan;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanMonthQuery;
import io.metaxk.module.mes.controller.admin.plan.vo.request.InsertPlanMonthReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.request.UpdatePlanMonthReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.response.SelectPlanMonthResVo;
import io.metaxk.module.mes.dal.dataobject.plan.PlanDay;
import io.metaxk.module.mes.dal.dataobject.plan.PlanMonth;
import io.metaxk.module.mes.dal.mysql.plan.PlanDayMapper;
import io.metaxk.module.mes.dal.mysql.plan.PlanMonthMapper;
import io.metaxk.module.mes.service.plan.PlanMonthService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.PLAN_MONTH_ID_NOT_EXIST;

/**
 * io.metaxk.module.mes.dal.service.impl.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:23
 */

@Service
public class PlanMonthServiceImpl implements PlanMonthService {

    @Resource
    private PlanMonthMapper planMonthMapper;

    @Resource
    private PlanDayMapper planDayMapper;

    @Override
    public Integer savePlanMonth(InsertPlanMonthReqVo insertPlanMonthReqVo) {
        //vo -> bean
        PlanMonth planMonth = new PlanMonth();
        BeanUtils.copyProperties(insertPlanMonthReqVo, planMonth);

        if (planMonthMapper.selectOne(Wrappers.lambdaQuery(PlanMonth.class).eq(PlanMonth::getNumber, planMonth.getNumber())) != null
                || planMonthMapper.selectOneFromDeleted(planMonth.getNumber()) != null)
            // -1 代表数据库中已经存在了该月计划编号或被废弃
            return -1;

        return planMonthMapper.insert(planMonth.setPlanQty(0.0));
    }

    @Override
    public Integer removePlanMonthByIds(List<Long> ids) {
        //防止空指针
        try {
            ids.forEach(id ->
                    planDayMapper.delete(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getMonthNumber, planMonthMapper.selectById(id).getNumber()))
            );
        } catch (Exception e) {
            System.out.println(e);
            throw exception(PLAN_MONTH_ID_NOT_EXIST);
        }
        return planMonthMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updatePlanMonthById(UpdatePlanMonthReqVo updatePlanMonthReqVo) {
        if (planMonthMapper.selectById(updatePlanMonthReqVo.getId()) == null)
            //没有找到这个id
            return -1;

        PlanMonth planMonth = new PlanMonth();
        BeanUtils.copyProperties(updatePlanMonthReqVo, planMonth);
        //取monthNumber
        planMonth.setNumber(planMonthMapper.selectById(planMonth.getId()).getNumber());

        //如果修改了月计划的车间或生产线，关联的日计划也将被修改 isNotNull是为了避免前端月计划修改传空格时此处不会同步进行修改
        if (StringUtils.isNotNull(planMonth.getWorkshop())) {
            planDayMapper.selectList(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getMonthNumber, planMonth.getNumber())).forEach(planDay ->
                    planDayMapper.updateById(new PlanDay().setId(planDay.getId()).setWorkshop(planMonth.getWorkshop()))
            );
        }
        if (StringUtils.isNotNull(planMonth.getProductionLine())) {
            planDayMapper.selectList(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getMonthNumber, planMonth.getNumber())).forEach(planDay ->
                    planDayMapper.updateById(new PlanDay().setId(planDay.getId()).setProductionLine(planMonth.getProductionLine()))
            );
        }

        return planMonthMapper.updateById(planMonth);
    }

    @Override
    public SelectPlanMonthResVo selectPlanMonthWithPlanDayById(Long id) {
        //bean -> vo
        SelectPlanMonthResVo selectPlanMonthResVo = new SelectPlanMonthResVo();
        //避免id不存在时的空指针
        try {
            BeanUtils.copyProperties(planMonthMapper.selectById(id), selectPlanMonthResVo);
        } catch (Exception e) {
            //报空指针时直接返回null
            System.out.println("errors: " + e);
            return null;
        }
        return selectPlanMonthResVo.setDayList(planDayMapper.selectList(Wrappers.lambdaQuery(PlanDay.class).eq(PlanDay::getMonthNumber, selectPlanMonthResVo.getNumber())));
    }

    @Override
    public PageResult<SelectPlanMonthResVo> selectPlanMonthList(SelectPlanMonthQuery selectPlanMonthQuery) {
        //planMonthList to planMonthResVoList
        List<SelectPlanMonthResVo> selectPlanMonthResVoList = BeanCopyUtil.copyListProperties(planMonthMapper.pageQuery(selectPlanMonthQuery).getList(), SelectPlanMonthResVo::new);
        //获得所有的monthNumber
        Set<String> planMonthNumberSet = selectPlanMonthResVoList.stream().map(SelectPlanMonthResVo::getNumber).collect(Collectors.toSet());
        //拼接PlanMonthResVo
        if (!planMonthNumberSet.isEmpty()) {
            Map<String, List<PlanDay>> map = planDayMapper.selectList().stream().collect(Collectors.groupingBy(PlanDay::getMonthNumber));
            selectPlanMonthResVoList.forEach(selectPlanMonthResVo -> {
                if (map.get(selectPlanMonthResVo.getNumber()) != null)
                    selectPlanMonthResVo.setDayList(map.get(selectPlanMonthResVo.getNumber()));
                else
                    selectPlanMonthResVo.setDayList(new ArrayList<>());
            });
        }

        return new PageResult<>(selectPlanMonthResVoList, (long) selectPlanMonthResVoList.size());
    }
}
