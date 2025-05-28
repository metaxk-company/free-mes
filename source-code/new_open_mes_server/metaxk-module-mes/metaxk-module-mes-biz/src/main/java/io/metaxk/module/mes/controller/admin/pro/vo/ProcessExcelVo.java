package io.metaxk.module.mes.controller.admin.pro.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 生产工序 Excel VO
 *
 * @author 万界星空MES
 */
@Data
public class ProcessExcelVo {

    @ExcelProperty("工序ID")
    private Long id;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("工艺要求")
    private String attention;

    @ExcelProperty("是否启用")
    private String enableFlag;

    @ExcelProperty("备注")
    private String remark;


    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;



}
