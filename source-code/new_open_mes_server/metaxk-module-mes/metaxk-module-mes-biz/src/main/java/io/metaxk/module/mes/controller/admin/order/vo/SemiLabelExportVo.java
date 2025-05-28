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
public class SemiLabelExportVo {

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

}
