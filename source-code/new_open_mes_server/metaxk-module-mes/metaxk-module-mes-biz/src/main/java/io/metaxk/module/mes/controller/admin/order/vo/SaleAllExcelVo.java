package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/25 10:59
 */

@Data
public class SaleAllExcelVo {

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

    /**
     * 产品编号
     */
    @ExcelProperty("产品编号")
    private String productNumber;

    /**
     * 线别
     */
    @ExcelProperty("线别")
    private String lineType;

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String model;

    /**
     * 数量
     */
    @ExcelProperty("数量")
    private BigDecimal itemQuantity;

    /**
     * 规格
     */
    @ExcelProperty("规格")
    private String spec;

    /**
     * 原材料价
     */
    @ExcelProperty("原材料价")
    private BigDecimal rawPrice;

    /**
     * 加工费
     */
    @ExcelProperty("加工费")
    private BigDecimal processingFee;

    /**
     * 单价
     */
    @ExcelProperty("单价")
    private String itemPrice;

    /**
     * 单位
     */
    @ExcelProperty("单位")
    private String unit;

    /**
     * 库存
     */
    @ExcelProperty("库存")
    private BigDecimal stocks;

    /**
     * 颜色
     */
    @ExcelProperty("颜色")
    private String color;

    /**
     * 盘号
     */
    @ExcelProperty("盘号")
    private String panhao;

    /**
     * 客户代码
     */
    @ExcelProperty("客户代码")
    private String customerCode;

    /**
     *存货编号
     */
    @ExcelProperty("存货编号")
    private String inventoryNumber;

    /**
     * 制令单号
     */
    @ExcelProperty("制令单号")
    private String warrantNumber;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String itemRemark;

}
