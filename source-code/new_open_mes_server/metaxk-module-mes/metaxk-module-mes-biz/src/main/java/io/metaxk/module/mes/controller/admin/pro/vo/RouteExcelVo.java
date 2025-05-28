package io.metaxk.module.mes.controller.admin.pro.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工艺路线 Excel VO
 *
 * @author 万界星空MES
 */
@Data
public class RouteExcelVo {

    @ExcelProperty("工艺路线ID")
    private Long id;

    @ExcelProperty("工艺路线编号")
    private String routeCode;

    @ExcelProperty("工艺路线名称")
    private String routeName;

    @ExcelProperty("工艺路线说明")
    private String routeDesc;

    @ExcelProperty("是否启用")
    private String enableFlag;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
