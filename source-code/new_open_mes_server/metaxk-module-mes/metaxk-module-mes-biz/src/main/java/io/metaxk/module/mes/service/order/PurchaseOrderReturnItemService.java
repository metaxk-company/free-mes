package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturnItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
public interface PurchaseOrderReturnItemService {

    Integer removeByNumber(String returnNumber);

    List<PurchaseOrderReturnItem> findPurchaseOrderReturnItemByNumber(String returnNumber);
}
