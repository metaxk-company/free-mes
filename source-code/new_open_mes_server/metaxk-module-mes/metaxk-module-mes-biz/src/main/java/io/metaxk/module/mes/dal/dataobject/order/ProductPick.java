package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 生产领料实体类
 * @author 万界星空
 * @time 2023/7/21 14:10
 */
@Data
@TableName("order_product_pick")
public class ProductPick extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 外部系统id
     */
    private String externalId;

    /**
     * 编号
     */
    private String number;

    /**
     * 币种
     */
    private String currency;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 领料日期
     */
    private String pickDate;

    /**
     * 产品类别
     */
    private String productType;

    /**
     * 不含税总价
     */
    private BigDecimal noTaxPrice;

    /**
     * 含税总价
     */
    private BigDecimal taxPrice;

    /**
     * 状态
     */
    private String status;

    /**
     * 仓库
     */
    private String warehouse;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<ProductPickItem> productPickItemList;




}
