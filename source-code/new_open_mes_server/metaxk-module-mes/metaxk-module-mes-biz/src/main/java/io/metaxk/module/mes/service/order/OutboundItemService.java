package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.OutboundItem;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 17:30
 */
public interface OutboundItemService {

    Integer saveOrderSaleOutboundItem(OutboundItem orderSaleOutboundItem);


    Integer removeByCode(String number);

    List<OutboundItem> findByOutboundNumber(String number);

    List<OutboundItem> findByOutboundItemNumber(String outItemNumber);

    Integer removeBySaleNumber(String saleItemNumber);


    OutboundItem findBySaleItemNumber(String saleItemNumber);

    OutboundItem findOutboundItemByItemNumber(String outboundItemNumber);

    OutboundItem findCustomerNameByOutBoundNumber(String outBoundNumber);

    OutboundItem findOutboundItemByOutboundNumber(String number);

    List<OutboundItem> findSaleItemListByNumber(String number);

    OutboundItem findOutboundItemByNumber(String outboundNumber, String outBoundItemNumber);

    OutboundItem findOutboundItemById(Long id);

    Integer updateOutboundItem(OutboundItem item);

    List<OutboundItem> findOutboundItemByOutBounderItemNum(String outboundItemNumber);
}
