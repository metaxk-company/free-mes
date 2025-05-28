package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 单位分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnitMeasureQueryVo extends PageParam {

    @Schema(description = "单位编码")
    private String id;

    @Schema(description = "单位名称", example = "星空斗士")
    private String measureName;





}
