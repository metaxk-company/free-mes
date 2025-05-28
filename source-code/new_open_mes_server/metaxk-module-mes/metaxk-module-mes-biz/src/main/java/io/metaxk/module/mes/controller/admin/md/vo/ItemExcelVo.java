package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.experimental.Accessors;

/**
 * 物料产品 Excel VO
 * @author 万界星空MES
 */
@Data
@Accessors(chain = false)
public class ItemExcelVo {

    @ExcelProperty("物料产品编码")
    private String itemCode;

    @ExcelProperty("物料产品名称")
    private String itemName;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("物料产品类型编码")
    private String itemTypeCode;

    @ExcelProperty("物料产品类型名称")
    private String itemTypeName;


    @ExcelProperty("物料或者产品")
    private String itemOrProduct;







}
