package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 销售订单实体类
 *
 * @author 万界星空MES
 */
@Data
@TableName("order_sale")
public class Sale extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private String externalId;

    /**
     * 销售单编号
     */
    private String number;

    /**
     * 客户编号
     */
    private String customerNumber;

    /**
     *  收货地址
     */
    private String address;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户订单号
     */
    private String customerOrderNumber;

    /**
     * 备注
     */
    private String remark;


    /**
     * 订货日期
     */
    private String orderDate;

    /**
     * 是否含税
     */
    private String isTax;

    /**
     *  含税总价
     */
    private BigDecimal includeTax;

    /**
     * 交货日期
     */
    private String deliveryDate;


    /**
     * 总数量
     */
    private BigDecimal quantity;


    private BigDecimal noIncludeTax;


    private String priceMode;


    private String copperPrice;


    private BigDecimal copperTaxRate;


    private String aluminiumPrice;


    private BigDecimal aluminiumTaxRate;


    private String status;

    private String copperPriceTwo;

    private String aluminiumPriceTwo;


    private String priceModel;


    @TableField(exist = false)
    private List<SaleItem> itemList;


    @TableField(exist = false)
    private String saleNumber;

    @TableField(exist = false)
    private String copperLabelName;



    @TableField(exist = false)
    private String aluminiumName;



}
