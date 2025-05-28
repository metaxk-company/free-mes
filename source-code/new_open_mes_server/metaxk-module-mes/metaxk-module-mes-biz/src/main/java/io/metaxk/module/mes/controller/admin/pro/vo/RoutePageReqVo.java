package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 工艺路线分页请求")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoutePageReqVo extends PageParam {

    @Schema(description = "工艺路线编号")
    private String routeCode;

    @Schema(description = "工艺路线名称", example = "星空斗士")
    private String routeName;

    @Schema(description = "是否启用")
    private String enableFlag;



}
