package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.QuoteItem;
import io.metaxk.module.mes.dal.mysql.order.QuoteItemMapper;
import io.metaxk.module.mes.service.order.QuoteItemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 14:48
 */
@Service
public class QuoteItemServiceImpl implements QuoteItemService {

    @Resource
    private QuoteItemMapper orderQuoteItemMapper;

    @Override
    public Integer saveOrderQuoteItem(QuoteItem orderQuoteItem) {
        return orderQuoteItemMapper.insert(orderQuoteItem);
    }

    @Override
    public Integer removeOrderQuoteItem(List<Long> ids) {
        return orderQuoteItemMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateOrderQuoteItem(QuoteItem orderQuoteItem) {
        return orderQuoteItemMapper.updateById(orderQuoteItem);
    }

    @Override
    public QuoteItem findOrderQuoteItemById(Long id) {
        return orderQuoteItemMapper.selectById(id);
    }

    @Override
    public PageResult<QuoteItem> findPage(QuoteItemQueryVo orderQuoteItemQueryVo) {
        return orderQuoteItemMapper.findPage(orderQuoteItemQueryVo);
    }

    @Override
    public Integer removeByNumber(String number) {
        LambdaQueryWrapperX<QuoteItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(QuoteItem::getQuoteNumber,number);
        return orderQuoteItemMapper.delete(queryWrapperX);
    }

    @Override
    public List<QuoteItem> findOrderQuoteItemByNUm(String number) {
        LambdaQueryWrapperX<QuoteItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(QuoteItem::getQuoteNumber,number);
        return orderQuoteItemMapper.selectList(queryWrapperX);
    }
}
