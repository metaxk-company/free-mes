package io.metaxk.module.mes.dal.dataobject.pro;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 生产工单BOM
 * @author 万界星空
 */
@TableName("pro_workorder_bom")
@Data
public class WorkOrderBom extends EntityCommon {

    /**
     * 行ID
     */
    private Long id;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * BOM物料ID
     */
    private Long itemId;
    /**
     * BOM物料编号
     */
    private String itemCode;
    /**
     * BOM物料名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String itemSpc;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 物料产品标识
     */
    private String itemOrProduct;
    /**
     * 预计使用量
     */
    private BigDecimal quantity;
    /**
     * 备注
     */
    private String remark;
}
