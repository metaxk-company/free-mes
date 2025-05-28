package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/25 13:36
 */

@Data
public class ProductPickAllExportVo {

    /**
     * 编号
     */
    @ExcelProperty("领料编号")
    private String number;

    /**
     * 领料日期
     */
    @ExcelProperty("领料日期")
    private String pickDate;

    /**
     * 产品类别
     */
    @ExcelProperty("类别")
    private String productType;

    /**
     * 创建日期
     */
    @ExcelProperty("创建日期")
    private Date createTime;

    /**
     * 编号
     */
    @ExcelProperty("编号")
    private String itemCode;

    /**
     * 名称
     */
    @ExcelProperty("名称")
    private String itemName;

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String model;

    /**
     * 规格
     */
    @ExcelProperty("规格")
    private String spec;

    /**
     * 类别
     */
    @ExcelProperty("类别")
    private String kind;

    /**
     * 单位
     */
    @ExcelProperty("单位")
    private String unitOfMeasure;

    /**
     * 	数量
     */
    @ExcelProperty("数量")
    private BigDecimal quantity;

}
