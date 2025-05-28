package io.metaxk.module.mes.controller.admin.order.vo;

import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/8/18 13:52
 */
@Data
public class SaleCountVo {

    /**
     * 出库单号
     */
    private String number;

    /**
     * 客户单号
     */
    private String customerNumber;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户订单号
     */
    private String customerOrderNumber;

    /**
     * 含税价
     */
    private String includeTax;

    /**
     * 不含税价
     */
    private String noIncludeTax;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 已发
     */
    private String sendOut;

    /**
     * 未发
     */
    private String noSend;

    /**
     * 销售单号
     */
    private String saleNumber;

    /**
     * 产品编号
     */
    private String itemCode;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;

    /**
     * 线别
     */
    private String lineType;

    /**
     * 件数
     */
    private String pieces;

    /**
     * 总皮重
     */
    private String totalTare;



}
