package io.metaxk.module.mes.controller.admin.qc.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/10 14:40
 */
@Data
public class ReceiveRecordQueryVo extends PageParam {

    //来料检验单编号
    private String  recordCode;

    //到货通知单编号
    private String recNumber;

    //产品编号
    private String itemCode;

    //型号
    private String model;

    //规格
    private String spec;

    //质检员
    private String inspectUser;

}
