package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 其他采购入库实体类
 * @author 万界星空MES
 */
@Data
@TableName("wh_other_po_inbound")
public class OtherInbound extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 入库编号
     */
    private String inNumber;


    /**
     * 采购单号
     */
   // private String receiptNumber;

    /**
     *  到货通知单编号
     */
   // private String recNumber;


    /**
     * 仓库
     */
    private String wareHouse;


    /**
     * 存储位置
     */
    private String location;

    /**
     * 采购日期
     */
    private String receiptDate;


    /**
     * 备注
     */
    private String remark;


    /**
     * 来源
     */
    private String source;




    /**
     * 交货日期
     */
    private String deliveryDate;


    /**
     * 状态
     */
    private String status;

    /**
     * 供应商
     */
    private String vendor;



    @TableField(exist = false)
    private List<OtherInboundItem> itemList;


    @TableField(exist = false)
    private BigDecimal quantity;
}
