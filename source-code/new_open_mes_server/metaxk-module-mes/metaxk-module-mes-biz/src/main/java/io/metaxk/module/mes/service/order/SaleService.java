package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.Sale;

import java.util.List;


/**
 * 销售订单Service
 * @author 万界星空MES
 */
public interface SaleService {

    Integer saveOrderSale(Sale orderSale);

    Integer removeOrderSaleByIds(List<Long> ids);

    Integer updateOrderSale(Sale orderSale);

    Sale findOrderSaleById(Long id);

    PageResult<Sale> findPage(SaleQueryVo orderSaleQueryVo);


    List<Sale> findBySaleNumber(String saleNumber);

    List<SaleProgressResVo> findProgressBySaleNumber(SaleProgressResVo saleProgressResVo);


    List<ProductResVo> findProductList(ProductResVo pv);

    List<PrintSaleDateVo> printPurchaseOrder(String saleNumber);

    List<ProductResVo> findProductListAll(ProductResVo pv);

    PageResult<Sale> saleCountPage(SaleCountQueryVo saleCountQueryVo);

    List<SaleCountVo> findOutBound(SaleCountQueryVo saleCountQueryVo);

    Sale findSaleByNUmAndCustomerName(String saleNumber, String customerName);

    Integer updateSale(Sale saleDo);

    List<SaleExcelVo> exportData();

    List<SaleAllExcelVo> exportAllDataByIds(List<Integer> ids);

    //Sale findSaleBySaleNumber(String saleNumber);
}
