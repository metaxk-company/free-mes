package io.metaxk.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 用户导入 Response VO")
@Data
@Builder
public class UserImportRespVO {

    @Schema(description = "创建成功的用户名数组", required = true)
    private List<String> createUsernames;

    @Schema(description = "更新成功的用户名数组", required = true)
    private List<String> updateUsernames;

    @Schema(description = "导入失败的用户集合,key 为用户名，value 为失败原因", required = true)
    private Map<String, String> failureUsernames;

}
