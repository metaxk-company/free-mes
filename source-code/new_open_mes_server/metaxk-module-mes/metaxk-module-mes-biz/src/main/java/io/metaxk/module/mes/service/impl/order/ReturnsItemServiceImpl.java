package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.ReturnsItem;
import io.metaxk.module.mes.dal.mysql.order.ReturnsItemMapper;
import io.metaxk.module.mes.service.order.ReturnsItemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 17:04
 */
@Service
public class ReturnsItemServiceImpl implements ReturnsItemService {

    @Resource
    private ReturnsItemMapper returnsItemMapper;


    @Override
    public Integer removeByNumber(String number) {
        LambdaQueryWrapperX<ReturnsItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReturnsItem::getReturnNumber,number);
        return returnsItemMapper.delete(queryWrapperX);
    }

    @Override
    public List<ReturnsItem> findReturnsItemByNum(String number) {
        LambdaQueryWrapperX<ReturnsItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReturnsItem::getReturnNumber,number);
        return returnsItemMapper.selectList(queryWrapperX);
    }
}
