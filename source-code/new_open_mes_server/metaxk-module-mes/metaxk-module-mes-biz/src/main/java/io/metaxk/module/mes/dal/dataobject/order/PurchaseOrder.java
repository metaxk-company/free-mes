package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


/**
 * 采购订单实体类
 * @author 万界星空
 * @time 2023/7/18 15:06
 */
@Data
@TableName("order_po")
public class PurchaseOrder extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 外部系统id
     */
    private String externalId;


    /**
     * 编号
     */
    private String number;

    /**
     * 币种
     */
    private String currency;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 交货日期
     */
    private String deliveryDate;

    /**
     * 产品类别
     */
    private String productType;

    /**
     * 不含税总价
     */
    private BigDecimal noTaxPrice;

    /**
     * 含税总价
     */
    private BigDecimal taxPrice;

    /**
     * 类型
     */
    private String status;

    /**
     * 类型0:未到货1:部分到货2:全部到货
     */
    private String state;


    /**
     * 是否含税
     */
    private String isTax;


    private String remark;


    @TableField(exist = false)
    private List<PurchaseOrderItem> receiptItemList;

}
