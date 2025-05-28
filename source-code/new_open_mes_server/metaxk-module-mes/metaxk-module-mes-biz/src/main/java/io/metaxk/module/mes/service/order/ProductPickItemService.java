package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.ProductPickItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/21 14:25
 */
public interface ProductPickItemService {

    Integer removeByNumber(String number);

    List<ProductPickItem> findProductPickItemByNum(String number);
}
