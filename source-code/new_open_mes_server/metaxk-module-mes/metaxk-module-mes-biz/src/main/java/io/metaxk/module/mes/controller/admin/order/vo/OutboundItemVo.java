package io.metaxk.module.mes.controller.admin.order.vo;


import lombok.Data;
import java.math.BigDecimal;

/**
 * @author 万界星空
 * @time 2023/8/7 19:47
 */
@Data
public class OutboundItemVo {


    /**
     * 编号
     */
    private String  number;



    /**
     * 出库编号
     */
    private String  outboundNumber;


    /**
     * 销售单明细编号
     */
    private String  saleItemNumber;


    /**
     * 产品编号
     */
    private String itemCode;

    /**
     * 型号
     */
    private String  model;

    /**
     * 规格
     */
    private String  spec;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 线别
     */
    private String  lineType;

    /**
     * 客户代码
     */
    private String  customerCode;


    /**
     * 颜色
     */
    private String  color;


    /**
     * 盘号
     */
    private String  panhao;


    /**
     * 总价
     */
    private BigDecimal  totalPrice;

    /**
     * 单位
     */
    private String  unit;

    /**
     * 销售数量
     */
    private BigDecimal  quantity;

    /**
     * 已发
     */
    private BigDecimal  sendOut;

    /**
     * 未发
     */
    private BigDecimal  noSend;

    /**
     * 总净重
     */
    private BigDecimal totalWeight;

    /**
     * 皮重(g)
     */
    private BigDecimal  tare;

    /**
     * 总皮重
     */
    private BigDecimal  totalTare;

    /**
     * 件数
     */
    private String  pieces;

    /**
     * 备注
     */
    private String  remark;



    /**
     * 明细出库状态
     */
    private String itemStatus;



}
