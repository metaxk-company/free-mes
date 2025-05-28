package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.ReceiptItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderItemMapper;
import io.metaxk.module.mes.service.order.PurchaseOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 15:19
 */
@Service
public class PurchaseOrdertItemServiceImpl implements PurchaseOrderItemService {

    @Resource
    private PurchaseOrderItemMapper receiptItemMapper;


    @Override
    public Integer removeByNumber(String number) {
        LambdaQueryWrapperX<PurchaseOrderItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PurchaseOrderItem::getReceiptNumber,number);
        return receiptItemMapper.delete(queryWrapperX);
    }

    @Override
    public List<PurchaseOrderItem> findReceiptItemByNum(String number) {
        LambdaQueryWrapperX<PurchaseOrderItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PurchaseOrderItem::getReceiptNumber,number);
        return receiptItemMapper.selectList(queryWrapperX);
    }

    @Override
    public PageResult<PurchaseOrderItem> findPageByReceiptNumber( ReceiptItemQueryVo receiptItemQueryVo) {
        return receiptItemMapper.selectByReceiptNumber( receiptItemQueryVo);
    }

    @Override
    public PurchaseOrderItem findReceiptItemByNumAndCode(String itemCode, String receiptNumber) {
        return receiptItemMapper.selectOne(new LambdaQueryWrapperX<PurchaseOrderItem>().eq(PurchaseOrderItem::getItemCode,itemCode).eq(PurchaseOrderItem::getReceiptNumber,receiptNumber));
    }

    @Override
    public Integer updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        return receiptItemMapper.updateById(purchaseOrderItem);
    }

}
