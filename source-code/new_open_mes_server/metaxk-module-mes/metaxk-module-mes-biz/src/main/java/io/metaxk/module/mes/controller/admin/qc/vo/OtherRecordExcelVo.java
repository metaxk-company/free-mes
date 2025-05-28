package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/19 10:43
 */
@Data
public class OtherRecordExcelVo {

    @ExcelProperty("检验单编号")
    private String number;

    @ExcelProperty("生产订单编号")
    private String workOrderCode;

    @ExcelProperty("产品编号")
    private String itemCode;

    @ExcelProperty("产品名称")
    private String itemName;

    @ExcelProperty("型号")
    private String model;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("线别")
    private String lineType;

    @ExcelProperty("盘号")
    private String reelNumber;

    @ExcelProperty("颜色")
    private String color;

    //@ExcelProperty("数量")
    //private BigDecimal quantity;

    @ExcelProperty("检验方式")
    private String inspectWay;

    //@ExcelProperty("版本")
    //private String version;

    @ExcelProperty("状态")
    private String status;//状态，0：不合格 1：合格

    @ExcelProperty("质检组")
    private String inspectGroup;

    @ExcelProperty("质检员")
    private String inspectUser;

   // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   // @ExcelProperty("检验开始时间")
   // private Date inspectStartTime;

  //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  //  @ExcelProperty("检验结束时间")
  //  private Date inspectEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("创建时间")
    private Date createTime;
}
