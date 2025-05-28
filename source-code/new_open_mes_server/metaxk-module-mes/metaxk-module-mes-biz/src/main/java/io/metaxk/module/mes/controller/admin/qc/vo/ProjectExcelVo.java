package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 万界星空
 * @time 2023/7/7 17:14
 */
@Data
@Accessors(chain = false)
public class ProjectExcelVo {

    @ExcelProperty("检测分类")
    private String classify;

    @ExcelProperty("检测编号")
    private String projectCode;

    @ExcelProperty("检测名称")
    private String projectName;

    @ExcelProperty("量化标准数值")
    private String standValue;

    @ExcelProperty("量化标准单位")
    private String  standUnit;

    @ExcelProperty("检测器具")
    private String inspectDevice;

    @ExcelProperty("描述")
    private String   remark;
}
