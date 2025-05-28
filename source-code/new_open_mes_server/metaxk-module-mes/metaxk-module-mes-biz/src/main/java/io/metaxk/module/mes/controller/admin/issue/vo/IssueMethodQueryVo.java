package io.metaxk.module.mes.controller.admin.issue.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.issue.vo
 *
 * @author 万界星空
 * @time 2023/7/27 16:43
 */

@Data
public class IssueMethodQueryVo extends PageParam {

    private String name;
    private String description;

}
