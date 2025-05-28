package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 采购退货单实体类
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
@Data
@TableName("order_po_return")
public class PurchaseOrderReturn extends EntityCommon {

    /**
     * 采购退货id
     */
    private Long id;

    /**
     * 采购退货单编号
     */
    private String number;

    /**
     * 采购单编号
     */
    private String poNumber;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 供应商名称
     */
    private String vendorName;


    /**
     * 退货日期
     */
    private String returnDate;

    /**
     * 仓库
     */
    private String warehouse;


    /**
     * 	数量
     */
    private BigDecimal quantity;

    /**
     *金额
     */
    private BigDecimal totalPrice;


    /**
     * 状态
     */
    private String status;


    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<PurchaseOrderReturnItem> purchaseOrderReturnItemList;
}
