package io.metaxk.module.mes.controller.admin.qc.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 来料检验单excelVO
 * @author 万界星空
 * @time 2023/7/26 15:24
 */

@Data
@Accessors(chain = false)
public class ReceiveRecordExcelVo {

    @ExcelProperty("来料检验单编号")
    private String recordCode;

    @ExcelProperty("到货通知单编号")
    private String recNumber;

    @ExcelProperty("产品编号")
    private String itemCode;

    @ExcelProperty("产品名称")
    private String itemName;

    @ExcelProperty("型号")
    private String model;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("类别")
    private String kind;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("采购价")
    private BigDecimal purchasePrice;

    @ExcelProperty("数量")
    private String quantity;

    @ExcelProperty("含税总价")
    private BigDecimal includTax;

    @ExcelProperty("不含税总价")
    private BigDecimal noIncludTax;

    @ExcelProperty("供应商")
    private String vendor;

    @ExcelProperty("检验方式")
    private String inspectWay;

    @ExcelProperty("版本")
    private String version;

    @ExcelProperty("质检组")
    private String inspectGroup;

    @ExcelProperty("质检员")
    private String inspectUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("检验开始时间")
    private Date inspectStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("检验结束时间")
    private Date inspectEndTime;
}
