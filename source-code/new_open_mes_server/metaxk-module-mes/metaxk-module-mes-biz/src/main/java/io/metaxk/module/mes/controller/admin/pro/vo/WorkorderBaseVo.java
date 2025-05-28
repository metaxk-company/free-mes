package io.metaxk.module.mes.controller.admin.pro.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 生产工单 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class WorkorderBaseVo {

    @Schema(description = "工单编码", required = true)
    @NotNull(message = "工单编码不能为空")
    private String workorderCode;

    @Schema(description = "工单名称", required = true, example = "李四")
    @NotNull(message = "工单名称不能为空")
    private String workorderName;

    @Schema(description = "来源类型", required = true)
    @NotNull(message = "来源类型不能为空")
    private String orderSource;

    @Schema(description = "来源单据")
    private String sourceCode;

    @Schema(description = "产品ID", required = true, example = "10053")
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @Schema(description = "产品编号", required = true)
    @NotNull(message = "产品编号不能为空")
    private String productCode;

    @Schema(description = "产品名称", required = true, example = "赵六")
    @NotNull(message = "产品名称不能为空")
    private String productName;

    @Schema(description = "规格型号")
    private String productSpc;

    @Schema(description = "单位", required = true)
    @NotNull(message = "单位不能为空")
    private String unitOfMeasure;

    @Schema(description = "生产数量", required = true)
    @NotNull(message = "生产数量不能为空")
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

    @Schema(description = "需求日期", required = true)
    @NotNull(message = "需求日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime requestDate;

    @Schema(description = "父工单", required = true, example = "8229")
    @NotNull(message = "父工单不能为空")
    private Long parentId;

    @Schema(description = "所有父节点ID", required = true)
    @NotNull(message = "所有父节点ID不能为空")
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

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

}
