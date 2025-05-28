package io.metaxk.module.mes.controller.admin.pro.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;


@Data
@Accessors(chain = false)
public class TemporaryWorkHoursExcelVo {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("工时类型")
    private String workhoursType;

    @ExcelProperty("工时")
    private String workhours;

    @ExcelProperty("工人姓名")
    private String workerName;

    @ExcelProperty("所属车间")
    private String workshopName;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("时间")
    private LocalDateTime createTime;


    //自定义
    @ExcelProperty(value = "总工时")
    private Double totalWorkHours;



}
