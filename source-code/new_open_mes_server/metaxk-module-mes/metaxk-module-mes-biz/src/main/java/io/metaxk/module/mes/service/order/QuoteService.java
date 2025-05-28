package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Quote;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteExportVo;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 14:10
 */
public interface QuoteService {

    Integer saveOrderQuote(Quote orderQuote);

    Integer removeOrderQuote(List<Long> ids);

    Integer updateOrderQuote(Quote orderQuote);

    Quote findOrderQuoteById(Long id);

    PageResult<Quote> findPage(QuoteQueryVo orderQuoteQueryVo);

    List<Quote> findModelByName(String customerName);

    List<Quote> listAll();

    Quote findQuoteByNameLineTypeModel(String customerName, String lineType, String model);

    List<QuoteExportVo> exportData(List<String> ids);

    List<QuoteExportVo> exportAllData();
}
