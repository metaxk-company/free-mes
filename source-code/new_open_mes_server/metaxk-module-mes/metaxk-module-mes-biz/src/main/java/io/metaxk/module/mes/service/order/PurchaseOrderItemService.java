package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.ReceiptItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 15:18
 */
public interface PurchaseOrderItemService {
    
    Integer removeByNumber(String number);


    List<PurchaseOrderItem> findReceiptItemByNum(String number);

    PageResult<PurchaseOrderItem> findPageByReceiptNumber(ReceiptItemQueryVo receiptItemQueryVo);

    PurchaseOrderItem findReceiptItemByNumAndCode(String itemCode, String receiptNumber);

    Integer updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);
}
