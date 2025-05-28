package io.metaxk.module.mes.controller.admin.cla.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


/**
 * 班组类型excel VO
 * @author 万界星空
 * @time 2023/6/26 9:27
 */
@Data
public class ClaTeamTypeExcelVo {

    @ExcelProperty("班组类型编号")
    private String typeCode;

    @ExcelProperty("班组类型名称")
    private String typeName;

    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("备注")
    private String remark;




}
