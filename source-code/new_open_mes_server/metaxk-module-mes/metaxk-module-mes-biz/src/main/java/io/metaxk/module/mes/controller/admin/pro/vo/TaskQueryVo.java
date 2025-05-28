package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产任务分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskQueryVo extends PageParam {

    @Schema(description = "任务编号")
    private String taskCode;

    @Schema(description = "任务名称", example = "赵六")
    private String taskName;

    @Schema(description = "生产工单ID", example = "30095")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "工单名称", example = "李四")
    private String workorderName;

    @Schema(description = "工作站ID", example = "17352")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "张三")
    private String workstationName;

    @Schema(description = "工序ID", example = "24763")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "王五")
    private String processName;

    @Schema(description = "产品物料ID", example = "24153")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "赵六")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "排产数量")
    private Object quantity;

    @Schema(description = "已生产数量")
    private Object quantityProduced;

    @Schema(description = "合格品数量")
    private Object quantityQuanlify;

    @Schema(description = "不良品数量")
    private Object quantityUnquanlify;

    @Schema(description = "调整数量")
    private Object quantityChanged;

    @Schema(description = "客户ID", example = "16104")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "李四")
    private String clientName;

    @Schema(description = "客户简称")
    private String clientNick;

    @Schema(description = "开始生产时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "生产时长")
    private Integer duration;

    @Schema(description = "完成生产时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "甘特图显示颜色")
    private String colorCode;

    @Schema(description = "需求日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] requestDate;

    @Schema(description = "生产状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "任务二维码", example = "https://www.metaxk.io")
    private String taskUrl;

    @Schema(description = "二维码格式")
    private String barcodeFormat;

    @Schema(description = "工序二维码", example = "https://www.metaxk.io")
    private String processUrl;

}
