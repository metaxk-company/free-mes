package io.metaxk.module.mes.service.impl.order;


import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.ProductPickItem;
import io.metaxk.module.mes.dal.mysql.order.ProductPickItemMapper;
import io.metaxk.module.mes.service.order.ProductPickItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/21 14:25
 */
@Service
public class ProductPickItemServiceImpl implements ProductPickItemService {

    @Resource
    private ProductPickItemMapper productPickItemMapper;

    @Override
    public Integer removeByNumber(String number) {
        LambdaQueryWrapperX<ProductPickItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProductPickItem::getPickNumber,number);
        return productPickItemMapper.delete(queryWrapperX);
    }

    @Override
    public List<ProductPickItem> findProductPickItemByNum(String number) {
        LambdaQueryWrapperX<ProductPickItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProductPickItem::getPickNumber,number);
        return productPickItemMapper.selectList(queryWrapperX);
    }
}
