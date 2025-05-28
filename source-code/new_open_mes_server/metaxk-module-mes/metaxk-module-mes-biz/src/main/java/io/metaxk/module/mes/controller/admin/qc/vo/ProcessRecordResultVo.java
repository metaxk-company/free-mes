package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessRecordResultVo {

    private String  recordId;

    private String  inspectWay;

    private String  inspectUser;

    private Date inspectStartTime;

    private Date inspectEndTime;

    private String  processCode;

    private String  processName;

    //序列号
    private String sortNumber;

    //不合格数
    private String unqualifiedNum;

    //合格数
    private String qualifiedNum;

    //实际检验数
    private String inspectNum;

    //检验数
    private String quantity;

    //合格率
    private String passRate;

    //不合格率
    private String failureRate;
}
