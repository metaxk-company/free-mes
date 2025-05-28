package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 扫码出库记录实体类
 * @author 万界星空
 * @time 2023/7/19 14:33
 */
@Data
@TableName("order_outbound_record")
public class OutboundRecord extends EntityCommon {

    /**
     *  id
     */
    private  Long id;

    /**
     * 出库单号
     */
    private String outboundNumber;

    /**
     * 型号规格
     */
    private String modelAndSpec;

    /**
     * 销售数量
     */
    private String saleQuantity;

    /**
     * 托盘号
     */
    private String panhao;

    /**
     * 托盘数量
     */
    private String panhaoQuantity;

    /**
     * 二维码
     */
    private String barCode;


    @TableField(exist = false)
    private String  outBoundType;


}
