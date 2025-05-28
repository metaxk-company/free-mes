package io.metaxk.module.mes.controller.admin.md.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * io.metaxk.module.mes.controller.admin.md.vo
 *
 * @author xx
 * @time 2023/8/23 13:58
 */

@Data
public class SpecExportVo {
    /**
     * 型号
     */
    @ExcelProperty("规格型号")
    private String model;
    /**
     * 编号
     */
    @ExcelProperty("规格编号")
    private String serial;
    /**
     * 名称
     */
    @ExcelProperty("规格名称")
    private String name;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date createTime;
}
