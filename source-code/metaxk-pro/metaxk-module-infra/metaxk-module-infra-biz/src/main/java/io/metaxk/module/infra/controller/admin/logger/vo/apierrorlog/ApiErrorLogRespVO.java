package io.metaxk.module.infra.controller.admin.logger.vo.apierrorlog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - API 错误日志 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApiErrorLogRespVO extends ApiErrorLogBaseVO {

    @Schema(description = "编号", required = true, example = "1024")
    private Integer id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "处理时间", required = true)
    private LocalDateTime processTime;

    @Schema(description = "处理用户编号", example = "233")
    private Integer processUserId;

}
