package io.metaxk.module.mes.controller.admin.qc.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/10 13:34
 */
@Data
public class OtherRecordQueryVo extends PageParam {

    private String number;

    private String workOrderCode;

    private String model;

    private String spec;

    private String lineType;
}
