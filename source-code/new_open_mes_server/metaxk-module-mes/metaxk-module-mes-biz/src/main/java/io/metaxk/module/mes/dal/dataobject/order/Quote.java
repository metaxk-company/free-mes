package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.util.List;


/**
 * 客户报价单实体类
 * @author 万界星空
 * @time 2023/7/17 14:04
 */
@Data
@TableName("order_quote")
public class Quote extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 客户报价单编号
     */
    private String  number;

    /**
     * 客户编号
     */
    @TableField(exist = false)
    private String  customerNumber;

    /**
     * 客户名称
     */
    private String  customerName;

    /**
     * 型号
     */
    private String  model;

    /**
     * 单位
     */
    private String unit;

    /**
     * 线别
     */
    private String  lineType;

    /**
     * 备注
     */
    private String  remark;

    /**
     * 集合用于接收明细数组
     */
    @TableField(exist = false)
    List<QuoteItem> orderQuoteItemList;


}
