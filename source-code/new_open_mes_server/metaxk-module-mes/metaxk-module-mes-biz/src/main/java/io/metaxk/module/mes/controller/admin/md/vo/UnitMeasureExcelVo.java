package io.metaxk.module.mes.controller.admin.md.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 单位 Excel VO
 * @author 万界星空
 */
@Data
public class UnitMeasureExcelVo {


    @ExcelProperty("单位名称")
    private String measureName;

    @ExcelProperty("是否是主单位")
    private String primaryFlag;


    @ExcelProperty("与主单位换算比例")
    private BigDecimal changeRate;

    @ExcelProperty("是否启用")
    private String enableFlag;

    @ExcelProperty("备注")
    private String remark;


    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
