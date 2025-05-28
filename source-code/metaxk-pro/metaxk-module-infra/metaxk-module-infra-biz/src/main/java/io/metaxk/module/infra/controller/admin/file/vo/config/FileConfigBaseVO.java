package io.metaxk.module.infra.controller.admin.file.vo.config;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 文件配置 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FileConfigBaseVO {

    @Schema(description = "配置名", required = true, example = "S3 - 阿里云")
    @NotNull(message = "配置名不能为空")
    private String name;

    @Schema(description = "备注", example = "我是备注")
    private String remark;

}
