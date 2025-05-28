package io.metaxk.module.mes.dal.dataobject.md;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 产品BOM关系
 * @author 万界星空
 */
@TableName("md_product_bom")
@Data
public class ProductBom extends EntityCommon {

    /**
     * 流水号
     */
    private Long id;
    /**
     * 物料产品ID
     */
    private Long itemId;
    /**
     * BOM物料ID
     */
    private Long bomItemId;
    /**
     * BOM物料编码
     */
    private String bomItemCode;
    /**
     * BOM物料名称
     */
    private String bomItemName;
    /**
     * BOM物料规格
     */
    private String bomItemSpec;
    /**
     * BOM物料单位
     */
    private String unitOfMeasure;
    /**
     * 产品物料标识
     */
    private String itemOrProduct;
    /**
     * 物料使用比例
     */
    private BigDecimal quantity;
    /**
     * 是否启用
     */
    private String enableFlag;

}
