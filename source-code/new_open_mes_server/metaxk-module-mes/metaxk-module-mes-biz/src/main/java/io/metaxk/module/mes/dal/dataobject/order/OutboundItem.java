package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


/**
 * 销售出库--出库明细
 * @author 万界星空
 * @time 2023/7/17 17:23
 */
@Data
@TableName("order_outbound_item")
public class OutboundItem extends EntityCommon {


    private Long id;

    /**
     * 编号
     */
    private String  number;


    /**
     * 出库编号
     */
    private String  outboundNumber;


    /**
     * 销售单编号
     */
    private String  saleNumber;


    /**
     * 销售单明细编号
     */
    private String  saleItemNumber;


    /**
     * 产品编号
     */
    private String itemCode;

    /**
     * 型号
     */
    private String  model;

    /**
     * 规格
     */
    private String  spec;

    /**
     * 单价
     */
    private BigDecimal    price;

    /**
     * 线别
     */
    private String  lineType;

    /**
     * 客户代码
     */
    private String  customerCode;


    /**
     * 颜色
     */
    private String  color;


    /**
     * 盘号
     */
    private String  panhao;


    /**
     * 总价
     */
    private BigDecimal  totalPrice;

    /**
     * 单位
     */
    private String  unit;

    /**
     * 销售数量
     */
    private BigDecimal  quantity;

    /**
     * 已发
     */
    private BigDecimal  sendOut;

    /**
     * 未发
     */
    private BigDecimal  noSend;

    /**
     * 总净重
     */
    private BigDecimal totalWeight;

    /**
     * 皮重(g)
     */
    private BigDecimal  tare;

    /**
     * 总皮重
     */
    private BigDecimal  totalTare;

    /**
     * 件数
     */
    private String  pieces;

    /**
     * 备注
     */
    private String  remark;


    /**
     * 出库明细的状态
     */
    private String  status;

    /**
     * 客户名称
     */
    private String customerName;



    @TableField(exist = false)
    private List<OutboundItemLabel> labelList;


}
