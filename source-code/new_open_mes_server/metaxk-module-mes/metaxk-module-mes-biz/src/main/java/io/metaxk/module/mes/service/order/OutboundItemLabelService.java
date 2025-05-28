package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.OutboundItemLabel;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/31 14:12
 */
public interface OutboundItemLabelService {

    List<OutboundItemLabel> findOutboundItemLabelList(Long id);

    Integer removeByNum(String number);

    Integer removeItemLabel(Long id, String number);

    OutboundItemLabel findByIdAndNumber(Long id, String number);

    List<OutboundItemLabel> findOutboundItemLabeByNum(String saleItemNumber);

    Integer removeBySaleNum(String saleItemNumber);

    OutboundItemLabel getOutboundItemLabel(OutboundItemLabel outboundItemLabel);

    Integer removeByOutItemNum(String number);

    Integer removeByOutboundNum(String number);

    List<OutboundItemLabel> findOutboundItemLabeByOutNum(String number);

    OutboundItemLabel findItemLabeByBarCode(String barCode);

    List<OutboundItemLabel> findItemLabeByPanHao(String panhao);

    List<OutboundItemLabel> findItemLabeListByBarCode(String barCode);

    Integer removeOutItemByCode(String barCode);

    Integer removeOutItemByPanHao(String panhao);
}
