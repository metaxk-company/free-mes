package io.metaxk.module.system.controller.admin.logger.vo.operatelog;

import io.metaxk.framework.excel.core.annotations.DictFormat;
import io.metaxk.framework.excel.core.convert.DictConvert;
import io.metaxk.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志 Excel 导出响应 VO
 */
@Data
public class OperateLogExcelVO {

    @ExcelProperty("日志编号")
    private Long id;

    @ExcelProperty("操作模块")
    private String module;

    @ExcelProperty("操作名")
    private String name;

    @ExcelProperty(value = "操作类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.OPERATE_TYPE)
    private String type;

    @ExcelProperty("操作人")
    private String userNickname;

    @ExcelProperty(value = "操作结果") // 成功 or 失败
    private String successStr;

    @ExcelProperty("操作日志")
    private LocalDateTime startTime;

    @ExcelProperty("执行时长")
    private Integer duration;

}
