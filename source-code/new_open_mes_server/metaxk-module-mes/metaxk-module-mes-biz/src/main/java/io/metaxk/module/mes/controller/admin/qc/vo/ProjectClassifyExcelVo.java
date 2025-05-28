package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 万界星空
 * @time 2023/7/7 16:31
 */
@Data
@Accessors(chain = false)
public class ProjectClassifyExcelVo {

    @ExcelProperty("项目编号")
    private String  projectCode;

    @ExcelProperty("项目名称")
    private String  projectName;

    @ExcelProperty("分类")
    private String  classify;

}
