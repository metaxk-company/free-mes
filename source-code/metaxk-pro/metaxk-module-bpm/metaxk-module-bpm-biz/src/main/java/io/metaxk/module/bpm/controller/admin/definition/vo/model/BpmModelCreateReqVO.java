package io.metaxk.module.bpm.controller.admin.definition.vo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 流程模型的创建 Request VO")
@Data
public class BpmModelCreateReqVO {

    @Schema(description = "流程标识", required = true, example = "process_metaxk")
    @NotEmpty(message = "流程标识不能为空")
    private String key;

    @Schema(description = "流程名称", required = true, example = "万界星空科技")
    @NotEmpty(message = "流程名称不能为空")
    private String name;

    @Schema(description = "流程描述", example = "我是描述")
    private String description;

}
