package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturnItem;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderReturnItemMapper;
import io.metaxk.module.mes.service.order.PurchaseOrderReturnItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
@Service
public class PurchaseOrderReturnItemServiceImpl implements PurchaseOrderReturnItemService {

    @Resource
    private PurchaseOrderReturnItemMapper purchaseOrderReturnItemMapper;

    @Override
    public Integer removeByNumber(String returnNumber) {
        LambdaQueryWrapperX<PurchaseOrderReturnItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PurchaseOrderReturnItem::getReturnNumber,returnNumber);
        return purchaseOrderReturnItemMapper.delete(queryWrapperX);
    }

    @Override
    public List<PurchaseOrderReturnItem> findPurchaseOrderReturnItemByNumber(String returnNumber) {
        LambdaQueryWrapperX<PurchaseOrderReturnItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PurchaseOrderReturnItem::getReturnNumber,returnNumber);
        return purchaseOrderReturnItemMapper.selectList(queryWrapperX);
    }
}
