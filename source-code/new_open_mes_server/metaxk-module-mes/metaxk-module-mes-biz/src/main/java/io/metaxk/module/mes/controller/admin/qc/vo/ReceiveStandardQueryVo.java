package io.metaxk.module.mes.controller.admin.qc.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/26 10:50
 */
@Data
public class ReceiveStandardQueryVo extends PageParam {

    private String number;

    private String method;

    private String itemCode;
}
