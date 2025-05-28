package io.metaxk.module.mes.controller.admin.order.vo;


import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
@Data
public class PurchaseOrderReturnQueryVo extends PageParam {

    /**
     * 采购退货单编号
     */
    private String number;

    /**
     * 采购单编号
     */
    private String poNumber;

    /**
     * 供应商名称
     */
    private String vendorName;

    private String createTime;

    private String endTime;

    private List returnDate;
}
