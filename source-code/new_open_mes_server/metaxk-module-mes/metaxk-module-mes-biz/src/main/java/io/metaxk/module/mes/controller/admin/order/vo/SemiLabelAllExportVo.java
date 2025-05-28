package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/25 13:55
 */

@Data
public class SemiLabelAllExportVo {

    /**
     * 半成品编号
     */
    @ExcelProperty("半成品编号")
    private String  number;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date createTime;

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String  model;

    /**
     * 规格
     */
    @ExcelProperty("规格")
    private String  spec;

    /**
     * 颜色
     */
    @ExcelProperty("颜色")
    private String  color;

    /**
     * 线别
     */
    @ExcelProperty("线别")
    private String  lineType;

    /**
     * 盘号
     */
    @ExcelProperty("盘号")
    private String  reelNumber;

    /**
     * 数量
     */
    @ExcelProperty("数量")
    private String quantity;

    /**
     * 单位
     */
    @ExcelProperty("单位")
    private String  unitOfMeasure;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;

}
