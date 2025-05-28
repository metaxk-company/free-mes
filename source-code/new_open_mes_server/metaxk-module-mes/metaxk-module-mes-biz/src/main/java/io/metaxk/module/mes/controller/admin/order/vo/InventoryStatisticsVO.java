package io.metaxk.module.mes.controller.admin.order.vo;

import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/17 11:58
 */

@Data
public class InventoryStatisticsVO {
    /**
     * 线别
     */
    private String  lineType;
    /**
     * 型号
     */
    private String  model;
    /**
     * 规格
     */
    private String  spec;
    /**
     * 颜色
     */
    private String  color;
    /**
     * 盘号
     */
    private String  reelNumber;
    /**
     * 入库总数量
     */
    private double  totalHeight;
    /**
     * 入库总件数
     */
    private String  labelPieces;
    /**
     * 出库总数量
     */
    private String  totalWeight;
    /**
     * 出库总件数
     */
    private String  outboundPieces;
    /**
     * 日期
     */
    private String  date;
}
