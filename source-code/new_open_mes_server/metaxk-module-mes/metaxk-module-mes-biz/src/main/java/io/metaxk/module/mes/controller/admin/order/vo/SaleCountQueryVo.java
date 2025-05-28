package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/8/18 13:38
 */
@Data
public class SaleCountQueryVo extends PageParam {


    private String customerNumber;

    private String customerName;

    private String saleNumber;



}
