package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工作站分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkstationQueryVo extends PageParam {

    @Schema(description = "工作站编码")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "赵六")
    private String workstationName;

    @Schema(description = "工作站地点")
    private String workstationAddress;

    @Schema(description = "所在车间ID", example = "22568")
    private Long workshopId;

    @Schema(description = "所在车间编码")
    private String workshopCode;

    @Schema(description = "所在车间名称", example = "赵六")
    private String workshopName;

    @Schema(description = "工序ID", example = "8460")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "王五")
    private String processName;

    @Schema(description = "线边库ID", example = "28214")
    private Long warehouseId;

    @Schema(description = "线边库编码")
    private String warehouseCode;

    @Schema(description = "线边库名称", example = "星空斗士")
    private String warehouseName;

    @Schema(description = "库区ID", example = "21950")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "张三")
    private String locationName;

    @Schema(description = "库位ID", example = "3")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "赵六")
    private String areaName;

    @Schema(description = "是否启用")
    private String enableFlag;

    @Schema(description = "备注", example = "你说的对")
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
