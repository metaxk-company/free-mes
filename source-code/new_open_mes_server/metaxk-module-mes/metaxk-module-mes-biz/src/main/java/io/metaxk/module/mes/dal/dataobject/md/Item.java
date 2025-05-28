package io.metaxk.module.mes.dal.dataobject.md;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;

/**
 * 物料产品
 *  @author 万界星空
 */
@TableName("md_item")
@Data
public class Item extends EntityCommon {

    /**
     * 产品物料ID
     */
    private Long id;

    /**
     * 外部系统id
     */
    private String externalId;
    /**
     * 产品物料编码
     */
    private String itemCode;

    /**
     * 线别
     */
    private String lineType;

    /**
     * 供应商
     */
    private String vendor;


    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 规格
     */
    private String spec;

    /**
     * 型号
     */
    private String model;


    /**
     * 有效日期
     */
    private String effectiveDate;


    /**
     * 品类
     */
    private String kind;

    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 物料类型ID
     */
    private Long itemTypeId;
    /**
     * 物料类型编码
     */
    private String itemTypeCode;
    /**
     *
     * 物料类型名称
     */
    private String itemTypeName;

    /**
     * 产品物料标识
     */
    private String itemOrProduct;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 是否设置安全库存
     */
    private String safeStockFlag;
    /**
     * 最低库存量
     */
    private Double minStock;
    /**
     * 最大库存量
     */
    private Double maxStock;
    /**
     * 备注
     */
    private String remark;

    /**
     * 转化后数值
     */
    private String quantityTos;
    /**
     * 换算后单位
     */
    private String unitTo;

    /**
     *  采购价格
     */
    private BigDecimal purchasePrice;

    /**
     * 销售价格
     */
    private  BigDecimal salePrice;


    private  BigDecimal  quantity;

    /**
     *  状态
     */
    //private String status;


    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Boolean deleted;



}
