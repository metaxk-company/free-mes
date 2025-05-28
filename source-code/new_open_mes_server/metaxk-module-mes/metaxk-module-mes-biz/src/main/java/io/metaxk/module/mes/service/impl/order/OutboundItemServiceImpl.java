package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItem;
import io.metaxk.module.mes.dal.mysql.order.OutboundItemMapper;
import io.metaxk.module.mes.service.order.OutboundItemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 17:31
 */
@Service
public class OutboundItemServiceImpl implements OutboundItemService {

    @Resource
    private OutboundItemMapper outboundItemMapper;

    @Override
    public Integer saveOrderSaleOutboundItem(OutboundItem orderSaleOutboundItem) {
        return outboundItemMapper.insert(orderSaleOutboundItem);
    }

    @Override
    public Integer removeByCode(String number) {
        return outboundItemMapper.delete(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getOutboundNumber,number));
    }


    @Override
    public List<OutboundItem> findByOutboundNumber(String number) {
        LambdaQueryWrapperX<OutboundItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItem::getOutboundNumber,number);
        return outboundItemMapper.selectList(queryWrapperX);
    }



    @Override
    public  List<OutboundItem> findByOutboundItemNumber(String outItemNumber) {
        return outboundItemMapper.selectList(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getOutboundNumber,outItemNumber));
    }

    @Override
    public Integer removeBySaleNumber(String saleItemNumber) {
        return outboundItemMapper.delete(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getSaleItemNumber,saleItemNumber));
    }

    @Override
    public OutboundItem findBySaleItemNumber(String saleItemNumber) {
      return outboundItemMapper.selectOne(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getSaleItemNumber,saleItemNumber));
    }

    @Override
    public OutboundItem findOutboundItemByItemNumber(String outboundItemNumber) {
        return outboundItemMapper.selectOne(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getNumber,outboundItemNumber));
    }

    @Override
    public OutboundItem findCustomerNameByOutBoundNumber(String outBoundNumber) {
        return outboundItemMapper.selectOne(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getOutboundNumber,outBoundNumber).last("LIMIT 1"));
    }

    @Override
    public OutboundItem findOutboundItemByOutboundNumber(String number) {
        return outboundItemMapper.selectOne(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getOutboundNumber,number).last("LIMIT 1"));
    }

    @Override
    public List<OutboundItem> findSaleItemListByNumber(String number) {
        return outboundItemMapper.selectList(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getSaleItemNumber,number));
    }

    @Override
    public OutboundItem findOutboundItemByNumber(String outboundNumber, String outBoundItemNumber) {
        LambdaQueryWrapperX<OutboundItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItem::getOutboundNumber,outboundNumber);
        queryWrapperX.eq(OutboundItem::getNumber,outBoundItemNumber);
        return outboundItemMapper.selectOne(queryWrapperX);
    }

    @Override
    public OutboundItem findOutboundItemById(Long id) {
        return outboundItemMapper.selectById(id);
    }

    @Override
    public Integer updateOutboundItem(OutboundItem item) {
        return outboundItemMapper.updateById(item);
    }

    @Override
    public List<OutboundItem> findOutboundItemByOutBounderItemNum(String outboundItemNumber) {
        return outboundItemMapper.selectList(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getNumber,outboundItemNumber));
    }


}
