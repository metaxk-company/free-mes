package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 万界星空
 * @time 2023/8/23 14:33
 */
@Data
public class ItemPriceExportVo {

    @ExcelProperty("类别")
    private String category;

    @ExcelProperty("日期")
    private String time;

    @ExcelProperty("价格(吨)")
    private BigDecimal priceTon;


    @ExcelProperty("价格(千克)")
    private BigDecimal priceKg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("创建时间")
    private Date createTime;


}
