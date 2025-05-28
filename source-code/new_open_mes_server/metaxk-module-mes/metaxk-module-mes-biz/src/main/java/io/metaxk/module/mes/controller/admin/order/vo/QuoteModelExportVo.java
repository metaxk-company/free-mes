package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


/**
 * @author 万界星空
 * @time 2023/8/23 13:58
 */
@Data
public class QuoteModelExportVo {

    @ExcelProperty("型号名称")
    private String model;

    @ExcelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ExcelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
