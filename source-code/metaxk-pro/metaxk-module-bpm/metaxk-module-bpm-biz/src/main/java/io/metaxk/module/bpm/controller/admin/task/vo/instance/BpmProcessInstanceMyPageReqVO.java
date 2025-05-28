package io.metaxk.module.bpm.controller.admin.task.vo.instance;

import io.metaxk.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 流程实例的分页 Item Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessInstanceMyPageReqVO extends PageParam {

    @Schema(description = "流程名称", example = "万界星空科技")
    private String name;

    @Schema(description = "流程定义的编号", example = "2048")
    private String processDefinitionId;

    @Schema(description = "流程实例的状态-参见 bpm_process_instance_status", example = "1")
    private Integer status;

    @Schema(description = "流程实例的结果-参见 bpm_process_instance_result", example = "2")
    private Integer result;

    @Schema(description = "流程分类-参见 bpm_model_category 数据字典", example = "1")
    private String category;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
