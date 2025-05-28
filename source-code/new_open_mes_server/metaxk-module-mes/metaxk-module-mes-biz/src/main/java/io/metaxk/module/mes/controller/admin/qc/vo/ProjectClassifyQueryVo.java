package io.metaxk.module.mes.controller.admin.qc.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/6 11:48
 */
@Data
public class ProjectClassifyQueryVo extends PageParam {

    private String  projectCode;

    private String  projectName;


}
