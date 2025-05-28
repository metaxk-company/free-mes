package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 16:34
 */
@Data
@TableName("order_outbound")
public class Outbound extends EntityCommon {

    /**
     * id
     */
    private Long id;

    private String externalId;

    /**
     * 客户编号
     */
    private String  customerNumber;

    /**
     * 客户名称
     */
    private String  customerName;


    /**
     * 客户订单号
     */
    private String  customerOrderNumber;


    /**
     * 交货日期
     */
    private String  deliveryDate;


    /**
     * 是否含税
     */
    private String   isTax;

    /**
     * 剩余未发
     */
    private BigDecimal noSend;

    /**
     * 出库单编号
     */
    private String number;


    /**
     * 订单日期
     */
    private String orderDate;


    /**
     * 出库总价
     */
    private BigDecimal outboundTotalPrice;


    /**
     * 总件数
     */
    private String pieces;

    /**
     * 销售单编号
     */
    private String saleNumber;

    /**
     * 销售总价
     */
    private BigDecimal saleTotalPrice ;


    /**
     * 已发
     */
    private BigDecimal sendOut;

    /**
     * 来源
     */
    private String source;

    /**
     * 状态
     */
    private String status;

    /**
     * 总净重
     */
    private String totalWeight;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String remark;


    /**
     * 新加字段  数量
     */
    private BigDecimal quantity;

    private String isSales;



    @TableField(exist = false)
    public BigDecimal tare;

    @TableField(exist = false)
    private BigDecimal totalTare;



    @TableField(exist = false)
    private List<OutboundItem> orderOutboundItemList;


}
