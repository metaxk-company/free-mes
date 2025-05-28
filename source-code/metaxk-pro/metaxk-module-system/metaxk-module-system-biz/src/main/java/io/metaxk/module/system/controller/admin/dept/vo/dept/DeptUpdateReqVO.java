package io.metaxk.module.system.controller.admin.dept.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 部门更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptUpdateReqVO extends DeptBaseVO {

    @Schema(description = "部门ID", required = true, example = "1024")
    @NotNull(message = "部门ID不能为空")
    private Long id;

}
