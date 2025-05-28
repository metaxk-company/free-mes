package io.metaxk.module.mes.service.order;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderReturnQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturn;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderReturnExcelVo;
import java.util.Collection;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
public interface PurchaseOrderReturnService {

    PageResult<PurchaseOrderReturn> findPage(PurchaseOrderReturnQueryVo purchaseOrderReturnQueryVo);

    Integer savePurchaseOrderReturn(PurchaseOrderReturn purchaseOrderReturn);

    Integer updatePurchaseOrderReturn(PurchaseOrderReturn purchaseOrderReturn);

    Integer removePurchaseOrderReturn(List<Long> ids);

    PurchaseOrderReturn findPurchaseOrderReturn(Long id);

    List<PurchaseOrderReturnExcelVo> exportData();
}
