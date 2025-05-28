package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.OtherInboundQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.OtherInbound;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/22 16:32
 */
public interface OtherInboundService {

    PageResult<OtherInbound> findPage(OtherInboundQueryVo otherInboundQueryVo);

    OtherInbound findOtherInboundById(Long id);

    Integer saveOtherInbound(OtherInbound otherInbound);

    Integer updateOtherInbound(OtherInbound otherInbound);

    Integer removeOtherInboundItem(List<Long> ids);
}
