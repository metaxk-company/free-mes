package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.md.Client;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItem;
import io.metaxk.module.mes.dal.dataobject.order.Sale;
import io.metaxk.module.mes.dal.dataobject.order.SaleItem;
import io.metaxk.module.mes.dal.mysql.md.ClientMapper;
import io.metaxk.module.mes.dal.mysql.order.OutboundItemMapper;
import io.metaxk.module.mes.dal.mysql.order.SaleItemMapper;
import io.metaxk.module.mes.dal.mysql.order.SaleMapper;
import io.metaxk.module.mes.service.order.OutboundItemService;
import io.metaxk.module.mes.service.order.SaleService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 销售订单ServiceImpl
 * @author 万界星空MES
 */
@Service
public class SaleServiceImpl implements SaleService {

    @Resource
    private SaleMapper saleMapper;

    @Resource
    private SaleItemMapper saleItemMapper;

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Resource
    private OutboundItemMapper outboundItemMapper;



    @Override
    public Integer saveOrderSale(Sale orderSale) {
        BigDecimal totalQuantity = BigDecimal.ZERO;
        for (int i=0; i<orderSale.getItemList().size(); ++i) {
            orderSale.getItemList().get(i).setSaleNumber(orderSale.getNumber());
            BigDecimal quantity = orderSale.getItemList().get(i).getQuantity();
            totalQuantity = totalQuantity.add(quantity);
            SaleItem saleItem = orderSale.getItemList().get(i);
            saleItem.setSpecPrice(orderSale.getItemList().get(i).getModel() + "|" +orderSale.getItemList().get(i).getSpec() + "|" + orderSale.getItemList().get(i).getPrice().toString());
            saleItem.setNumber(autoCodeUtil.genSerialCode(UserConstants.SALE_DETAIL_CODE,null));
            saleItem.setSaleItemNumber(saleItem.getNumber());
            if(orderSale.getPriceModel().equals("pieces")){
                saleItem.setQuantity(saleItem.getPieces());
                totalQuantity = totalQuantity.add(saleItem.getPieces());
            }
            saleItemMapper.insert(saleItem);
        }
        if(StringUtils.isNotBlank(orderSale.getCopperLabelName()) || StringUtils.isNotBlank(orderSale.getAluminiumName())) {
            //设置铜价铝价完整名称
            orderSale.setCopperPriceTwo(orderSale.getCopperLabelName());
            orderSale.setAluminiumPriceTwo(orderSale.getAluminiumName());
            //解析铜价铝价
            orderSale.setCopperPrice(orderSale.getCopperPrice());
            orderSale.setAluminiumPrice(orderSale.getAluminiumPrice());
        }
        orderSale.setQuantity(totalQuantity).setStatus("未完成");
        orderSale.setStatus("未完成");
        orderSale.setCustomerNumber(orderSale.getCustomerName());
        orderSale.setCustomerName(clientMapper.selectOne(new LambdaQueryWrapperX<Client>().eq(Client::getClientCode,orderSale.getCustomerName())).getClientName());
        return saleMapper.insert(orderSale);
    }






    @Override
    public Integer removeOrderSaleByIds(List<Long> ids) {
        Map<String, Object> map = new HashMap<>();
        if(ids.size() >0) {
            for (Long id : ids) {
                Sale sale = saleMapper.selectById(id);
                if (sale != null && sale.getNumber() != null) {
                    map.clear();
                    map.put("sale_number", sale.getNumber());
                    saleItemMapper.deleteByMap(map);
                }
            }
        }
        return saleMapper.deleteBatchIds(ids);
    }






    @Override
    public Integer updateOrderSale(Sale sale) {
        saleItemMapper.delete(new LambdaQueryWrapperX<SaleItem>().eq(SaleItem::getSaleNumber,sale.getNumber()));
        BigDecimal totalQuantity = BigDecimal.ZERO;
        for(SaleItem saleItem:sale.getItemList()){
            SaleItem item = new SaleItem();
            if(sale.getPriceModel().equals("pieces")){
                item.setQuantity(saleItem.getPieces());
                totalQuantity = totalQuantity.add(item.getQuantity());
            }else {
                item.setQuantity(saleItem.getQuantity());
                totalQuantity = totalQuantity.add(saleItem.getQuantity());
            }
            item.setNumber(autoCodeUtil.genSerialCode(UserConstants.SALE_DETAIL_CODE,null)).setSaleNumber(sale.getNumber())
                    .setProductNumber(saleItem.getProductNumber()).setLineType(saleItem.getLineType())
                    .setModel(saleItem.getModel()).setSpec(saleItem.getSpec()).setUnit(saleItem.getUnit())
                    .setRawPrice(saleItem.getRawPrice()).setProcessingFee(saleItem.getProcessingFee()).setPrice(saleItem.getPrice())
                    .setStocks(saleItem.getStocks()).setTotalPrice(saleItem.getTotalPrice()).setDiscount(saleItem.getDiscount()).setColor(saleItem.getColor())
                    .setPanhao(saleItem.getPanhao()).setCustomerCode(saleItem.getCustomerCode()).setInventoryNumber(saleItem.getInventoryNumber())
                    .setWarrantNumber(saleItem.getWarrantNumber()).setSpecPrice(saleItem.getSpecPrice()).setRemark(saleItem.getRemark())
                    .setTotalQuantity(saleItem.getTotalQuantity()).setTotalTare(saleItem.getTotalTare())
                    .setPieces(saleItem.getPieces()).setStatus(saleItem.getStatus()).setSaleItemNumber(item.getNumber());
            saleItemMapper.insert(item);


            List<OutboundItem> outboundItemList = outboundItemMapper.findOutItemByNumAndItemCode(item.getSaleNumber(),item.getProductNumber());
            if(outboundItemList.size() > 0){
                for(OutboundItem outboundItem :outboundItemList){
                    OutboundItem outboundItem1 = outboundItemMapper.selectById(outboundItem.getId());
                    outboundItem1.setItemCode(item.getProductNumber()).setSaleNumber(item.getSaleNumber())
                                    .setModel(item.getModel()).setCustomerName(sale.getCustomerName()).setSpec(item.getSpec())
                                    .setPrice(new BigDecimal(item.getPrice())).setLineType(item.getLineType())
                                    .setCustomerCode(item.getCustomerCode()).setColor(item.getColor()).setPanhao(item.getPanhao())
                                    .setTotalPrice(item.getTotalPrice()).setUnit(item.getUnit()).setQuantity(item.getQuantity())
                                    .setSendOut(BigDecimal.valueOf(0)).setNoSend(item.getQuantity());
                            outboundItemMapper.updateById(outboundItem1);
                }
            }
        }
        sale.setQuantity(totalQuantity);
        return saleMapper.updateById(sale);
    }


    @Override
    public Sale findOrderSaleById(Long id) {
        return saleMapper.selectById(id);
    }


    @Override
    public PageResult<Sale> findPage(SaleQueryVo orderSaleQueryVo) {
        return saleMapper.findPage(orderSaleQueryVo);
    }





    @Override
    public List<Sale> findBySaleNumber(String saleNumber) {
        return saleMapper.selectList(new LambdaQueryWrapperX<Sale>().eq(Sale::getNumber,saleNumber));
    }


    @Override
    public List<SaleProgressResVo> findProgressBySaleNumber(SaleProgressResVo saleProgressResVo) {
        return saleMapper.findProgressBySaleNumber(saleProgressResVo);
    }

    @Override
    public List<ProductResVo> findProductList(ProductResVo pv) {
        return saleMapper.findProductList(pv);
    }

    @Override
    public List<PrintSaleDateVo> printPurchaseOrder(String saleNumber) {
        return saleMapper.printPurchaseOrder(saleNumber);
    }

    @Override
    public List<ProductResVo> findProductListAll(ProductResVo pv) {
        return saleMapper.findProductListAll(pv);
    }

    @Override
    public PageResult<Sale> saleCountPage(SaleCountQueryVo saleCountQueryVo) {
        return saleMapper.saleCountPage(saleCountQueryVo);
    }


    @Override
    public List<SaleCountVo> findOutBound(SaleCountQueryVo saleCountQueryVo) {
        return saleMapper.findCountOutBound(saleCountQueryVo);
    }

    @Override
    public Sale findSaleByNUmAndCustomerName(String saleNumber, String customerName) {
        LambdaQueryWrapperX<Sale> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Sale::getCustomerName,customerName);
        queryWrapperX.eq(Sale::getNumber,saleNumber);
        queryWrapperX.last("LIMIT 1");
        return saleMapper.selectOne(queryWrapperX);
    }

    @Override
    public Integer updateSale(Sale saleDo) {
        return saleMapper.updateById(saleDo);
    }

    @Override
    public List<SaleExcelVo> exportData() {
        List<Sale> saleList = saleMapper.selectList();
        ArrayList<SaleExcelVo> saleExcelVo = new ArrayList<>(saleList.size());
        saleList.forEach(dict -> {
            SaleExcelVo excelDictDTO = new SaleExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            if(excelDictDTO.getPriceModel().equals("pieces")){
                excelDictDTO.setPriceModel("件数");
            }else {
                excelDictDTO.setPriceModel("重量");
            }
            if("Y".equals(dict.getIsTax())){
                excelDictDTO.setPrice(dict.getIncludeTax());
            }else {
                excelDictDTO.setPrice(dict.getNoIncludeTax());
            }
            saleExcelVo.add(excelDictDTO);
        });
        return saleExcelVo;
    }

    @Override
    public List<SaleAllExcelVo> exportAllDataByIds(List<Integer> ids) {
        return saleMapper.exportAllByIds(ids);
    }

}
