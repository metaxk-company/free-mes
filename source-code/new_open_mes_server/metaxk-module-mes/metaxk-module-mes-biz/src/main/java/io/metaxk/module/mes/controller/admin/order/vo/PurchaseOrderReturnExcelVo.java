package io.metaxk.module.mes.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/8/23 16:27
 */
@Data
public class PurchaseOrderReturnExcelVo {

    @ExcelProperty("退货编号")
    private String number;

    @ExcelProperty("供应商名称")
    private String vendorName;


    @ExcelProperty("采购编号")
    private String poNumber;

    @ExcelProperty("退货日期")
    private String returnDate;

    @ExcelProperty("备注")
    private String remark;
}
