package io.metaxk.module.mes.controller.admin.plan.vo.query;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.plan.vo
 *
 * @author 万界星空
 * @time 2023/8/4 09:38
 */


@Data
public class SelectPlanDayQuery extends PageParam {

    private String day;

}
