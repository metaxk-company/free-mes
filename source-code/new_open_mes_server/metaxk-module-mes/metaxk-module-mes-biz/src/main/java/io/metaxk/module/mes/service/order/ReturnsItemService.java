package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.ReturnsItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 17:03
 */
public interface ReturnsItemService {
    Integer removeByNumber(String number);

    List<ReturnsItem> findReturnsItemByNum(String number);
}
