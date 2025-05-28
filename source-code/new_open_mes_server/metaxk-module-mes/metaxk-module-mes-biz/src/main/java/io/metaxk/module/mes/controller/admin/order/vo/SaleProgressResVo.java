package io.metaxk.module.mes.controller.admin.order.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 *
 * @author 万界星空
 * @time 2023/8/2 13:34
 */
@Data
public class SaleProgressResVo {


    /**
     * 销售单编号
     */
    private String number;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;

    /**
     * 	销售数量
     */
    private BigDecimal quantity;

    /**
     * 已出库
     */
    private BigDecimal sendOut;

    /**
     * 剩余未出库
     */
    private BigDecimal noSend;

    /**
     * 状态
     */
    private String status;

    /**
     * 库存状态(KG)
     */
    private String inventoryStatus;


}
