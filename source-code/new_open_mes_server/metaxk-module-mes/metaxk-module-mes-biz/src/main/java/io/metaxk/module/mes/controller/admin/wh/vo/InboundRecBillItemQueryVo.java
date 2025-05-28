package io.metaxk.module.mes.controller.admin.wh.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/27 14:25
 */
@Data
public class InboundRecBillItemQueryVo extends PageParam {


    /**
     * 到货通知单编号
     */
    private String recNumber;

    /**
     * 产品编号
     */
    private String itemCode;

    /**
     * 产品名称
     */
    private String itemName;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;
}
