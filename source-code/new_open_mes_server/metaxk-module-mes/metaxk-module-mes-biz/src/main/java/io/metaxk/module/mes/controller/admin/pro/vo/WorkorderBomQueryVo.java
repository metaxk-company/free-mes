package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产工单BOM组成分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkorderBomQueryVo extends PageParam {

    @Schema(description = "生产工单ID", example = "12846")
    private Long workorderId;

    @Schema(description = "BOM物料ID", example = "77")
    private Long itemId;

    @Schema(description = "BOM物料编号")
    private String itemCode;

    @Schema(description = "BOM物料名称", example = "王五")
    private String itemName;

    @Schema(description = "规格型号")
    private String itemSpc;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "物料产品标识")
    private String itemOrProduct;

    @Schema(description = "预计使用量")
    private Object quantity;

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
    private LocalDateTime[] createTime;

}
