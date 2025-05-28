package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.OtherInboundItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/22 16:32
 */
public interface OtherInboundItemService {

    List<OtherInboundItem> findItemByNumber(String inNumber);
}
