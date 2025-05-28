package io.metaxk.module.mes.dal.dataobject.wh;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 到货通知实体类
 * @author 万界星空
 * @time 2023/7/25 17:06
 */
@Data
@TableName("wh_inbound_rec_bill")
public class InboundRecBill extends EntityCommon {
    /**
     * id
     */
    private Long id;

    /**
     * 编号
     */
    private String number;

    /**
     * 采购单编号
     */
    private String receiptNumber;

    /**
     * 币种
     */
    private String currency;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 仓库
     */
    private String wareHouse;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 交货日期
     */
    private String deliveryDate;

    /**
     * 产品类别
     */
    private String productType;

    /**
     * 不含税总价
     */
    private BigDecimal noIncludTax;

    /**
     * 含税总价
     */
    private BigDecimal includTax;

    /**
     *到货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receiveDate;

    /**
     * 状态
     */
    private String status;
}
