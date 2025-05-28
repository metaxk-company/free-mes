package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/23 16:36
 */
@Data
public class ReturnExcelVo {

    @ExcelProperty("编号")
    private String  number;

    @ExcelProperty("总净重")
    private BigDecimal weight;

    @ExcelProperty("客户名")
    private String customerName;

    @ExcelProperty("总金额")
    private BigDecimal totalPrice;

    @ExcelProperty("退货日期")
    private String returnDate;

    @ExcelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
