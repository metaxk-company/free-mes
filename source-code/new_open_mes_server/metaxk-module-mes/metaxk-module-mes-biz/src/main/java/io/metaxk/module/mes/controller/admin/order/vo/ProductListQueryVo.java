package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/8/7 21:39
 */
@Data
public class ProductListQueryVo extends PageParam {

    private  String customerName;
    private  String productNumber;
    private  String productName;
    private  String model;
    private  String spec;


}
