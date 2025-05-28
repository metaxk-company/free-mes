package io.metaxk.module.mes.dal.dataobject.md;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;


/**
 * 单位
 * @author 万界星空
 */
@TableName("md_unit_measure")
@Data
public class UnitMeasure extends EntityCommon {

    /**
     * 单位ID
     */
    private Long id;
    /**
     * 单位编码
     */
    private String measureCode;
    /**
     * 单位名称
     */
    private String measureName;
    /**
     * 是否是主单位
     */
    private String primaryFlag;
    /**
     * 主单位ID
     */
    private Long primaryId;
    /**
     * 与主单位换算比例
     */
    private BigDecimal changeRate;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 外部系统id
     */
    private String externalId;


}
