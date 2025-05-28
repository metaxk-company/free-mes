package io.metaxk.module.mes.dal.dataobject.plan;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.dal.dataobject.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:00
 */

@Data
@TableName("plan_month")
public class PlanMonth extends BaseDO {

    /**
     * 月计划id
     */
    private Long id;
    /**
     * 编号
     */
    @NotBlank(message = "月计划编号不能为空")
    private String number;
    /**
     * 工厂名
     */
    @NotBlank(message = "月计划工厂名不能为空")
    private String factoryName;
    /**
     * 车间
     */
    @NotBlank(message = "月计划车间不能为空")
    private String workshop;
    /**
     * 生产线
     */
    @NotBlank(message = "月计划生产线不能为空")
    private String productionLine;
    /**
     * 月份
     */
    @NotBlank(message = "月计划月份不能为空")
    private String month;
    /**
     * 计划数量
     */
    private Double planQty;
    /**
     * 单位
     */
    @NotBlank(message = "月计划单位不能为空")
    private String unit;

}
