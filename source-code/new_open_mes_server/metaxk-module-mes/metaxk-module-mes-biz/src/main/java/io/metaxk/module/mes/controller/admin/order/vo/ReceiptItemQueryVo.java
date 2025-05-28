package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/18 15:46
 */
@Data
public class ReceiptItemQueryVo extends PageParam {

    private String number;

    private String name;

    private String model;

    private String spec;

    private String vendor;


    private String receiptNumber;


}
