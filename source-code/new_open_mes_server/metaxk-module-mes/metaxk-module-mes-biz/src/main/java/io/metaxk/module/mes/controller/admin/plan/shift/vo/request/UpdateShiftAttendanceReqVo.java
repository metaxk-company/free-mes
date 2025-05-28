package io.metaxk.module.mes.controller.admin.plan.shift.vo.request;

import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.plan.shift.vo
 *
 * @author 万界星空
 * @time 2023/8/7 11:00
 */

@Data
public class UpdateShiftAttendanceReqVo {

    /**
     * 考勤id
     */
    private Long id;
    /**
     * 日期
     */
    private String date;
    /**
     * 车间
     */
    private String workshop;
    /**
     * 生产线
     */
    private String productionLine;
    /**
     * 班次
     */
    private String shiftNumber;
    /**
     * 应出勤
     */
    private Long attendanceRequired;
    /**
     * 实出勤
     */
    private Long attendanceActual;

}
