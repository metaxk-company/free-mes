package io.metaxk.module.mes.controller.admin.md.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工作站 Excel VO
 *
 * @author 万界星空MES
 */
@Data
public class WorkstationExcelVo {

    @ExcelProperty("工作站ID")
    private Long id;

    @ExcelProperty("工作站编码")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

    @ExcelProperty("工作站地点")
    private String workstationAddress;

    @ExcelProperty("所在车间ID")
    private Long workshopId;

    @ExcelProperty("所在车间编码")
    private String workshopCode;

    @ExcelProperty("所在车间名称")
    private String workshopName;

    @ExcelProperty("工序ID")
    private Long processId;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("是否启用")
    private String enableFlag;

    @ExcelProperty("备注")
    private String remark;


    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
