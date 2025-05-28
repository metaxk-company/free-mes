package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/23 15:30
 */
@Data
public class PurchaseOrderExcelVo {

    @ExcelProperty("采购单号")
    private String number;

    @ExcelProperty("交货日期")
    private String deliveryDate;


    @ExcelProperty("供应商名称")
    private String vendorName;

    @ExcelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ExcelProperty("供应商")
    private  String vendor;

    @ExcelProperty("产品编号")
    private  String itemCode;

    @ExcelProperty("产品名称")
    private  String itemName;

    @ExcelProperty("型号")
    private  String model;

    @ExcelProperty("规格")
    private  String spec;

    @ExcelProperty("数量")
    private  BigDecimal quantity;

    @ExcelProperty("类别")
    private  String kind;

    @ExcelProperty("单位")
    private  String unitOfMeasure;

    @ExcelProperty("单价")
    private  String purchasePrice;

    @ExcelProperty("价格")
    private  String prices;





}
