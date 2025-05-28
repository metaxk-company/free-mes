package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产工单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderQueryVo extends PageParam {

    @Schema(description = "工单编码")
    private String workorderCode;

    @Schema(description = "工单名称", example = "李四")
    private String workorderName;

    @Schema(description = "来源类型")
    private String orderSource;

    @Schema(description = "来源单据")
    private String sourceCode;

    @Schema(description = "产品ID", example = "10053")
    private Long productId;

    @Schema(description = "产品编号")
    private String productCode;

    @Schema(description = "产品名称", example = "赵六")
    private String productName;

    @Schema(description = "规格型号")
    private String productSpc;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "生产数量")
    private Object quantity;

    @Schema(description = "已生产数量")
    private Object quantityProduced;

    @Schema(description = "调整数量")
    private Object quantityChanged;

    @Schema(description = "已排产数量")
    private Object quantityScheduled;

    @Schema(description = "客户ID", example = "9055")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "王五")
    private String clientName;

    @Schema(description = "批次号")
    private String batchCode;

    @Schema(description = "需求日期")
    private String requestDate;


    @Schema(description = "生产订单日期")
    private String orderDate;


    @Schema(description = "生产日期")
    private String  produceDate;


    @Schema(description = "父工单", example = "8229")
    private Long parentId;

    @Schema(description = "所有父节点ID")
    private String ancestors;

    @Schema(description = "单据状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
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
    private LocalDateTime createTime;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

}
