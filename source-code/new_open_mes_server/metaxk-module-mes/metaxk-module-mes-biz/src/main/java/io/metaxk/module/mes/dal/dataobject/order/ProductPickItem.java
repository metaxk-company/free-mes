package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 生产领料明细表
 * @author 万界星空
 * @time 2023/7/21 14:13
 */
@Data
@TableName("order_product_pick_item")
public class ProductPickItem extends EntityCommon {


    /**
     * id
     */
    private Long id;

    /**
     * 领料单编号
     */
    private String pickNumber;


    /**
     * 编号
     */
    private String itemCode;

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
     * 含税总价
     */
    private BigDecimal includTax;

    /**
     * 不含税总价
     */
    private BigDecimal noIncludTax;



}
