package io.metaxk.module.system.controller.admin.dept.vo.dept;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 部门 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DeptBaseVO {

    @Schema(description = "菜单编号", required = true, example = "万界星空科技")
    @NotBlank(message = "部门编号不能为空")
    @Size(max = 30, message = "部门编号长度不能超过30个字符")
    private String number;

    @Schema(description = "菜单名称", required = true, example = "万界星空科技")
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    private String name;

    @Schema(description = "父菜单 ID", example = "1024")
    private Long parentId;

    @Schema(description = "显示顺序不能为空", required = true, example = "1024")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "负责人的用户编号", example = "2048")
    private Long leaderUserId;

    @Schema(description = "联系电话", example = "15601691000")
    @Size(max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    @Schema(description = "邮箱", example = "metaxk@metaxk.io")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @Schema(description = "状态,见 CommonStatusEnum 枚举", required = true, example = "1")
    @NotNull(message = "状态不能为空")
//    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;

}
