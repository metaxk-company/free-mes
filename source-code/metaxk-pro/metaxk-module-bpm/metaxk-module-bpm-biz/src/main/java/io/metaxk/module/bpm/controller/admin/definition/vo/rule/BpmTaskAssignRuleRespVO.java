package io.metaxk.module.bpm.controller.admin.definition.vo.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 流程任务分配规则的 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskAssignRuleRespVO extends BpmTaskAssignRuleBaseVO {

    @Schema(description = "任务分配规则的编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "流程模型的编号", required = true, example = "2048")
    private String modelId;

    @Schema(description = "流程定义的编号", required = true, example = "4096")
    private String processDefinitionId;

    @Schema(description = "流程任务定义的编号", required = true, example = "2048")
    private String taskDefinitionKey;
    @Schema(description = "流程任务定义的名字", required = true, example = "关注万界星空科技")
    private String taskDefinitionName;

}
