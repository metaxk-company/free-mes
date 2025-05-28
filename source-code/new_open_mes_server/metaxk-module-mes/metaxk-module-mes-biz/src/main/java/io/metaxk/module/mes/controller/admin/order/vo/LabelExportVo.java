package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/23 16:54
 */

@Data
public class LabelExportVo {

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String  model;

    /**
     * 规格
     */
    @ExcelProperty("规格")
    private String  spec;

    /**
     * 箱号
     */
    @ExcelProperty("箱号")
    private String  boxNumber;

    /**
     * 批号
     */
    @ExcelProperty("批号")
    private String  batchNumber;

    /**
     * 线别
     */
    @ExcelProperty("线别")
    private String  lineType;

    /**
     * 盘号
     */
    @ExcelProperty("盘号")
    private String  reelNumber;

    /**
     * 颜色
     */
    @ExcelProperty("颜色")
    private String  color;

    /**
     * 客户代码
     */
    @ExcelProperty("客户代码")
    private String  clientCode;

    /**
     * 条码
     */
    @ExcelProperty("条码")
    private String  barCode;

    /**
     * 轴数
     */
    @ExcelProperty("轴数")
    private String  axlesNum;

    /**
     * 总重量
     */
    @ExcelProperty("总净重")
    private BigDecimal  totalHeight;

    /**
     * 1轴净重
     */
    @ExcelProperty("1轴净重")
    private String  oneAxleHeight;

    /**
     * 2轴净重
     */
    @ExcelProperty("2轴净重")
    private String  twoAxleHeight;

    /**
     * 3轴净重
     */
    @ExcelProperty("3轴净重")
    private String  threeAxleHeight;

    /**
     * 4轴净重
     */
    @ExcelProperty("4轴净重")
    private String  fourAxleHeight;

    /**
     * 托盘编号
     */
    @ExcelProperty("托盘编号")
    private String  palletNumber;

    /**
     * 托盘数量
     */
    @ExcelProperty("托盘数量")
    private String  palletQuantity;

    /**
     * 是否退货
     */
    @ExcelProperty("是否退货")
    private String  returnGood;

    /**
     * 退货日期
     */
    @ExcelProperty("退货日期")
    private String  returnDate;

    /**
     * 是否重包
     */
    @ExcelProperty("是否重包")
    private String  hePackage;

    /**
     * 状态
     */
    @ExcelProperty("状态")
    private String  status;

}
