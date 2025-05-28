package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.InboundQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.InboundVo;
import io.metaxk.module.mes.dal.dataobject.order.Inbound;
import io.metaxk.module.mes.controller.admin.order.vo.*;

import java.util.List;

/**
 * 采购入库Service
 * @author 万界星空MES
 */
public interface InboundService {

    Integer saveInbound(Inbound inbound);

    Integer removeInboundByIds(List<Long> ids);

    Integer updateInbound(Inbound inbound);

    Inbound findInboundById(Long id);


    PageResult<Inbound> findPage(InboundQueryVo inboundQueryVo);


    List<InboundVo> inBoundPrint(String number);


    Integer removeInboundByNum(String number);

    List<InboundExcelVo> exportData();
}
