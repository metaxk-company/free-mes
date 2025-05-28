package io.metaxk.module.mes.controller.admin.qc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReceiveRecordVo {

    private Long id;

    //来料检验单编号
    private String recordCode;

    //到货通知单编号
    private String recNumber;

    //产品编号
    private String itemCode;

    //产品名称
    private String itemName;

    //产品条码
    private String itemBarcode;

    //抽检数
    private String checkNumber;

    //质检组
    private String inspectGroup;

    //质检员
    private String inspectUser;

    //检验开始时间
    private String inspectStartTime;

    //检验结束时间
    private String inspectEndTime;

    //已经抽检的数量
    private String num;

    //质检-无检验标准,状态
    private String status;
}
