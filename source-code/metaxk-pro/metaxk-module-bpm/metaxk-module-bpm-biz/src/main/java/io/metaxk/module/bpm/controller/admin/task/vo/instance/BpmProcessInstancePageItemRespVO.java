package io.metaxk.module.bpm.controller.admin.task.vo.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 流程实例的分页 Item Response VO")
@Data
public class BpmProcessInstancePageItemRespVO {

    @Schema(description = "流程实例的编号", required = true, example = "1024")
    private String id;

    @Schema(description = "流程名称", required = true, example = "万界星空科技")
    private String name;

    @Schema(description = "流程定义的编号", required = true, example = "2048")
    private String processDefinitionId;

    @Schema(description = "流程分类-参见 bpm_model_category 数据字典", required = true, example = "1")
    private String category;

    @Schema(description = "流程实例的状态-参见 bpm_process_instance_status", required = true, example = "1")
    private Integer status;

    @Schema(description = "流程实例的结果-参见 bpm_process_instance_result", required = true, example = "2")
    private Integer result;

    @Schema(description = "提交时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "结束时间", required = true)
    private LocalDateTime endTime;

    /**
     * 当前任务
     */
    private List<Task> tasks;

    @Schema(description = "流程任务")
    @Data
    public static class Task {

        @Schema(description = "流程任务的编号", required = true, example = "1024")
        private String id;

        @Schema(description = "任务名称", required = true, example = "万界星空科技")
        private String name;

    }

}
