package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/17 13:16
 */

@Data
public class InventoryStatisticsQueryVO extends PageParam {
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
     * 起始时间
     */
    private String  startTime;
    /**
     * 结束时间
     */
    private String  endTime;
}
