package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;


@Data
public class ProcessRecordItemVo {

    private Long recordId;

    private String sortNumber;

    private String itemName;

    private String itemStandard;

    private String itemDevice;

    private String itemValue;

    private String status;
}
