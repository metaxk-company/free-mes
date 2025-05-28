package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.ItemPriceExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteModelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SaleItemPriceQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.SaleItemPriceVo;
import io.metaxk.module.mes.dal.dataobject.order.QuoteModel;
import io.metaxk.module.mes.dal.dataobject.order.SaleItemPrice;
import io.metaxk.module.mes.dal.mysql.order.SaleItemPriceMapper;
import io.metaxk.module.mes.service.order.SaleItemPriceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 15:29
 */
@Service
public class SaleItemPriceServiceImpl implements SaleItemPriceService {

    @Resource
    private SaleItemPriceMapper saleItemPriceMapper;


    @Override
    public Integer saveOrderSalePrice(SaleItemPrice orderSalePrice) {
        orderSalePrice.setPriceKg(orderSalePrice.getPriceTon().divide(new BigDecimal("1000")));
        return saleItemPriceMapper.insert(orderSalePrice);
    }


    @Override
    public Integer removeOrderSalePrice(List<Long> ids) {
        return saleItemPriceMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateOrderSalePrice(SaleItemPrice orderSalePrice) {
        orderSalePrice.setPriceKg(orderSalePrice.getPriceTon().divide(new BigDecimal("1000")));
        return saleItemPriceMapper.updateById(orderSalePrice);
    }

    @Override
    public SaleItemPrice findOrderSalePriceById(Long id) {
        return saleItemPriceMapper.selectById(id);
    }

    @Override
    public PageResult<SaleItemPrice> findPage(SaleItemPriceQueryVo orderSalePriceQueryVo) {
        return saleItemPriceMapper.findPage(orderSalePriceQueryVo);
    }



    @Override
    public  List<SaleItemPriceVo>  findPrice(String copper) {
        return saleItemPriceMapper.findPrice(copper);
    }

    @Override
    public List<ItemPriceExportVo> exportData() {
        List<SaleItemPrice> saleItemPriceList = saleItemPriceMapper.selectList();
        ArrayList<ItemPriceExportVo> itemPriceExportVo = new ArrayList<>(saleItemPriceList.size());
        saleItemPriceList.forEach(dict -> {
            ItemPriceExportVo excelDictDTO = new ItemPriceExportVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            itemPriceExportVo.add(excelDictDTO);
        });
        return itemPriceExportVo;
    }


}
