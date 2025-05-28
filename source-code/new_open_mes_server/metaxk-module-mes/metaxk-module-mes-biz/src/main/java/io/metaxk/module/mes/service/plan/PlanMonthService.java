package io.metaxk.module.mes.service.plan;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanMonthQuery;
import io.metaxk.module.mes.controller.admin.plan.vo.request.InsertPlanMonthReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.request.UpdatePlanMonthReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.response.SelectPlanMonthResVo;

import java.util.List;

/**
 * io.metaxk.module.mes.dal.service.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:23
 */

public interface PlanMonthService {
    Integer savePlanMonth(InsertPlanMonthReqVo insertPlanMonthReqVo);

    Integer removePlanMonthByIds(List<Long> ids);

    Integer updatePlanMonthById(UpdatePlanMonthReqVo updatePlanMonthReqVo);

    SelectPlanMonthResVo selectPlanMonthWithPlanDayById(Long id);

    PageResult<SelectPlanMonthResVo> selectPlanMonthList(SelectPlanMonthQuery selectPlanMonthQuery);
}
