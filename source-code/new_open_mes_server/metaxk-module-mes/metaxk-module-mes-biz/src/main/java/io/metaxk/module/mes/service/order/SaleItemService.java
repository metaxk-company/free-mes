package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.SaleItem;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/20 10:31
 */
public interface SaleItemService {

    List<SaleItem> findsaleItemByNum(String number);


    SaleItem findSaleItemById(Long id);

    Integer updateSaleItem(SaleItem item);

    List<SaleItem> findsaleItemByItemNum(String saleItemNumber);

    Integer removeSaleItem(String saleNumber);

}
