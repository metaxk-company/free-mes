package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 物料产品分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemTypeQueryVo extends PageParam {

    @Schema(description = "产品物料类型编码")
    private String itemTypeCode;

    @Schema(description = "产品物料类型名称", example = "王五")
    private String itemTypeName;

    @Schema(description = "父类型ID", example = "28877")
    private Long parentTypeId;

    @Schema(description = "所有层级父节点")
    private String ancestors;

    @Schema(description = "产品物料标识")
    private String itemOrProduct;

    @Schema(description = "排列顺序")
    private Integer orderNum;

    @Schema(description = "是否启用")
    private String enableFlag;

    @Schema(description = "备注", example = "你猜")
    private String remark;



}
