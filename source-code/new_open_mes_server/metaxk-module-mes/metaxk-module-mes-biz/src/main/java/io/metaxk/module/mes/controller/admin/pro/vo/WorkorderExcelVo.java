package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 生产订单 ExcelVo
 */
@Data
public class WorkorderExcelVo {


    @ExcelProperty("订单编码")
    private String workorderCode;

    @ExcelProperty("订单名称")
    private String workorderName;

    @ExcelProperty("订单数量")
    private BigDecimal quantity;


    @ExcelProperty("产品编号")
    private String productCode;

    @ExcelProperty("产品名称")
    private String productName;

    @ExcelProperty("规格型号")
    private String productSpc;

    @ExcelProperty("排产状态")
    private String status;

    @ExcelProperty("生产进度(百分比)")
    private BigDecimal productionSchedule;

    @ExcelProperty("排产进度(百分比)")
    private BigDecimal produceProgress;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("客户名称")
    private String clientName;

    @ExcelProperty("生产订单日期")
    private String orderDate;

    @ExcelProperty("生产日期")
    private String produceDate;

    @ExcelProperty("需求日期")
    private String requestDate;

}
