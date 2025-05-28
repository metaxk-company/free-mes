package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/25 14:13
 */

@Data
public class OutBoundAllExcelVo {

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

    /**
     * 编号
     */
    @ExcelProperty("产品编号")
    private String itemNumber;

    /**
     * 销售单编号
     */
    @ExcelProperty("销售单编号")
    private String saleNumber;

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String model;

    /**
     * 客户名称
     */
    @ExcelProperty("产品客户名称")
    private String itemCustomerName;

    /**
     * 规格
     */
    @ExcelProperty("规格")
    private String spec;

    /**
     * 单价
     */
    @ExcelProperty("单价")
    private BigDecimal price;

    /**
     * 线别
     */
    @ExcelProperty("线别")
    private String lineType;

    /**
     * 客户代码
     */
    @ExcelProperty("客户代码")
    private String customerCode;

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
     * 总价
     */
    @ExcelProperty("总价")
    private BigDecimal totalPrice;

    /**
     * 单位
     */
    @ExcelProperty("单位")
    private String unit;

    /**
     * 销售数量
     */
    @ExcelProperty("销售数量")
    private BigDecimal quantity;

    /**
     * 已发
     */
    @ExcelProperty("产品已发")
    private BigDecimal itemSendOut;

    /**
     * 未发
     */
    @ExcelProperty("产品未发")
    private BigDecimal itemNoSend;
    /**
     * 总净重
     */
    @ExcelProperty("总净重")
    private BigDecimal totalWeight;

    /**
     * 皮重(g)
     */
    @ExcelProperty("皮重(g)")
    private BigDecimal tare;

    /**
     * 总皮重
     */
    @ExcelProperty("总皮重")
    private BigDecimal totalTare;

    /**
     * 件数
     */
    @ExcelProperty("件数")
    private String pieces;

    /**
     * 备注
     */
    @ExcelProperty("产品备注")
    private String itemRemark;

}
