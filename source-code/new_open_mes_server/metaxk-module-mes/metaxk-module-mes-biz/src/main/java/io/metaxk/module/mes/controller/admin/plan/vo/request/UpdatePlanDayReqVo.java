package io.metaxk.module.mes.controller.admin.plan.vo.request;

import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.plan.vo
 *
 * @author 万界星空
 * @time 2023/8/4 16:30
 */

@Data
public class UpdatePlanDayReqVo {

    /**
     * 日计划id
     */
    private Long id;
    /**
     * 日期
     */
    private String day;
    /**
     * 生产订单编号
     */
    private String moNumber;
    /**
     * 产品编号
     */
    private String productNumber;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 计划数量
     */
    private Double planQty;
    /**
     * 单位
     */
    private String unit;

}
