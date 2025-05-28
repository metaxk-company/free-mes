package io.metaxk.module.mes.controller.admin.wh.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/27 14:25
 */
@Data
public class InboundRecBillVo {

    //采购订单id
    private Long id;

    //采购单编号
    private String receiptNumber;

    //仓库
    private String wareHouse;

    //到货通知单
    private String receiveBillCode;

    //到货时间
    private String receiveDate;

    private List<InboundRecBillItemVo> inboundRecBillItemVoList;
}
