package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;


/**
 * 销售订单子类实体类
 * @author 万界星空MES
 */
@Data
@TableName("order_sale_item")
public class SaleItem extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 销售单号
     */
    private String saleNumber;

    /**
     * 销售明细编号
     */
    private String saleItemNumber;

    /**
     * 销售明细编号
     */
    private String number;

    /**
     * 产品编号
     */
    private String productNumber;

    /**
     * 线别
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
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 原材料价
     */
    private BigDecimal rawPrice;

    /**
     * 加工费
     */
    private BigDecimal processingFee;

    /**
     * 单价
     */
    private String price;

    /**
     * 库存
     */
    private BigDecimal stocks;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 折扣
     */
    private Long discount;

    /**
     * 颜色
     */
    private String color;

    /**
     * 盘号
     */
    private String panhao;

    /**
     * 客户代码
     */
    private String customerCode;

    /**
     *存货编号
     */
    private String inventoryNumber;

    /**
     * 制令单号
     */
    private String warrantNumber;

    /**
     * 规格价格
     */
    private String specPrice;


    /**
     * 状态
     */
    private String status;


    /**
     * 出库状态
     */
    private String outboundStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 总净重
     */
    @TableField(exist = false)
    private BigDecimal  totalQuantity;

    /**
     * 总皮重
     */
    @TableField(exist = false)
    private BigDecimal totalTare;

    /**
     * 件数
     */
    @TableField(exist = false)
    private BigDecimal pieces;



}
