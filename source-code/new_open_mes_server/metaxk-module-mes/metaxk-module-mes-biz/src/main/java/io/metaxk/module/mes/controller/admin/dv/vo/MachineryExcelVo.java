package io.metaxk.module.mes.controller.admin.dv.vo;


import lombok.*;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 设备 Excel VO
 * @author 万界星空MES
 */
@Data
public class MachineryExcelVo {


    @ExcelProperty("设备编码")
    private String machineryCode;

    @ExcelProperty("设备名称")
    private String machineryName;

    @ExcelProperty("品牌")
    private String machineryBrand;

    @ExcelProperty("规格型号")
    private String machinerySpec;

    @ExcelProperty("设备类型编码")
    private String machineryTypeCode;

    @ExcelProperty("设备类型名称")
    private String machineryTypeName;

    @ExcelProperty("所属车间编码")
    private String workshopCode;

    @ExcelProperty("所属车间名称")
    private String workshopName;

    @ExcelProperty("设备状态")
    private String status;

    @ExcelProperty("位置")
    private String location;

    @ExcelProperty("备注")
    private String remark;


}
