package io.metaxk.module.mes.dal.dataobject.wh;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 到货通知明细实体类
 * @author 万界星空
 * @time 2023/7/25 17:12
 */
@Data
@TableName("wh_inbound_rec_bill_item")
public class InboundRecBillItem extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 到货通知单编号
     */
    private String recNumber;

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
     *采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 	到货数量
     */
    private BigDecimal quantity;

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
     * 质检状态0:未质检1:已质检
     */
    private String state;

    /**
     * 	状态（此状态为是否加入到采购入库中）
     * 	0代表明细未加入到采购入库中，1代表明细已经加入到采购入库中
     */
    private String status;


    @TableField(exist = false)
    public BigDecimal  totalPrice;

    /**
     * 	采购订单编号
     */
    @TableField(exist = false)
    public String  receiptNumber;

    /**
     * 	入库数量
     */
    @TableField(exist = false)
    public BigDecimal  amount;

    /**
     * 	不合格数
     */
    @TableField(exist = false)
    public BigDecimal  unqualifiedNumber;
}
