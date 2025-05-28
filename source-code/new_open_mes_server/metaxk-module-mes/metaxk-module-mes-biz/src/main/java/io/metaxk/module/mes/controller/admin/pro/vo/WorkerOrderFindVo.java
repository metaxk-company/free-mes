package io.metaxk.module.mes.controller.admin.pro.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class WorkerOrderFindVo extends PageParam {

    private String workOrderCode;
}
