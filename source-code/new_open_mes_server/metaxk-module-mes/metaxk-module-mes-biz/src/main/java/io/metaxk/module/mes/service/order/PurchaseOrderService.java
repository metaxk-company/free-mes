package io.metaxk.module.mes.service.order;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderVo;
import io.metaxk.module.mes.controller.admin.order.vo.ReceiptQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrder;

import java.util.Collection;
import java.util.List;



/**
 * @author 万界星空
 * @time 2023/7/18 15:11
 */
public interface PurchaseOrderService {


    Integer saveReceipt(PurchaseOrder receipt);

    PurchaseOrder findReceiptById(Long id);

    Integer removeReceipt(List<Long> ids);


    Integer updateReceipt(PurchaseOrder receipt);


    PageResult<PurchaseOrder> findPage(ReceiptQueryVo receiptQueryVo);


    List<PurchaseOrder> selectList();

    List<PurchaseOrderVo> PurchaseOrderPriant(String number);

    PurchaseOrder selectByNumber(String number);

    List<PurchaseOrderExcelVo> exportData(List<String> number);

    List<PurchaseOrderExcelVo> exportAllData();
}
