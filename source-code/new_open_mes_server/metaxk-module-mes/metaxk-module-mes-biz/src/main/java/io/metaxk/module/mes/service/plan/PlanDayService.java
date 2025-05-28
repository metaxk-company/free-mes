package io.metaxk.module.mes.service.plan;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanDayQuery;
import io.metaxk.module.mes.controller.admin.plan.vo.request.InsertPlanDayReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.request.UpdatePlanDayReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.response.SelectPlanDayResVo;

import java.util.List;

/**
 * io.metaxk.module.mes.dal.service.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:24
 */

public interface PlanDayService {
    Integer savePlanDay(InsertPlanDayReqVo insertPlanDayReqVo);

    Integer removePlanDayByIds(List<Long> ids);

    Integer updatePlanDayById(UpdatePlanDayReqVo updatePlanDayReqVo);

    SelectPlanDayResVo selectPlanDayById(Long id);

    PageResult<SelectPlanDayResVo> selectPlanDayList(SelectPlanDayQuery selectPlanDayQuery);
}
