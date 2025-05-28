package io.metaxk.module.mes.controller.admin.order;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.md.Client;
import io.metaxk.module.mes.dal.dataobject.order.*;
import io.metaxk.module.mes.service.md.ClientService;
import io.metaxk.module.mes.service.order.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.error;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;


/**
 * 销售订单Controller
 * @author 万界星空MES
 */
@Tag(name = "管理后台 - 销售订单")
@RestController
@RequestMapping("/mes/order/sale")
public class SaleController {

    @Resource
    private SaleService  saleService;


    @Resource
    private SaleItemService saleItemService;

    @Resource
    private ClientService clientService;


    @Resource
    private OutboundService  outboundService;


    @Resource
    private OutboundItemService outboundItemService;


    @Resource
    private OutboundItemLabelService  outboundItemLabelService;

    @Resource
    private LabelService labelService;




    @GetMapping("/list")
    @Operation(summary = "销售订单列表")
    public CommonResult<PageResult<Sale>> list(SaleQueryVo orderSaleQueryVo){
        PageResult<Sale> pageResult = saleService.findPage(orderSaleQueryVo);
        for(Sale sale:pageResult.getList()){
            if("Y".equals(sale.getIsTax())){
                sale.setIncludeTax(sale.getIncludeTax());
            }else {
                sale.setIncludeTax(sale.getNoIncludeTax());
            }
            if(sale.getPriceModel().equals("pieces")){
                sale.setPriceModel("件数");
            }else {
                sale.setPriceModel("重量");
            }
        }
        return success(pageResult);
    }


    @GetMapping("/find/customerName")
    @Operation(summary = "销售订单中根据客户名称查询客户的地址")
    public  CommonResult<Client> findCustomerCode(String customerNumber){
        return  success(clientService.findCustomerCode(customerNumber));
    }




    /**
     * 根据客户编号查询客户报价单，对客户报价单中查询出来的客户型号进行解析
     * 解析之后根据客户报价单中的型号匹配产品表中的型号查询产品表中的数据，
     * 在根据查询出来的产品数据得到产品中的规格，再用产品中的规格匹配客户报价单中
     * 当前客户规格的加工费，如果产品中的规格超过客户报价单中规格的返回，那么此时加工费就会为空
     * 匹配加工费为空的产品
     */
    @GetMapping("/customerName")
    @Operation(summary = "根据用户编号查询报价单用户型号，根据型号匹配产品、订单号")
    public CommonResult<PageResult<ProductResVo>> findProductList(ProductResVo pv){
        if (pv.getCustomerName().matches("\\d+")) {
            Client client = clientService.findClientByCode(pv.getCustomerName());
            pv.setCustomerName(client.getClientName());
            pv.setOrderNumber(client.getOrderNumber());
        }
        List<ProductResVo>  projectResVoList =  saleService.findProductListAll(pv);
        for(ProductResVo productResVo:projectResVoList){
            productResVo.setCustomerName(pv.getCustomerName());
        }
        PageResult<ProductResVo> pageResult = new PageResult<>();
        pageResult.setTotal((long) projectResVoList.size());
        int firstIndex = (pv.getPageNo()-1)*pv.getPageSize();
        int lastIndex = pv.getPageNo()*pv.getPageSize();
        if (projectResVoList.size() != 0 && projectResVoList.size() >= 5){
            List<ProductResVo>  list = new ArrayList<>();
            if (projectResVoList.size() < lastIndex){
                list = projectResVoList.subList(firstIndex,projectResVoList.size());
            }else{
                list = projectResVoList.subList(firstIndex,lastIndex);
            }
            pageResult.setList(list);
        }else{
            pageResult.setList(projectResVoList);
        }
        return  success(pageResult);
    }







    @PutMapping("/setStatus/{id}")
    @Operation(summary = "根据id设置销售订单状态以及销售订单下明细的状态")
    public CommonResult<Integer> setStatus(@PathVariable Long id){
        Sale sale = saleService.findOrderSaleById(id);
        List<SaleItem> saleItemList = saleItemService.findsaleItemByNum(sale.getNumber());
        for(SaleItem saleItem:saleItemList){
            SaleItem item =  saleItemService.findSaleItemById(saleItem.getId());
            item.setStatus("已完成");
            saleItemService.updateSaleItem(item);
        }
        return success(saleService.updateOrderSale(findOrderSaleById(id).getData().setStatus("已完成"))).setMsg("修改成功");
    }




    @PostMapping("/save")
    @Operation(summary = "新增销售订单")
    public CommonResult<Integer> save(@RequestBody Sale sale){
        Map<String, String> map = new HashMap<>();
        for(SaleItem saleItem:sale.getItemList()){
            String productNumber = saleItem.getProductNumber();
            String color = saleItem.getColor();
            if (map.containsKey(productNumber) && map.get(productNumber).equals(color)) {
             return  error(500,"请不要添加重复的订单颜色");
            }
            map.put(productNumber, color);
        }
        return success(saleService.saveOrderSale(sale)).setMsg("新增成功");
    }




    @DeleteMapping("/batch")
    @Operation(summary = "删除销售订单")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        //删除销售订单的同时需要删除出库单
        for(Long id :ids){
            Sale sale = saleService.findOrderSaleById(id);
            List<SaleItem> saleItemList = saleItemService.findsaleItemByNum(sale.getNumber());
            if(saleItemList.size() >0) {
                for (SaleItem saleItem : saleItemList) {
                        List<OutboundItemLabel> outboundItemLabelList = outboundItemLabelService.findOutboundItemLabeByNum(saleItem.getNumber());
                        if (outboundItemLabelList.size() > 0) {
                            for (OutboundItemLabel outboundItemLabel : outboundItemLabelList) {
                                Label label = labelService.findByBarCode(outboundItemLabel.getBarCode());
                                if(label != null) {
                                    label.setStatus("1");
                                    labelService.updateLabel(label);
                                }
                                outboundItemLabelService.removeBySaleNum(outboundItemLabel.getSaleItemNumber());
                            }
                        }

                      List<OutboundItem> outboundItemList = outboundItemService.findSaleItemListByNumber(saleItem.getNumber());
                        if(outboundItemList.size() >0){
                            for(OutboundItem outboundItem:outboundItemList){
                                outboundService.removeByNumber(outboundItem.getOutboundNumber());
                                outboundItemService.removeByCode(outboundItem.getOutboundNumber());
                            }
                        }
                        saleItemService.removeSaleItem(saleItem.getSaleNumber());
                }
            }
        }
        return success(saleService.removeOrderSaleByIds(ids)).setMsg("删除成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改销售订单")
    public CommonResult<Integer> update(@RequestBody Sale orderSale){
        return success(saleService.updateOrderSale(orderSale)).setMsg("修改成功");
    }



    @GetMapping("/find/{id}")
    @Operation(summary = "销售订单详情")
    public CommonResult<Sale> findOrderSaleById(@PathVariable Long id){
        Sale sale = saleService.findOrderSaleById(id);
        List<SaleItem> saleItemList = saleItemService.findsaleItemByNum(sale.getNumber());
        if("pieces".equals(sale.getPriceModel())) {
            for (SaleItem saleItem : saleItemList) {
                saleItem.setPieces(saleItem.getQuantity());
            }
        }
        sale.setItemList(saleItemList);
        return success(sale);
    }




    @GetMapping("/get/{number}")
    @Operation(summary = "根据单号查询销售订单")
    public  CommonResult<List<SaleItem>> findByCode(@PathVariable String number){
        return  success(saleItemService.findsaleItemByNum(number));
    }




    @GetMapping("/findProgress")
    @Operation(summary = "查询销售订单进度")
    public CommonResult<PageResult<SaleProgressResVo>> findProgressBySaleNumber(SaleProgressResVo saleProgressResVo){
        List<SaleProgressResVo> list = saleService.findProgressBySaleNumber(saleProgressResVo);
        PageResult<SaleProgressResVo> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setTotal((long) list.size());
        return success(pageResult);
    }



    @GetMapping("/printPurchaseOrder/{saleNumber}")
    @Operation(summary = "打印销售订单")
    public CommonResult<List<PrintSaleDateVo>>  printPurchaseOrder(@PathVariable String saleNumber){
      List<PrintSaleDateVo>  printSaleDateVoList = saleService.printPurchaseOrder(saleNumber);
      return  success(printSaleDateVoList);
    }







    @GetMapping("/export")
    @Operation(summary = "销售单导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("销售单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), SaleExcelVo.class).registerWriteHandler(styleStrategy).sheet("销售单").doWrite(saleService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

    @GetMapping("/exportAllByIds")
    @Operation(summary = "销售单全部内容导出")
    public void exportAllByIds(HttpServletResponse response, @RequestBody List<Integer> ids){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("销售单全部内容", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), SaleAllExcelVo.class).registerWriteHandler(styleStrategy).sheet("销售单全部内容").doWrite(saleService.exportAllDataByIds(ids));
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }





}
