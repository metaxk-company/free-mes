package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceiveRecordResultVo {

    private String  recordId;

    private String  inspectWay;

    private String  inspectUser;

    private Date inspectStartTime;

    private Date inspectEndTime;

    //产品编号
    private String  itemCode;

    //到货通知单编号
    private String  recNumber;

    //序列号
    private String sortNumber;

    //不合格数
    private String unqualifiedNum;

    //合格数
    private String qualifiedNum;

    //实际检验数
    private String inspectNum;

    //数量(到货数)
    private BigDecimal quantity;

    //合格率
    private String passRate;

    //不合格率
    private String failureRate;
}
