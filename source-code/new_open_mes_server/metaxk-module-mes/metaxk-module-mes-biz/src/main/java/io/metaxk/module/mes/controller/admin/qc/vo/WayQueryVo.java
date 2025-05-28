package io.metaxk.module.mes.controller.admin.qc.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/6 11:04
 */
@Data
public class WayQueryVo extends PageParam {

    private String inspectCode;

    private String inspectName;

}
