package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/23 14:46
 */
@Data
public class SaleExcelVo {

    @ExcelProperty("订单编号")
    private String number;

    @ExcelProperty("客户编号")
    private String customerNumber;

    @ExcelProperty("客户名称")
    private String customerName;

    @ExcelProperty("客户订单号")
    private String customerOrderNumber;

    @ExcelProperty("总数量")
    private BigDecimal quantity;

    @ExcelProperty("件数")
    private String priceModel;

    @ExcelProperty("金额")
    private BigDecimal price;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ExcelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ExcelProperty("备注")
    private String remark;


}
