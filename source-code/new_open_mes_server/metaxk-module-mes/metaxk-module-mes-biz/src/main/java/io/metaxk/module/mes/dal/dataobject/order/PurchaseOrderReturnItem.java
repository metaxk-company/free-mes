package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购退货单实体类
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
@Data
@TableName("order_po_return_item")
public class PurchaseOrderReturnItem extends EntityCommon {

    /**
     * 采购退货单明细id
     */
    private Long id;

    /**
     * 采购退货单编号
     */
    private String returnNumber;

    /**
     * 产品编号
     */
    private String itemCode;

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
     * 类别
     */
    private String kind;

    /**
     * 单位
     */
    private String unitOfMeasure;

    /**
     * 	原本数量
     */
    private BigDecimal quantity;

    /**
     * 实际退货数量
     */
    public BigDecimal  amount;

    /**
     *采购价
     */
    private BigDecimal purchasePrice;

    /**
     *金额
     */
    private BigDecimal totalPrice;


}
