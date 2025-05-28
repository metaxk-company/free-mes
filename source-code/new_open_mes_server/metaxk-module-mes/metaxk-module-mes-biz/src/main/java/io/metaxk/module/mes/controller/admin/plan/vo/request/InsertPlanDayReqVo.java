package io.metaxk.module.mes.controller.admin.plan.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * io.metaxk.module.mes.controller.admin.plan.vo.request
 *
 * @author 万界星空
 * @time 2023/8/9 09:20
 */

@Data
public class InsertPlanDayReqVo {

    /**
     * 编号
     */
    @NotBlank(message = "日计划编号不能为空")
    private String number;
    /**
     * 月计划编号
     */
    @NotBlank(message = "日计划的月计划编号不能为空")
    private String monthNumber;
    /**
     * 日期
     */
    @NotBlank(message = "日计划日期不能为空")
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
    @NotNull(message = "日计划的计划数量不能为空")
    private Double planQty;
    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空")
    private String unit;

}
