package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产工序分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessQueryVo extends PageParam {

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "赵六")
    private String processName;

    @Schema(description = "工艺要求")
    private String attention;

    @Schema(description = "是否启用")
    private String enableFlag;

    @Schema(description = "备注", example = "随便")
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

    @Schema(description = "工时(分钟)")
    private Long manHour;

    @Schema(description = "工序二维码", example = "https://www.metaxk.vip")
    private String processUrl;

    @Schema(description = "二维码规则")
    private String barcodeFormat;

}
