package io.metaxk.module.mes.controller.admin.plan.shift.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.controller.admin.plan.shift.vo
 *
 * @author 万界星空
 * @time 2023/8/8 10:27
 */

@Data
public class InsertShiftAttendanceReqVo {

    /**
     * 编号
     */
    @NotBlank(message = "考勤编号不能为空")
    private String number;
    /**
     * 日期
     */
    @NotBlank(message = "考勤日期不能为空")
    private String date;
    /**
     * 车间
     */
    @NotBlank(message = "考勤车间不能为空")
    private String workshop;
    /**
     * 生产线
     */
    @NotBlank(message = "考勤生产线不能为空")
    private String productionLine;
    /**
     * 班次
     */
    @NotBlank(message = "考勤班次不能为空")
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
