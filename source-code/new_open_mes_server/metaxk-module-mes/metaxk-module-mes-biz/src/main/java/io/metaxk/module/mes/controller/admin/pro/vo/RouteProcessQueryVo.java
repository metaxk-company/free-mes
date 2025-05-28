package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工艺组成分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RouteProcessQueryVo extends PageParam {

    @Schema(description = "工艺路线ID", example = "4966")
    private Long routeId;

    @Schema(description = "工序ID", example = "9898")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "李四")
    private String processName;

    @Schema(description = "序号")
    private Integer orderNum;

    @Schema(description = "工序ID", example = "16534")
    private Long nextProcessId;

    @Schema(description = "工序编码")
    private String nextProcessCode;

    @Schema(description = "工序名称", example = "李四")
    private String nextProcessName;

    @Schema(description = "与下一道工序关系", example = "1")
    private String linkType;

    @Schema(description = "准备时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] defaultPreTime;

    @Schema(description = "等待时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] defaultSufTime;

    @Schema(description = "甘特图显示颜色")
    private String colorCode;

    @Schema(description = "关键工序")
    private String keyFlag;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
