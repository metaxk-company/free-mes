package io.metaxk.module.mes.dal.dataobject.pro;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;



/**
 * 产品制程 DO
 * @author 万界星空
 */
@TableName("pro_route_product")
@Data
public class RouteProduct extends EntityCommon {

    /**
     * 记录ID
     */
    private Long id;
    /**
     * 工艺路线ID
     */
    private Long routeId;
    /**
     * 产品物料ID
     */
    private Long itemId;
    /**
     * 产品物料编码
     */
    private String itemCode;
    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 生产数量
     */
    private Integer quantity;
    /**
     * 生产用时
     */
    private BigDecimal productionTime;
    /**
     * 时间单位
     */
    private String timeUnitType;
    /**
     * 备注
     */
    private String remark;


}
