package io.metaxk.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 用户精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleRespVO {

    @Schema(description = "用户编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "用户昵称", required = true, example = "万界星空科技")
    private String nickname;

}
