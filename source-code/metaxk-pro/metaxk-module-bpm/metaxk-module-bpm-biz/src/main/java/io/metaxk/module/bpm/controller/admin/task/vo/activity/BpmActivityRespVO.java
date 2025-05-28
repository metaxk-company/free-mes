package io.metaxk.module.bpm.controller.admin.task.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 流程活动的 Response VO")
@Data
public class BpmActivityRespVO {

    @Schema(description = "流程活动的标识", required = true, example = "1024")
    private String key;
    @Schema(description = "流程活动的类型", required = true, example = "StartEvent")
    private String type;

    @Schema(description = "流程活动的开始时间", required = true)
    private LocalDateTime startTime;
    @Schema(description = "流程活动的结束时间", required = true)
    private LocalDateTime endTime;

    @Schema(description = "关联的流程任务的编号-关联的流程任务，只有 UserTask 等类型才有", example = "2048")
    private String taskId;

}
