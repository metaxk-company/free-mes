package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/17 15:35
 */
@Data
public class SaleItemPriceQueryVo extends PageParam {

       private String category;

       private String createTime;

}
