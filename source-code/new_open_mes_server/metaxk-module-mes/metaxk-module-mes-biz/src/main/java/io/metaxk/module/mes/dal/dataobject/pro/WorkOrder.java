package io.metaxk.module.mes.dal.dataobject.pro;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;


/**
 * 生产工单
 * @author 万界星空
 */
@TableName("pro_workorder")
@Data
public class WorkOrder extends EntityCommon {
    /**
     * 工单ID
     */
    private Long id;
    /**
     * 工单编码
     */
    private String workorderCode;
    /**
     * 工单名称
     */
    private String workorderName;
    /**
     * 来源类型
     */
    private String orderSource;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 规格型号
     */
    private String productSpc;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 生产数量
     */
    private BigDecimal quantity;

    /**
     * 客户编码
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 需求日期
     */

    private String requestDate;

    /**
     * 生产订单日期
     */
    private String orderDate;

    /**
     * 生产日期
     */
    private String  produceDate;


    /**
     * 父工单
     */
   private Long parentId;
    /**
     * 所有父节点ID
     */
   private String ancestors;
    /**
     * 单据状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    private String attr1;


    private BigDecimal  produceProgress;

    /**
     * 订单报工(生产)进度
     */
    private BigDecimal productionSchedule;




    private BigDecimal remainQuantity;


}
