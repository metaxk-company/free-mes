package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author 万界星空MES
 */
@Data
public class SaleQueryVo extends PageParam {

    private String saleNumber;

    private String customerName;

    private String status;

    private String startTime;

    private String endTime;

}
