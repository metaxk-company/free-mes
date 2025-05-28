package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品BOM关系分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductBomQueryVo extends PageParam {

    @Schema(description = "物料产品ID", example = "13890")
    private Long itemId;

    @Schema(description = "BOM物料ID", example = "12763")
    private Long bomItemId;

    @Schema(description = "BOM物料编码")
    private String bomItemCode;

    @Schema(description = "BOM物料名称", example = "李四")
    private String bomItemName;

    @Schema(description = "BOM物料规格")
    private String bomItemSpec;

    @Schema(description = "BOM物料单位")
    private String unitOfMeasure;

    @Schema(description = "产品物料标识")
    private String itemOrProduct;

    @Schema(description = "物料使用比例")
    private Object quantity;

    @Schema(description = "是否启用")
    private String enableFlag;

    @Schema(description = "备注", example = "随便")
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

}
