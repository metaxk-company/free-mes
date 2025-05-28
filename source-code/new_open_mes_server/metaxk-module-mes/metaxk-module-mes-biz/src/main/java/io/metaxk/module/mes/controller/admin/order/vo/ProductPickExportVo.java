package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * io.metaxk.module.mes.controller.admin.order.vo
 *
 * @author xx
 * @time 2023/8/23 16:54
 */

@Data
public class ProductPickExportVo {

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

}
