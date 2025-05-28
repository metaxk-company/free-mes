package io.metaxk.module.mes.service.order;


import io.metaxk.module.mes.dal.dataobject.order.OutboundRecord;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/19 15:15
 */
public interface OutboundRecordService {


    String saveOutboundCount(OutboundRecord outboundCount);


    List<OutboundRecord> findByOutboundNum(String number);

    Integer removeByOutboundNum(String number);

    Boolean returnGood(String qrCode);

}
