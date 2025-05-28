package io.metaxk.module.mes.controller.admin.order.vo;

import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/8/11 10:37
 */
@Data
public class PurchaseOrderVo {


    /**
     * 采购订单编号
     */
    private String number;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购日期
     */
    private String deliveryDate;

    /**
     * 产品编号
     */
 //   private String itemCode;

    /**
     * 产品名称
     */
    private String itemName;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;

    /**
     * 规格
     */
   // private String kind;

    /**
     * 单位
     */
    private String unitOfMeasure;

    /**
     * 采购价
     */
    private String purchasePrice;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 含税总价
     */
   // private String includTax;

    /**
     * 不含税总价
     */
   // private String noIncludTax;





}
