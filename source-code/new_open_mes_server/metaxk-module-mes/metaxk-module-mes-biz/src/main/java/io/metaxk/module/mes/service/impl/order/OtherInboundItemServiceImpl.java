package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.OtherInboundItem;
import io.metaxk.module.mes.dal.mysql.order.OtherInboundItemMapper;
import io.metaxk.module.mes.service.order.OtherInboundItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/22 16:32
 */
@Service
public class OtherInboundItemServiceImpl implements OtherInboundItemService {

    @Resource
    private OtherInboundItemMapper otherInboundItemMapper;


    @Override
    public List<OtherInboundItem> findItemByNumber(String inNumber) {
        return otherInboundItemMapper.selectList(new LambdaQueryWrapperX<OtherInboundItem>().eq(OtherInboundItem::getInNumber,inNumber));
    }
}
