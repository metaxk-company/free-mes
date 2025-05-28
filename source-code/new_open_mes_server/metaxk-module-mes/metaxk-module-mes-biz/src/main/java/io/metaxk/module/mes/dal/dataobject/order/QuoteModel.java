package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 报价单型号实体类
 * @author 万界星空
 * @time 2023/7/17 15:02
 */
@Data
@TableName("order_quote_model")
public class QuoteModel extends EntityCommon {

    private Long id;

    private String model;

}
