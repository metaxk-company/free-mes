package io.metaxk.module.system.controller.admin.oauth2.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "管理后台 - OAuth2 获得用户基本信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfoRespVO {

    @Schema(description = "用户编号", required = true, example = "1")
    private Long id;

    @Schema(description = "用户账号", required = true, example = "星空斗士")
    private String username;

    @Schema(description = "用户昵称", required = true, example = "万界星空科技")
    private String nickname;

    @Schema(description = "用户邮箱", example = "metaxk@metaxk.io")
    private String email;
    @Schema(description = "手机号码", example = "15601691300")
    private String mobile;

    @Schema(description = "用户性别,参见 SexEnum 枚举类", example = "1")
    private Integer sex;

    @Schema(description = "用户头像", example = "https://www.metaxk.io/xxx.png")
    private String avatar;

    /**
     * 所在部门
     */
    private Dept dept;

    /**
     * 所属岗位数组
     */
    private List<Post> posts;

    @Schema(description = "部门")
    @Data
    public static class Dept {

        @Schema(description = "部门编号", required = true, example = "1")
        private Long id;

        @Schema(description = "部门名称", required = true, example = "研发部")
        private String name;

    }

    @Schema(description = "岗位")
    @Data
    public static class Post {

        @Schema(description = "岗位编号", required = true, example = "1")
        private Long id;

        @Schema(description = "岗位名称", required = true, example = "开发")
        private String name;

    }

}
