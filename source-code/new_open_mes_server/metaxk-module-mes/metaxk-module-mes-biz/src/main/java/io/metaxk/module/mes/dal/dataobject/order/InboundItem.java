package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;


/**
 * 采购入库实体子类
 * @author 万界星空MES
 */
@Data
@TableName("wh_po_inbound_item")
public class InboundItem extends EntityCommon {

    private Long id;


    /**
     * 编号
     */
    private String itemCode;


    /**
     * 名称
     */
    private String itemName;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String spec;


    /**
     * 批次号
     */
    private String batchNumber;


    /**
     * 供应商
     */
    private String vendor;


    /**
     * 箱号
     */
    private String boxNumber;

    /**
     * 入库数量
     */
    private BigDecimal quantity;


    /**
     * 单价
     */
    private BigDecimal purchasePrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;


    /**
     * 单位
     */
    private String unitOfMeasure;

    /**
     * 类别
     */
    private String kind;

    /**
     * 条码
     */
    private String barcode;


    /**
     * 生产日期
     */
    private String productionDate;


    /**
     * 有效日期
     */
    private String effectiveDate;



    /**
     * 备注
     */
    private String remark;


    /**
     * 入库编号
     */
    private String inNumber;


    /**
     * 采购单号
     */
    private String receiptNumber;
}
