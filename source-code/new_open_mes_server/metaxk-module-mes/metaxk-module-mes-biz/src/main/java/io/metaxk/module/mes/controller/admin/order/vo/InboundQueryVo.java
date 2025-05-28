package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

/**
 * @author 万界星空MES
 */
@Data
public class InboundQueryVo extends PageParam {

    /**
     * 入库编号
     */
    private String inNumber;
    /**
     * 采购单号
     */
    private String receiptNumber;

    /**
     * 仓库
     */
    private String wareHouse;
    /**
     * 状态
     */
    private String status;


    private String source;

    private String startTime;
    private String endTime;

    private List deliveryDate;

}
