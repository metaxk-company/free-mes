package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabelItem;
import io.metaxk.module.mes.dal.mysql.order.SemiLabelItemMapper;
import io.metaxk.module.mes.service.order.SemiLabelItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
@Service
public class SemiLabelItemServiceImpl implements SemiLabelItemService {


    @Resource
    private SemiLabelItemMapper semiLabelItemMapper;

    @Override
    public List<SemiLabelItem> selectSemiLabelItemByNumber(String semiNumber) {
        return semiLabelItemMapper.selectSemiLabelItemByNumber(semiNumber);
    }

    @Override
    public Integer deleteSemiLabelItem(String semiNumber) {
        LambdaQueryWrapperX<SemiLabelItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SemiLabelItem::getSemiNumber,semiNumber);
        return semiLabelItemMapper.delete(queryWrapperX);
    }
}
