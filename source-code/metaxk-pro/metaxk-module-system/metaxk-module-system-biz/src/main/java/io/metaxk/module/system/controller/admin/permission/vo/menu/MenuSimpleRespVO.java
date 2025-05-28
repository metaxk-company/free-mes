package io.metaxk.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 菜单精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuSimpleRespVO {

    @Schema(description = "菜单编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "菜单名称", required = true, example = "万界星空科技")
    private String name;

    @Schema(description = "父菜单 ID", required = true, example = "1024")
    private Long parentId;

    @Schema(description = "类型,参见 MenuTypeEnum 枚举类", required = true, example = "1")
    private Integer type;

}
