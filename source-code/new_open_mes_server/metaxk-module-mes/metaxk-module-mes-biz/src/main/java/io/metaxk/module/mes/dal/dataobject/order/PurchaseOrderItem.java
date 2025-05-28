package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;


/**
 * @author 万界星空
 * @time 2023/7/18 15:12
 */
@Data
@TableName("order_po_item")
public class PurchaseOrderItem extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 编号
     */
    private String itemCode;

    /**
     * 采购单编号
     */
    private String receiptNumber;

    /**
     * 名称
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
     * 类别
     */
    private String kind;

    /**
     * 单位
     */
    private String unitOfMeasure;

    /**
     *采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 	数量
     */
    private BigDecimal quantity;

    /**
     * 	已到数量
     */
    private BigDecimal arrivedQuantity;

    /**
     * 含税总价
     */
    private BigDecimal includTax;

    /**
     * 不含税总价
     */
    private BigDecimal noIncludTax;

    /**
     * 	供应商
     */
    private String vendor;


    /**
     * 	状态（此状态为是否加入到采购入库中）
     */
    private String status;

    @TableField(exist = false)
    public BigDecimal  totalPrice;
}
