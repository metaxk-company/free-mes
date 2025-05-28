package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.InboundItem;
import io.metaxk.module.mes.dal.mysql.order.InboundItemMapper;
import io.metaxk.module.mes.service.order.InboundItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 13:17
 */
@Service
public class InboundItemServiceImpl implements InboundItemService {

    @Resource
    private InboundItemMapper  inboundItemMapper;


    @Override
    public List<InboundItem> findItemByNum(String number) {
        return inboundItemMapper.selectList(new LambdaQueryWrapperX<InboundItem>().eq(InboundItem::getInNumber,number));
    }

    @Override
    public List<InboundItem> findItemByPurchaseNum(String number) {
        return inboundItemMapper.selectList(new LambdaQueryWrapperX<InboundItem>().eq(InboundItem::getReceiptNumber,number));
    }

    @Override
    public Integer removeByPurchaseNum(String receiptNumber) {
        return inboundItemMapper.delete(new LambdaQueryWrapperX<InboundItem>().eq(InboundItem::getReceiptNumber,receiptNumber));
    }


}

