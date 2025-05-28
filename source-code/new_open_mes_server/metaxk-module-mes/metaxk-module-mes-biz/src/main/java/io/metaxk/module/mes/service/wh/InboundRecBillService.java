package io.metaxk.module.mes.service.wh;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillVo;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;

import java.text.ParseException;
import java.util.List;

/**
 * 到货通知Service
 *
 * @author 万界星空MES
 */
public interface InboundRecBillService {

    PageResult<InboundRecBill> findPage(InboundRecBillQueryVo inboundRecBillQueryVo);

    void insertInboundRecBill(InboundRecBillVo inboundRecBillVo) throws ParseException;

    void insertInboundRecBillAll(InboundRecBillVo inboundRecBillVo);

    Integer updateStatus(String number,String status);

    InboundRecBill selectByNumber(String number);
}
