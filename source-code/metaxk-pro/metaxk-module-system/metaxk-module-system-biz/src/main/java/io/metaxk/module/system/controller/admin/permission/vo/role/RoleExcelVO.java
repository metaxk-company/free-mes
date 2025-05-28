package io.metaxk.module.system.controller.admin.permission.vo.role;

import io.metaxk.framework.excel.core.annotations.DictFormat;
import io.metaxk.framework.excel.core.convert.DictConvert;
import io.metaxk.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 角色 Excel 导出响应 VO
 */
@Data
public class RoleExcelVO {

    @ExcelProperty("角色序号")
    private Long id;

    @ExcelProperty("角色名称")
    private String name;

    @ExcelProperty("角色标志")
    private String code;

    @ExcelProperty("角色排序")
    private Integer sort;

    @ExcelProperty("数据范围")
    private Integer dataScope;

    @ExcelProperty(value = "角色状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private String status;

}
