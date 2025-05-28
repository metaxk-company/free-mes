package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

@Data
public class ProcessRecordVo {

    private Long id;

    private String  recordCode;

    private String  processCode;

    private String  processName;

    //物料编码
    private String  productCode;

    private String  productName;

    //物料条码
    private String productBarcode;

    private String inspectTime;

    private String inspectStartTime;

    private String inspectEndTime;

    private String  checkNumber;

    //质检员
    private String inspectUser;

    //已经抽检的数量
    private String num;

    //质检-无检验标准,状态
    private String status;

}
