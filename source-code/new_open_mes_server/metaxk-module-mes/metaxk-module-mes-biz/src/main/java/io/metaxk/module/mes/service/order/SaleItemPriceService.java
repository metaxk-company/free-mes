package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.SaleItemPriceQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.SaleItemPriceVo;
import io.metaxk.module.mes.dal.dataobject.order.SaleItemPrice;
import io.metaxk.module.mes.controller.admin.order.vo.ItemPriceExportVo;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 15:29
 */
public interface SaleItemPriceService {

    Integer saveOrderSalePrice(SaleItemPrice orderSalePrice);

    Integer removeOrderSalePrice(List<Long> ids);

    Integer updateOrderSalePrice(SaleItemPrice orderSalePrice);

    SaleItemPrice findOrderSalePriceById(Long id);

    PageResult<SaleItemPrice> findPage(SaleItemPriceQueryVo orderSalePriceQueryVo);


    List<SaleItemPriceVo>  findPrice(String copper);

    List<ItemPriceExportVo> exportData();
}
