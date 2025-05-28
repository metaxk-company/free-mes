package io.metaxk.module.mes.service.wh;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemVo;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBillItem;

import java.util.List;

/**
 * 到货通知明细Service
 *
 * @author 万界星空MES
 */
public interface InboundRecBillItemService {

    PageResult<InboundRecBillItem> findPage(InboundRecBillItemQueryVo inboundRecBillItemQueryVo,String flag);

    List<InboundRecBillItemVo> findInboundRecBillItem(String receiptNumber);

    InboundRecBillItem findInboundRecBillItem(String itemCode,String recNumber);

    Integer updateinboundRecBillItem(InboundRecBillItem inboundRecBillItem);

    Integer updateState(String itemCode,String recNumber,String state);
}
