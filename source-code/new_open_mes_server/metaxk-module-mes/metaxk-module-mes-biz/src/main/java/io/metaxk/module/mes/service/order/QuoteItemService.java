package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.QuoteItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 14:43
 */
public interface QuoteItemService {
    
    Integer saveOrderQuoteItem(QuoteItem orderQuoteItem);


    Integer removeOrderQuoteItem(List<Long> ids);

    Integer updateOrderQuoteItem(QuoteItem orderQuoteItem);

    QuoteItem findOrderQuoteItemById(Long id);

    PageResult<QuoteItem> findPage(QuoteItemQueryVo orderQuoteItemQueryVo);

    Integer removeByNumber(String number);

    List<QuoteItem> findOrderQuoteItemByNUm(String number);
}
