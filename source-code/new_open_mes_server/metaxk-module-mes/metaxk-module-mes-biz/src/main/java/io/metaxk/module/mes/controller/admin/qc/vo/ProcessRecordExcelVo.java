package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 工序检验单excelVO
 * @author 万界星空
 * @time 2023/7/11 10:24
 */

@Data
@Accessors(chain = false)
public class ProcessRecordExcelVo {



    @ExcelProperty("检验单编号")
    private String  recordCode;

    @ExcelProperty("任务单号")
    private String  taskCode;

    @ExcelProperty("订单编号")
    private String  orderCode;

    @ExcelProperty("工序编号")
    private String  processCode;

    @ExcelProperty("工序名称")
    private String  processName;


    @ExcelProperty("数量")
    private BigDecimal quantity;

    @ExcelProperty("报工人")
    private String  reportUser;

    @ExcelProperty("检验方式")
    private String  inspectWay;

    @ExcelProperty("版本")
    private String version;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("单据日期")

    private Date orderDate;

    @ExcelProperty("单据状态")
    private String  status;

    @ExcelProperty("质检组")
    private String  inspectGroup;

    @ExcelProperty("质检员")
    private String  inspectUser;


}
