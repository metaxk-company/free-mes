package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 其他采购入库实体子类
 * @author 万界星空MES
 */
@Data
@TableName("wh_other_po_inbound_item")
public class OtherInboundItem extends EntityCommon {

    private Long id;

    /**
     * 入库编号
     */
    private String inNumber;


    /**
     * 采购单号
     */
    private String receiptNumber;

    /**
     * 到货通知单
     */
    private String recNumber;


    /**
     * 产品编号
     */
    private String itemCode;


    /**
     * 产品名称
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
     * 到货数
     */
    private BigDecimal quantity;

    /**
     * 入库数量
     */
    private BigDecimal amount;

    /**
     * 	不合格数
     */
    public BigDecimal  unqualifiedNumber;


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


}
