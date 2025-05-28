package io.metaxk.module.mes.controller.admin.cla.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 班组 excelVO
 * @author 万界星空
 * @time 2023/6/26 10:00
 */
@Data
public class ClaTeamExcelVo {

    @ExcelProperty("班组编号")
    private String teamCode;

    @ExcelProperty("班组类型")
    private String teamType;

    @ExcelProperty("班组名称")
    private String teamName;

    @ExcelProperty("班组组长")
    private String teamLeader;

    @ExcelProperty("备注")
    private String remark;
}
