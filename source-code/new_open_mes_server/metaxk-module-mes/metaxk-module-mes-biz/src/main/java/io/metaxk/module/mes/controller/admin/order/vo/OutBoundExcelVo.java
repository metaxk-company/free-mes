package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 万界星空
 * @time 2023/8/23 15:10
 */
@Data
public class OutBoundExcelVo {

    @ExcelProperty("出库单号")
    private String number;

    @ExcelProperty("出库总价")
    private BigDecimal outboundTotalPrice;

    @ExcelProperty("客户名")
    private String  customerName;

    @ExcelProperty("是否含税")
    private String   isTax;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("已发")
    private BigDecimal sendOut;

    @ExcelProperty("未发")
    private BigDecimal noSend;

    @ExcelProperty("备注")
    private String remark;

}
