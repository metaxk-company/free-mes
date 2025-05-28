package io.metaxk.module.mes.controller.admin.plan.vo.request;

import lombok.Data;

/**
 * io.metaxk.module.mes.controller.admin.plan.vo
 *
 * @author 万界星空
 * @time 2023/8/4 16:30
 */

@Data
public class UpdatePlanMonthReqVo {

    /**
     * 月计划id
     */
    private Long id;
    /**
     * 工厂名
     */
    private String factoryName;
    /**
     * 车间
     */
    private String workshop;
    /**
     * 生产线
     */
    private String productionLine;
    /**
     * 单位
     */
    private String unit;

}
