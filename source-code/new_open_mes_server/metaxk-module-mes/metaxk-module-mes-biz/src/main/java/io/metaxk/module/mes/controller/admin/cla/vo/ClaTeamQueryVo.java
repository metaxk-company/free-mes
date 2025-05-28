package io.metaxk.module.mes.controller.admin.cla.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/6/26 9:52
 */
@Data
public class ClaTeamQueryVo extends PageParam {

    private String teamType;

    private String teamCode;

    private String teamName;
}
