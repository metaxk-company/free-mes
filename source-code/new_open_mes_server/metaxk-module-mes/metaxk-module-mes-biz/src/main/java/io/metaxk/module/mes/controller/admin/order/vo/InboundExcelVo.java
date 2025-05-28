package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/23 15:54
 */
@Data
public class InboundExcelVo {

    @ExcelProperty("入库编号")
    private String inNumber;

    @ExcelProperty("供应商")
    private String vendor;

    @ExcelProperty("仓库")
    private String wareHouse;

    @ExcelProperty("交货日期")
    private String deliveryDate;


    @ExcelProperty("来源")
    private String source;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
