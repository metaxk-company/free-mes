package io.metaxk.module.mes.dal.dataobject.order;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;


/**
 *
 * PC扫描出库明细详情实体类
 * @author 万界星空
 * @time 2023/7/31 16:56
 */
@Data
@TableName("order_outbound_item_label")
public class OutboundItemLabel/* extends EntityCommon*/ {


    /**
     * id
     */
    private Long  id;

    /**
     * 出库id
     */
    private Long  outBoundLabelId;

    /**
     * 选择销售订单明细编号
     */
    private String saleItemNumber;

    /**
     * 出库单号
     */
    private String outboundNumber;

    /**
     * 出库明细编号
     */
    private String outboundItemNumber;
    /**
     * 型号
     */
    private String  model;

    /**
     * 轴数
     */
    private String  axlesNum;

    /**
     * 规格
     */
    private String  spec;

    /**
     * 批号
     */
    private String  batchNumber;

    /**
     * 箱号
     */
    private String  boxNumber;

    /**
     * 1轴净重
     */
    private String  oneAxleHeight;

    /**
     * 2轴净重
     */
    private String  twoAxleHeight;

    /**
     * 3轴净重
     */
    private String  threeAxleHeight;

    /**
     * 4轴净重
     */
    private String  fourAxleHeight;

    /**
     * 5轴净重
     */
    private String  fiveAxleHeight;

    /**
     * 6轴净重
     */
    private String  sixAxleHeight;

    /**
     * 7轴净重
     */
    private String  sevenAxleHeight;

    /**
     * 8轴净重
     */
    private String  eightAxleHeight;

    /**
     * 总净重
     */
    private BigDecimal  totalHeight;

    /**
     * 总件数
     */
    private Integer  pieces;

    /**
     * 标准
     */
    private String  stand;

    /**
     * 客户代码
     */
    private String  clientCode;

    /**
     * 线别
     */
    private String  lineType;

    /**
     * 盘号
     */
    private String  reelNumber;

    /**
     * 颜色
     */
    private String  color;

    /**
     * 是否重包
     */
    private String  hePackage;

    /**
     * 公司名称
     */
    private String  corporateName;

    /**
     * 条码
     */
    private String  barCode;

    /**
     * 设备编码
     */
    private String  equNumber;

    /**
     * 托盘编号
     */
    private String  palletNumber;

    /**
     * 托盘数量
     */
    private String  palletQuantity;

    /**
     * 产品编号
     */
    private String  itemCode;

    /**
     * 产品类型
     */
    private String  itemType;

    /**
     * 产品名称
     */
    private String  itemName;

    /**
     * 单位
     */
    private String  unitOfMeasure;

    /**
     * 是否退货
     */
    private String  returnGood;

    /**
     * 状态
     */
    private String  status;

    /**
     * 退货日期
     */
    private String  returnDate;

    /**
     * 日期
     */
    private String date;

    /**
     * 皮重
     */
    private BigDecimal tare;

    /**
     * 总皮重
     */
    private BigDecimal totalTare;


    private Long whLabelId;


    private String outReelNumber;

}
