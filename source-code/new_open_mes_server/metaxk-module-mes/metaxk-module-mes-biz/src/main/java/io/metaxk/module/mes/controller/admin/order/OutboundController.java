package io.metaxk.module.mes.controller.admin.order;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.*;
import io.metaxk.module.mes.service.order.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.error;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;


/**
 * @author 万界星空
 * @time 2023/7/17 16:42
 */
@Tag(name = "管理后台 - 销售出库 - 扫描出库PC")
@RestController
@RequestMapping("/mes/order/outbound")
public class OutboundController {

    @Resource
    private OutboundService orderOutboundService;

    @Resource
    private OutboundItemService orderOutboundItemService;

    @Resource
    private OutboundItemLabelService  outboundItemLabelService;

    @Resource
    private SaleItemService saleItemService;

    @Resource
    private SaleService saleService;

    @Resource
    private LabelService labelService;


    @Resource
    private OutboundRecordService outboundRecordService;



    @GetMapping("/list")
    @Operation(summary = "销售出库----有销售单列表")
    public CommonResult<PageResult<Outbound> > list(OutboundQueryVo orderOutboundQueryVo){
        PageResult<Outbound> page = orderOutboundService.findPage(orderOutboundQueryVo);
        for(Outbound outbound:page.getList()){
            outbound.setNoSend(outbound.getQuantity().subtract(outbound.getSendOut()));
                if(outbound.getCustomerName() == null && outbound.getIsTax() == null){
                    OutboundItem outboundItem = orderOutboundItemService.findOutboundItemByOutboundNumber(outbound.getNumber());
                      Sale sale = saleService.findSaleByNUmAndCustomerName(outboundItem.getSaleNumber(),outboundItem.getCustomerName());
                     if(outboundItem != null) {
                         outbound.setCustomerName(outboundItem.getCustomerName());
                     }
                      if(sale != null) {
                          outbound.setIsTax(sale.getIsTax());
                      }
                }
                BigDecimal zeroBigDecimal = new BigDecimal("0.0");
                BigDecimal totalOutBoundPrice = new BigDecimal(BigInteger.ZERO);
                if(zeroBigDecimal.equals(outbound.getOutboundTotalPrice())){
                    List<OutboundItem> orderOutboundItemList = orderOutboundItemService.findByOutboundNumber(outbound.getNumber());
                    for(OutboundItem outboundItem:orderOutboundItemList){
                        totalOutBoundPrice = totalOutBoundPrice.add(outboundItem.getPrice().multiply(outboundItem.getQuantity())) ;
                    }
                    outbound.setOutboundTotalPrice(totalOutBoundPrice);
                }
        }
        return  success(page);
    }








    @GetMapping("/noSale/list")
    @Operation(summary = "销售出库-----无销售单列表")
    public CommonResult<PageResult<Outbound> > noSaleList(OutboundQueryVo orderOutboundQueryVo){
        PageResult<Outbound> page = orderOutboundService.findPageNoSale(orderOutboundQueryVo);
        for(Outbound outbound:page.getList()){
            outbound.setNoSend(outbound.getQuantity().subtract(outbound.getSendOut()));
            if(outbound.getCustomerName() == null && outbound.getIsTax() == null){
                OutboundItem outboundItem = orderOutboundItemService.findOutboundItemByOutboundNumber(outbound.getNumber());
                Sale sale = null;
                if(outboundItem != null) {
                     outbound.setCustomerName(outboundItem.getCustomerName());
                     sale = saleService.findSaleByNUmAndCustomerName(outboundItem.getSaleNumber(),outboundItem.getCustomerName());
                }
                if(sale != null) {
                    outbound.setIsTax(sale.getIsTax());
                }
            }
            BigDecimal zeroBigDecimal = new BigDecimal("0.0");
            BigDecimal totalOutBoundPrice = new BigDecimal(BigInteger.ZERO);
            if(zeroBigDecimal.equals(outbound.getOutboundTotalPrice())){
                List<OutboundItem> orderOutboundItemList = orderOutboundItemService.findByOutboundNumber(outbound.getNumber());
                for(OutboundItem outboundItem:orderOutboundItemList){
                    totalOutBoundPrice = totalOutBoundPrice.add(outboundItem.getPrice().multiply(outboundItem.getQuantity())) ;
                }
                outbound.setOutboundTotalPrice(totalOutBoundPrice);
            }
        }
        return  success(page);
    }










    @PostMapping("/save")
    @Operation(summary = "新增销售出库")
    public CommonResult<Integer> save(@RequestBody Outbound orderOutbound){
        List<OutboundItem> orderOutboundItemList = orderOutbound.getOrderOutboundItemList();
        String allCustomerName = null;
        for(OutboundItem outboundItem:orderOutboundItemList){
            if (outboundItem != null) {
                String customerName = outboundItem.getCustomerName();
                if (allCustomerName == null) {
                    allCustomerName = customerName;
                } else {
                    if (!allCustomerName.equals(customerName)) {
                        return error(500,"出库单中客户名不一致，请选择相同客户名");
                    }
                }
            }
        }
        return  success(orderOutboundService.saveOutBound(orderOutbound)).setMsg("新增成功");
    }




    @PostMapping("/sale/detail/save")
    @Operation(summary = "新增销售出库明细")
    public CommonResult<Integer> saveDetail(@RequestBody OutboundItem outboundItem){
        OutboundItem outboundItemDo = orderOutboundItemService.findBySaleItemNumber(outboundItem.getSaleItemNumber());
        OutboundItem item  = orderOutboundItemService.findOutboundItemByItemNumber(outboundItemDo.getNumber());
        if("已完成".equals(item.getStatus())){
            return  error(500,"当前出库明细已完成");
        }
        return  success(orderOutboundService.saveOutboundDetail(outboundItem)).setMsg("新增成功");
    }





    /**
     * 销售出库2中的删除
     */
    @DeleteMapping("/batch")
    @Operation(summary = "销售出库删除")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for (Long id:ids) {
            Outbound outbound = orderOutboundService.findOutboundById(id);

            List<OutboundItemLabel> outboundItemLabelList = outboundItemLabelService.findOutboundItemLabeByOutNum(outbound.getNumber());
            if (outboundItemLabelList.size() > 0) {
                for(OutboundItemLabel outboundItemLabel:outboundItemLabelList){
                    Label label = labelService.findByBarCode(outboundItemLabel.getBarCode());
                    label.setStatus("1");
                    labelService.updateLabel(label);
                }
                outboundItemLabelService.removeByOutboundNum(outbound.getNumber());
            }
             List<OutboundRecord>  outboundRecordList =   outboundRecordService.findByOutboundNum(outbound.getNumber());
             if(outboundRecordList.size() > 0){
                 outboundRecordService.removeByOutboundNum(outbound.getNumber());
             }

            List<OutboundItem> outboundItemList = orderOutboundItemService.findByOutboundNumber(outbound.getNumber());
            if (outboundItemList.size() > 0) {
                for (OutboundItem outboundItem : outboundItemList) {
                    String saleItemNumber = outboundItem.getSaleItemNumber();
                    List<SaleItem> saleItemList = saleItemService.findsaleItemByItemNum(saleItemNumber);

                    //修改销售单状态
                    List<SaleItem> itemList = saleItemService.findsaleItemByNum(outboundItem.getSaleNumber());
                    boolean allCompleted = false;
                    for (SaleItem itemDo : itemList) {
                        if ("已完成".equals(itemDo.getStatus())) {
                            allCompleted = true;
                            break;
                        }
                    }
                    String saleNumber = outboundItem.getSaleNumber();
                    if (allCompleted) {
                        List<Sale> saleList = saleService.findBySaleNumber(saleNumber);
                        for (Sale sale : saleList) {
                            Sale saleDo = saleService.findOrderSaleById(sale.getId());
                            saleDo.setId(saleDo.getId());
                            saleDo.setStatus("未完成");
                            saleService.updateSale(saleDo);
                        }
                    }
                    for (SaleItem saleItem : saleItemList) {
                        SaleItem item = saleItemService.findSaleItemById(saleItem.getId());
                        item.setStatus("未完成").setOutboundStatus("0");
                        saleItemService.updateSaleItem(item);
                    }
                }
                orderOutboundItemService.removeByCode(outboundItemList.get(0).getOutboundNumber());
            }
        }
        return  success(orderOutboundService.removeOrderOutbound(ids)).setMsg("删除成功");
    }


    /**
     *  销售出库中的删除
     */
//    @DeleteMapping("/batch")
//    @Operation(summary = "销售出库删除")
//    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
//        for(Long id:ids){
//            Outbound outbound = orderOutboundService.findOutboundById(id);
//            List<OutboundItem> outboundItemList = orderOutboundItemService.findByOutboundNumber(outbound.getNumber());
//            for(OutboundItem outboundItem:outboundItemList){
//                List<SaleItem> saleItemList = saleItemService.findsaleItemByItemNum(outboundItem.getSaleItemNumber());
//                for(SaleItem saleItem:saleItemList){
//                    SaleItem item = saleItemService.findSaleItemById(saleItem.getId());
//                    item.setStatus("未完成");
//                    saleItemService.updateSaleItem(item);
//                }
//                outboundItemLabelService.removeByOutItemNum(outboundItem.getNumber());
//            }
//            orderOutboundItemService.removeByCode(outbound.getNumber());
//        }
//        return  success(orderOutboundService.removeOrderOutbound(ids)).setMsg("删除成功");
//    }





    @PutMapping("/update")
    @Operation(summary = "销售出库修改")
    public CommonResult<Integer> update(@RequestBody Outbound orderOutbound){
        List<OutboundItem> orderOutboundItemList = orderOutbound.getOrderOutboundItemList();
        String allCustomerName = null;
        for(OutboundItem outboundItem:orderOutboundItemList){
            if (outboundItem != null) {
                String customerName = outboundItem.getCustomerName();
                if (allCustomerName == null) {
                    allCustomerName = customerName;
                } else {
                    if (!allCustomerName.equals(customerName)) {
                        throw exception(CUSTOMER_NAME_IS_THE_SAME);
                    }
                }
            }
        }
        return  success(orderOutboundService.updateOrderOutbound(orderOutbound)).setMsg("修改成功");
    }



    @GetMapping("/find/{id}")
    @Operation(summary = "销售出库查询详情")
    public CommonResult<Outbound> findOrderOutboundById(@PathVariable Long id){
        return  success(orderOutboundService.findOrderOutboundById(id));
    }


    @GetMapping("/findSale")
    public CommonResult<PageResult<OutBoundSaleResVO>> findSale(OutBoundSaleReqVO outBoundSaleReqVO){
        PageResult<OutBoundSaleResVO> saleList = orderOutboundService.findSaleList(outBoundSaleReqVO);
        return success(saleList);

    }


    /**
     * 选择销售单出库
     */
    @GetMapping("/findSaleItem")
    @Operation(summary = "销售出库中查询未完成销售单的明细数据")
    public CommonResult<PageResult<OutBoundSaleItemResVO>> findSaleItem(OutBoundSaleItemResVO  outBoundSaleItemResVO){
        List<OutBoundSaleItemResVO> saleItemList = orderOutboundService.findSaleItemList(outBoundSaleItemResVO);
        for(OutBoundSaleItemResVO itemResVO:saleItemList){
            itemResVO.setPieces(BigDecimal.valueOf(0));
            itemResVO.setTotalTare(BigDecimal.valueOf(0));
            itemResVO.setTotalWeight(BigDecimal.valueOf(0));
        }
        PageResult<OutBoundSaleItemResVO> pageResult = new PageResult<>();
        pageResult.setTotal((long) saleItemList.size());
        int firstIndex = (outBoundSaleItemResVO.getPageNo()-1)*outBoundSaleItemResVO.getPageSize();
        int lastIndex = outBoundSaleItemResVO.getPageNo()*outBoundSaleItemResVO.getPageSize();
        if (saleItemList.size() != 0 && saleItemList.size() >= 1){
            List<OutBoundSaleItemResVO>  list = new ArrayList<>();
            if (saleItemList.size() < lastIndex){
                list = saleItemList.subList(firstIndex,saleItemList.size());
            }else{
                list = saleItemList.subList(firstIndex,lastIndex);
            }
            pageResult.setList(list);
        }else{
            pageResult.setList(saleItemList);
        }
        return success(pageResult);
    }




    /**
     * 不选择销售单出库
     */
    @GetMapping("/findSaleOrderItem")
    @Operation(summary = "销售出库中查询未完成销售单的明细数据")
    public CommonResult<PageResult<OutBoundSaleItemResVO>> findSaleOrderItem(OutBoundSaleItemResVO  outBoundSaleItemResVO){
        List<OutBoundSaleItemResVO> saleItemList = orderOutboundService.findSaleItemAll(outBoundSaleItemResVO);
        for(OutBoundSaleItemResVO saleItemResVO:saleItemList){
            saleItemResVO.setPieces(BigDecimal.valueOf(0));
            saleItemResVO.setTare(BigDecimal.valueOf(0));
            saleItemResVO.setTotalPrice(null);
        }
        PageResult<OutBoundSaleItemResVO> pageResult = new PageResult<>();
        pageResult.setTotal((long) saleItemList.size());
        int firstIndex = (outBoundSaleItemResVO.getPageNo()-1)*outBoundSaleItemResVO.getPageSize();
        int lastIndex = outBoundSaleItemResVO.getPageNo()*outBoundSaleItemResVO.getPageSize();
        if (saleItemList.size() != 0 && saleItemList.size() >= 1){
            List<OutBoundSaleItemResVO>  list = new ArrayList<>();
            if (saleItemList.size() < lastIndex){
                list = saleItemList.subList(firstIndex,saleItemList.size());
            }else{
                list = saleItemList.subList(firstIndex,lastIndex);
            }
            pageResult.setList(list);
        }else{
            pageResult.setList(saleItemList);
        }
        return success(pageResult);
    }




    @Operation(summary = "销售出库明细中查看label数据")
    @GetMapping("/findItem")
    public CommonResult<PageResult<OutboundItemLabel>> findItemById(String saleItemNumber , String number){
        List<OutboundItemLabel> outboundItemLabelList = orderOutboundService.findItemById(saleItemNumber, number);
        PageResult<OutboundItemLabel> pageResult = new PageResult<>();
        pageResult.setList(outboundItemLabelList);
        pageResult.setTotal((long) outboundItemLabelList.size());
        return  success(pageResult);
    }



    @Operation(summary = "PC扫描出库明细详情")
    @GetMapping("/findLabel")
    public CommonResult<List<Label>> findLabel(String model){
        return success(orderOutboundService.findLabelList(model));
    }




    @Operation(summary = "销售出库选择出库")
    @GetMapping("/outBound/{number}")
    public CommonResult<String> outBound(@PathVariable  String  number){
        orderOutboundService.outBound(number);
        return success("" ).setMsg("出库成功");
    }


    @Operation(summary = "销售退库选择退库")
    @GetMapping("/stockReturn/{number}")
    public CommonResult<String> stockReturn(@PathVariable  String  number){
        orderOutboundService.stockReturn(number);
        return success("").setMsg("退库成功");
    }




    @DeleteMapping("/remove/{id}/{number}")
    @Operation(summary = "根据出库编号和label id删除label表数据")
    public CommonResult<Integer> removeItemLabel(@PathVariable Long id , @PathVariable String number){
        if(outboundItemLabelService.findByIdAndNumber(id,number) == null){
            return success(0).setMsg(null);
        }
        return success( outboundItemLabelService.removeItemLabel(id, number)).setMsg("移除成功");
    }



    @DeleteMapping("/remove/{saleItemNumber}")
    @Operation(summary = "删除出库中销售明细的编号以及明细下的入库数据")
    public  CommonResult<Integer> removeOutBound(@PathVariable String saleItemNumber){
        if(orderOutboundItemService.findBySaleItemNumber(saleItemNumber) == null){
            return  error(500,"数据暂未添加进数据库");
        }
        List<OutboundItemLabel> outboundItemLabelList =  outboundItemLabelService.findOutboundItemLabeByNum(saleItemNumber);
        if(outboundItemLabelList.size() >0) {
            for (OutboundItemLabel outboundItemLabel : outboundItemLabelList) {
                Label label = labelService.findByBarCode(outboundItemLabel.getBarCode());
                if (label != null){
                    label.setStatus("1");
                    labelService.updateLabel(label);
                }
                outboundItemLabelService.removeBySaleNum(outboundItemLabel.getSaleItemNumber());
            }
        }
        List<SaleItem> saleItemList = saleItemService.findsaleItemByItemNum(saleItemNumber);
        for (SaleItem saleItem : saleItemList) {
            SaleItem item = saleItemService.findSaleItemById(saleItem.getId());
            item.setStatus("未完成").setOutboundStatus("0");
            saleItemService.updateSaleItem(item);

//            List<SaleItem> saleItemList1 = saleItemService.findsaleItemByItemNum(saleItemNumber);
//            boolean allCompleted  = true;
//            for (SaleItem itemDo : saleItemList1) {
//                if (!"已完成".equals(itemDo.getStatus())) {
//                    allCompleted = false;
//                    break;
//                }
//            }
//            if (allCompleted) {
//                List<Sale> saleList = saleService.findBySaleNumber(saleItem.getSaleNumber());
//               for(Sale sale:saleList){
//                   Sale sale1 = saleService.findOrderSaleById(sale.getId());
//                   sale1.setStatus("已完成") ;
//                   saleService.updateSale(sale1);
//               }
//            }
        }




     return  success( orderOutboundItemService.removeBySaleNumber(saleItemNumber)).setMsg("删除成功");
    }







    @GetMapping("/print")
    @Operation(summary = "出库打印")
    public CommonResult  printDataInclTax(String number,String label){
        if("hanshui".equals(label)) {
            List<PrintDataVo> printDataVoList = orderOutboundService.printDataInclTax(number);
            return  success(printDataVoList);
        }else  if("wushui".equals(label)) {
            List<PrintDataVo>  printDataVoList =  orderOutboundService.printDataNoInclTax(number);
            return  success(printDataVoList);
        }else if("wujiage".equals(label)){
            List<PrintDataVo>  printDataVoList =  orderOutboundService.printDataNoPrice(number);
            return  success(printDataVoList);
        }else if("zhiling".equals(label)){
            List<PrintDataVo>  printDataVoList =  orderOutboundService.printDataInstruct(number);
            return  success(printDataVoList);
        }else if("bangmadan".equals(label)){
            List<PrintDataVo>  printDataVoList =  orderOutboundService.printDataPoundScale(number);
            return  success(printDataVoList);
        }
        return  success(null);
    }


    /**
     *  扫码出库
     */
    @GetMapping("/scanCode")
    public CommonResult<Object> scanCodeOutBound(String code,String outBoundNumber,String itemNumber){
        orderOutboundService.scanCodeOutBound(code,outBoundNumber,itemNumber);
        return  success(null).setMsg("出库成功");
    }






    @GetMapping("/export")
    @Operation(summary = "销售出库导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("出库单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), OutBoundExcelVo.class).registerWriteHandler(styleStrategy).sheet("出库单").doWrite(orderOutboundService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

    @GetMapping("/exportAllByIds")
    @Operation(summary = "销售出库全部内容导出")
    public void exportAllByIds(HttpServletResponse response, @RequestBody List<Integer> ids){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("销售出库全部内容", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), OutBoundAllExcelVo.class).registerWriteHandler(styleStrategy).sheet("销售出库全部内容").doWrite(orderOutboundService.listAllDataByIds(ids));
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

}
