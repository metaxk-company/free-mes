package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Quote;
import io.metaxk.module.mes.dal.dataobject.order.QuoteItem;
import io.metaxk.module.mes.dal.mysql.order.QuoteItemMapper;
import io.metaxk.module.mes.dal.mysql.order.QuoteMapper;
import io.metaxk.module.mes.service.order.QuoteService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 14:11
 */
@Service
public class QuoteServiceImpl implements QuoteService {

    @Resource
    private QuoteMapper orderQuoteMapper;

    @Resource
    private QuoteItemMapper orderQuoteItemMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;


    @Override
    public Integer saveOrderQuote(Quote orderQuote) {
        //设置流水号
        orderQuote.setNumber(autoCodeUtil.genSerialCode(UserConstants.ORDERQUOTE_CODE,null));

        for(QuoteItem orderQuoteItem: orderQuote.getOrderQuoteItemList()){
            QuoteItem quoteItem = new QuoteItem();
            BeanUtils.copyProperties(orderQuoteItem,quoteItem);
            quoteItem.setQuoteNumber(orderQuote.getNumber());
            quoteItem.setSpec(quoteItem.getStartSpec() + "-" + quoteItem.getEndSpec());
            orderQuoteItemMapper.insert(quoteItem);
        }
        return  orderQuoteMapper.insert(orderQuote);
    }



    @Override
    public Integer removeOrderQuote(List<Long> ids) {
        return orderQuoteMapper.deleteBatchIds(ids);
    }


    @Override
    public Integer updateOrderQuote(Quote orderQuote) {
        Quote quote = orderQuoteMapper.selectById(orderQuote.getId());
        //修改的时候数组先执行删除方法在添加
        orderQuoteItemMapper.delete(new LambdaQueryWrapperX<QuoteItem>().eq(QuoteItem::getQuoteNumber,quote.getNumber()));
        for (QuoteItem orderQuoteItem : orderQuote.getOrderQuoteItemList()) {
            QuoteItem quoteItem = new QuoteItem();
            BeanUtils.copyProperties(orderQuoteItem, quoteItem);
            quoteItem.setQuoteNumber(quote.getNumber());
            quoteItem.setSpec(quoteItem.getStartSpec() + "-" + quoteItem.getEndSpec());
            orderQuoteItemMapper.insert(quoteItem);
        }
        return orderQuoteMapper.updateById(orderQuote);
    }





    @Override
    public Quote findOrderQuoteById(Long id) {
        return orderQuoteMapper.selectById(id);
    }

    @Override
    public PageResult<Quote> findPage(QuoteQueryVo orderQuoteQueryVo) {
        return orderQuoteMapper.findPage(orderQuoteQueryVo);
    }

    @Override
    public List<Quote> findModelByName(String customerName) {
        LambdaQueryWrapperX<Quote> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Quote::getCustomerName,customerName);
        return orderQuoteMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Quote> listAll() {
        return orderQuoteMapper.selectList();
    }

    @Override
    public Quote findQuoteByNameLineTypeModel(String customerName, String lineType, String model) {
        LambdaQueryWrapperX<Quote> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Quote::getCustomerName,customerName);
        queryWrapperX.eq(Quote::getLineType,lineType);
        queryWrapperX.eq(Quote::getModel,model);
        return orderQuoteMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<QuoteExportVo> exportData(List<String> number) {
        return orderQuoteMapper.exportData(number);
    }

    @Override
    public List<QuoteExportVo> exportAllData() {
        return orderQuoteMapper.exportAllData();
    }


}
