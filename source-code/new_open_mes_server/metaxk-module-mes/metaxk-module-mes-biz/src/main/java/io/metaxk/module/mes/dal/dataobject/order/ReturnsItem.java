package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;

/**
 * @author 万界星空
 * @time 2023/7/18 16:42
 */
@Data
@TableName("order_return_item")
public class ReturnsItem extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 退货单编号
     */
    private String returnNumber;

    /**
     * 线别
     */
    private String lineType;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;

    /**
     * 总净重
     */
    private BigDecimal weight;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 颜色
     */
    private String color;

    /**
     *箱号
     */
    private String boxNumber;

    /**
     * 轴数
     */
    private String axlesNumber;

    /**
     * 批次
     */
    private String batch;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 备注
     */
    private String remark;
}
