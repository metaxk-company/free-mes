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
public class PanHaoExportVo {
    /**
     * 盘号编号
     */
    @ExcelProperty("盘号")
    private String number;
}
