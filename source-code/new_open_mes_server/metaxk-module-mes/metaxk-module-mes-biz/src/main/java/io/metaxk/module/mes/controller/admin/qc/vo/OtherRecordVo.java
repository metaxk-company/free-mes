package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

@Data
public class OtherRecordVo{

    private Long id;

    private String  recordCode;

    private String itemCode;

    private String itemName;

    private String model;

    private String spec;

    private String lineType;

    private String reelNumber;

    private String color;

    //物料条码
    private String productBarcode;

    private String inspectStartTime;

    private String inspectEndTime;

   // private String  checkNumber;

    //质检员
    private String inspectUser;

    //已经抽检的数量
    private String num;

    //质检-无检验标准,状态
    private String status;
}
