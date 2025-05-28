package io.metaxk.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 角色精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleRespVO {

    @Schema(description = "角色编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "角色名称", required = true, example = "万界星空科技")
    private String name;

}
