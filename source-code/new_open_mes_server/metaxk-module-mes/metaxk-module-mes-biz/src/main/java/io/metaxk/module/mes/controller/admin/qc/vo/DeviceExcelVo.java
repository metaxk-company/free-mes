package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 万界星空
 * @time 2023/7/7 14:51
 */

@Data
@Accessors(chain = false)
public class DeviceExcelVo {


    @ExcelProperty("器具编号")
    private String  deviceCode;

    @ExcelProperty("器具名称")
    private String  deviceName;

    @ExcelProperty("协议")
    private String  agreement;

    @ExcelProperty("所属车间")
    private String  workshop;

    @ExcelProperty("部门")
    private String  department;

    @ExcelProperty("所属工序")
    private String  process;

    @ExcelProperty("描述")
    private String  remark;


}
