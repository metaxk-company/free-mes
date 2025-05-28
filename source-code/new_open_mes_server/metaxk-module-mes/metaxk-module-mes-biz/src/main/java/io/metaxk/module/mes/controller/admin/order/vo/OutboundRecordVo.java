package io.metaxk.module.mes.controller.admin.order.vo;

import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/21 9:16
 */
@Data
public class OutboundRecordVo {

    /**
     * 出库单号
     */
    private String outNumber;

    /**
     * 型号规格
     */
    private String modelAndSpec;

    /**
     * 销售数量
     */
    private String salesQuantity;

    /**
     *   托盘号
     */
    private String palletNumber;

    /**
     *  二维码
     */
    private String  qrCode;

}
