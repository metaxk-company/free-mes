package io.metaxk.module.mes.controller.admin.pro.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Accessors(chain = false)
public class FeedbackHoursExcelVo {

    @ExcelProperty("订单编号")
    private String  workorderCode;

    @ExcelProperty("任务编号")
    private String  taskCode;

    @ExcelProperty("产品名称")
    private String  itemName;

    @ExcelProperty("单位")
    private String  unitOfMeasure;

    @ExcelProperty("数量")
    private BigDecimal quantity;


    @ExcelProperty("报工时间")
    private LocalDateTime feedbackTime;


    @ExcelProperty("工作站编号")
    private String workstationCode;

    @ExcelProperty("车间编号")
    private String workshopCode;

    @ExcelProperty("工序编号")
    private String processCode;


    @ExcelProperty("人工工时")
    private String  workHour;

    @ExcelProperty("设备工时")
    private String  equipmentHour;

    @ExcelProperty("设备编号")
    private String  equipmentCode;


    @ExcelProperty("人工完工时间")
    private LocalDateTime workerFinishedTime;


    @ExcelProperty("设备完工时间")
    private LocalDateTime equipmentFinishedTime;



    @ExcelProperty("报工人")
    private String userName;


    @ExcelProperty("人工总工时")
    private String totalWorkHours;

    @ExcelProperty("设备总工时")
    private String equipmenTotalWorkhours;



}
