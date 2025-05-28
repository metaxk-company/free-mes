package io.metaxk.module.mes.controller.admin.md.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.md.vo
 *
 * @author xx
 * @time 2023/8/23 13:58
 */

@Data
public class ColorExportVo {
    /**
     * 颜色
     */
    @ExcelProperty("颜色")
    private String name;
}
