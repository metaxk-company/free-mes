package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.SaleItem;
import io.metaxk.module.mes.dal.mysql.order.SaleItemMapper;
import io.metaxk.module.mes.service.order.SaleItemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/20 10:31
 */
@Service
public class SaleItemServiceImpl implements SaleItemService {

    @Resource
    private SaleItemMapper saleItemMapper;


    @Override
    public List<SaleItem> findsaleItemByNum(String number) {
        LambdaQueryWrapperX<SaleItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SaleItem::getSaleNumber,number);
        return saleItemMapper.selectList(queryWrapperX);
    }

    @Override
    public SaleItem findSaleItemById(Long id) {
        return saleItemMapper.selectById(id);
    }

    @Override
    public Integer updateSaleItem(SaleItem item) {
        return saleItemMapper.updateById(item);
    }

    @Override
    public List<SaleItem> findsaleItemByItemNum(String saleItemNumber) {
        LambdaQueryWrapperX<SaleItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SaleItem::getNumber,saleItemNumber);
        return saleItemMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeSaleItem(String saleNumber) {
        LambdaQueryWrapperX<SaleItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SaleItem::getSaleNumber,saleNumber);
        return saleItemMapper.delete(queryWrapperX);
    }


}
