package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;
import java.math.BigDecimal;

/**
 * @author 万界星空
 * @time 2023/7/20 15:38
 */
@Data
public class ProductResVo extends PageParam {

    /**
     * 产品编号
     */
    private String productNumber;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 	线别
     */
    private String lineType;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 成本价
     */
    private Double rawPrice;

    /**
     * 销售价
     */
    private Double price;

    /**
     * 加工费
     */
    private BigDecimal processingFee;

    /**
     * 库存数量
     */
    private BigDecimal stocks;

    /**
     * 分类名称
     */
    private String productType;

    /**
     * 客户名称
     */
    private String customerName;




    private String quantity;

    private BigDecimal totalPrice;


    /**
     * 客户订单号
     */
    private String orderNumber;

}
