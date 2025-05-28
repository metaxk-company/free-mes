package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.InboundItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 13:17
 */
public interface InboundItemService {

    List<InboundItem> findItemByNum(String number);

    List<InboundItem> findItemByPurchaseNum(String number);

    Integer removeByPurchaseNum(String receiptNumber);
}
