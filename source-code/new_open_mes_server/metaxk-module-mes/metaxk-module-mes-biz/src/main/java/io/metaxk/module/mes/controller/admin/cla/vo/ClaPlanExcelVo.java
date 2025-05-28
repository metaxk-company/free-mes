package io.metaxk.module.mes.controller.admin.cla.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/6/26 14:01
 */
@Data
public class ClaPlanExcelVo {

    @ExcelProperty("'排班编号")
    private String planCode;

    @ExcelProperty("'排班名称")
    private String planName;

    @ExcelProperty("'班组类型")
    private String teamType;

    @ExcelProperty("'班组编号")
    private String teamCode;

    @ExcelProperty("班组名称")
    private String teamName;

    @ExcelProperty("轮班时长")
    private String shiftDuration;

    @ExcelProperty("轮班方式")
    private String shiftWay;

    @ExcelProperty("'倒班方式")
    private String changeShiftWay;

    @ExcelProperty("'开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @ExcelProperty("'结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

}
