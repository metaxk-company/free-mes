package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 报价单明细表
 * @author 万界星空
 * @time 2023/7/17 10:06
 */
@Data
@TableName("order_quote_item")
public class QuoteItem extends EntityCommon {

    /**
     * id
     */
    private Long id;

    /**
     * 客户报价单编号
     */
    private String quoteNumber;

    /**
     * 规格
     */
    private String spec;


    /**
     * 加工费
     */
    private BigDecimal price;

    /**
     * 开始规格
     */
    private BigDecimal startSpec;

    /**
     * 结束规格
     */
    private BigDecimal endSpec;

}
