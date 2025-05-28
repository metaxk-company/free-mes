package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 销售订单-物料价格实体类
 * @author 万界星空
 * @time 2023/7/17 15:26
 */
@Data
@TableName("order_sale_item_price")
public class SaleItemPrice extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 日期
     */
    private String time;

    /**
     * 类别
     */
    private String category;

    /**
     *  价格(吨)
     */
    private BigDecimal priceTon;


    /**
     * 价格(千克)
     */
    private BigDecimal priceKg;


    @TableField(exist = false)
    private String label;


    @TableField(exist = false)
    private BigDecimal value;

}
