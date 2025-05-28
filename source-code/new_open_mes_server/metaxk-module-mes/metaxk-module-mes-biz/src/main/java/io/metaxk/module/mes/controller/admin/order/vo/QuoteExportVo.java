package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/23 14:14
 */
@Data
public class QuoteExportVo {

    @ExcelProperty("客户名称")
    private String  customerName;

    @ExcelProperty("线别")
    private String  lineType;

    @ExcelProperty("型号")
    private String  model;

    @ExcelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ExcelProperty("规格")
    private String spec;


    @ExcelProperty("开始规格")
    private BigDecimal startSpec;

    @ExcelProperty("结束规格")
    private BigDecimal endSpec;

    @ExcelProperty("加工费")
    private BigDecimal price;


}
