package io.metaxk.module.mes.controller.admin.plan.shift.vo.query;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.plan.shift.vo
 *
 * @author 万界星空
 * @time 2023/8/7 11:00
 */

@Data
public class SelectShiftAttendanceQuery extends PageParam {

    private String date;

}
