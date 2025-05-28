package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


/**
 * 退货记录实体类
 *  @author 万界星空
 * @time 2023/7/18 16:37
 */
@Data
@TableName("order_return")
public class Returns extends EntityCommon {

    /**
     * id
     */
    private  Long id;

    /**
     * 退货单编号
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
     * 退货日期
     */
    private String returnDate;

    /**
     * 总净重
     */
    private BigDecimal  weight;

    /**
     * 备注
     */
    private String  remark;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 类型
     * 1：直接入库
     * 2：重包
     * 3：报废
     */
    private String type;

    /**
     * 仓库
     */
    private String warehouse;


    @TableField(exist = false)
    private List<ReturnsItem> returnsItemList;



}
