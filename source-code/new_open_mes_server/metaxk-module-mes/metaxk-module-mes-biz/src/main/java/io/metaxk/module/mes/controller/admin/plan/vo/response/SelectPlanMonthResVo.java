package io.metaxk.module.mes.controller.admin.plan.vo.response;

import io.metaxk.module.mes.dal.dataobject.plan.PlanDay;
import io.metaxk.module.mes.dal.dataobject.plan.PlanMonth;
import lombok.Data;

import java.util.List;

/**
 * io.metaxk.module.mes.controller.admin.plan.vo
 *
 * @author 万界星空
 * @time 2023/8/4 13:26
 */

@Data
public class SelectPlanMonthResVo extends PlanMonth {

    private List<PlanDay> dayList;

}
