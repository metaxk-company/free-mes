package io.metaxk.module.mes.controller.admin.pro.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProTaskVo extends PageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Long id;

    /**
     * 任务编号
     */
    @ExcelProperty("任务编号")
    private String taskCode;

    /**
     * 任务名称
     */
    @ExcelProperty("任务名称")
    private String taskName;

    /**
     * 生产工单ID
     */
    private Long workorderId;

    /**
     * 生产工单编号
     */
    @ExcelProperty("工单编号")
    private String workorderCode;

    /**
     * 工单名称
     */
    @ExcelProperty("工单名称")
    private String workorderName;

    /**
     * 工作站ID
     */
    private Long workstationId;

    /**
     * 工作站编号
     */
    @ExcelProperty("工作站编号")
    private String workstationCode;

    /**
     * 工作站名称
     */
    @ExcelProperty("工作站名称")
    private String workstationName;

    /**
     * 工序ID
     */
    private Long processId;

    /**
     * 工序编码
     */
    @ExcelProperty("工序编号")
    private String processCode;

    /**
     * 工序名称
     */

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("设备编号")
    private String machineryCode;

    @ExcelProperty("设备名称")
    private String machineryName;

    /**
     * 产品物料ID
     */
    private Long itemId;

    /**
     * 产品物料编码
     */
    @ExcelProperty("物料/产品编号")
    private String itemCode;

    /**
     * 产品物料名称
     */
    @ExcelProperty("物料/产品名称")
    private String itemName;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 单位
     */
    @ExcelProperty("单位")
    private String unitOfMeasure;

    /**
     * 排产数量
     */
    private BigDecimal quantity;

    /**
     * 已生产数量
     */
    private BigDecimal quantityProduced;

    private BigDecimal quantityQuanlify;

    private BigDecimal quantityUnquanlify;

    /**
     * 调整数量
     */
    private BigDecimal quantityChanged;

    /**
     * 客户ID
     */
    private Long clientId;

    /**
     * 客户编码
     */
    @ExcelProperty("客户编号")
    private String clientCode;

    /**
     * 客户名称
     */
    @ExcelProperty("客户名称")
    private String clientName;

    /**
     * 客户简称
     */
    private String clientNick;

    /**
     * 开始生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("开始时间")
    private String startTime;

    /**
     * 生产时长
     */
    private Long duration;

    /**
     * 完成生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("结束时间")
    private Date endTime;

    /**
     * 甘特图显示颜色
     */
    @ExcelProperty("颜色编码")
    private String colorCode;

    /**
     * 需求日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ExcelProperty("需求日期")
    private Date requestDate;

    @ExcelProperty("状态")
    private String status;

    /**
     * 生产任务二维码URL
     */
    private String taskUrl;

    /**
     * 二维码类型
     */
    private  String   barcodeFormat;

    /**
     * 生产工序二维码URL
     */
    private String   processUrl;


    private String tenantId;

}
