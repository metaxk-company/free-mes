package io.metaxk.module.mes.controller.admin.order;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ItemQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.md.Item;
import io.metaxk.module.mes.dal.dataobject.order.InboundItem;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrder;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.service.md.ItemService;
import io.metaxk.module.mes.service.order.InboundItemService;
import io.metaxk.module.mes.service.order.InboundService;
import io.metaxk.module.mes.service.order.PurchaseOrderItemService;
import io.metaxk.module.mes.service.order.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;


/**
 * @author 万界星空
 * @time 2023/7/18 15:11
 */
@Tag(name = "管理后台 - 采购订单")
@RestController
@RequestMapping("/mes/order/receipt")
public class PurchaseOrderController {


    @Resource
    private PurchaseOrderService receiptService;


    @Resource
    private PurchaseOrderItemService receiptItemService;


    @Resource
    private ItemService itemService;


    @Resource
    private InboundItemService inboundItemService;


    @Resource
    private InboundService inboundService;


    @GetMapping("/listAll")
    @Operation(summary = "采购订单根据产品分类编号查询产品信息")
    public CommonResult<PageResult<Item>> getItemPage(ItemQueryVo itemPage) {
        PageResult<Item> pageResult = itemService.findItemPage(itemPage);
        return success(pageResult);
    }



    @GetMapping("/list")
    @Operation(summary = "采购订单列表")
    public CommonResult<PageResult<PurchaseOrder>> list(ReceiptQueryVo receiptQueryVo){
        List deliveryDate = receiptQueryVo.getDeliveryDate();
        if (deliveryDate.size() != 0){
            receiptQueryVo.setCreateTime(deliveryDate.get(0).toString());
            receiptQueryVo.setEndTime(deliveryDate.get(1).toString());
        }
        PageResult<PurchaseOrder> result = receiptService.findPage(receiptQueryVo);
        return success(result);
    }




    @PostMapping("/save")
    @Operation(summary = "新增采购单")
    public CommonResult<Integer> save(@RequestBody PurchaseOrder receipt){
        return  success(receiptService.saveReceipt(receipt)).setMsg("新增成功");
    }




    @DeleteMapping("/batch")
    @Operation(summary = "删除采购单")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            PurchaseOrder purchaseOrder = receiptService.findReceiptById(id);
            //删除蔡工订单的同时，也会删除采购入库表以及采购入库下的明细表
            List<InboundItem> itemList  = inboundItemService.findItemByPurchaseNum(purchaseOrder.getNumber());
            for(InboundItem inboundItem:itemList){
                String number = inboundItem.getInNumber();
                inboundService.removeInboundByNum(number);
                inboundItemService.removeByPurchaseNum(inboundItem.getReceiptNumber());
            }
            receiptItemService.removeByNumber(purchaseOrder.getNumber());
        }
        return  success(receiptService.removeReceipt(ids)).setMsg("删除成功");
    }



    @Operation(summary = "修改采购单")
    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody PurchaseOrder receipt){
        return  success(receiptService.updateReceipt(receipt)).setMsg("修改成功");
    }


    @Operation(summary = "采购订单详情")
    @GetMapping("/find/{id}")
    public CommonResult<PurchaseOrder> findReceiptById(@PathVariable Long id){
        PurchaseOrder receipt = receiptService.findReceiptById(id);
        List<PurchaseOrderItem> receiptItemList =  receiptItemService.findReceiptItemByNum(receipt.getNumber());
        receipt.setReceiptItemList(receiptItemList);
        return success(receipt);
    }



    @GetMapping("/purchaseOrderPriant")
    @Operation(summary = "采购订单打印")
    public CommonResult<List<PurchaseOrderVo>> PurchaseOrderPriant(String number){
        List<PurchaseOrderVo> receiptItemList =   receiptService.PurchaseOrderPriant(number);
        return success(receiptItemList);
    }


    @GetMapping("/selectAll")
    @Operation(summary = "查询所有采购订单")
    public CommonResult<List<PurchaseOrder>> selectAll() {
        return success(receiptService.selectList());
    }



    @GetMapping("/selectItemByNumber")
    @Operation(summary = "通过采购单查采购订单明细")
    public CommonResult<PageResult<PurchaseOrderItem>> selectItemByNumber(ReceiptItemQueryVo receiptItemQueryVo){
        PageResult<PurchaseOrderItem> result = receiptItemService.findPageByReceiptNumber(receiptItemQueryVo);
        List<PurchaseOrderItem> list = result.getList();
        for (PurchaseOrderItem purchaseOrderItem : list ){
            purchaseOrderItem.setTotalPrice(new BigDecimal("0"));
        }
        result.setList(list);
        return success(result);
    }






    @GetMapping("/export")
    @Operation(summary = "采购单导出")
    public void export(HttpServletResponse response, @RequestBody(required = false) List<String> number){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("采购单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
          if(number == null){
              EasyExcel.write(response.getOutputStream(), PurchaseOrderExcelVo.class).registerWriteHandler(styleStrategy).sheet("采购单").doWrite(receiptService.exportAllData());
          }else {
              EasyExcel.write(response.getOutputStream(), PurchaseOrderExcelVo.class).registerWriteHandler(styleStrategy).sheet("采购单").doWrite(receiptService.exportData(number));
          }
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }









}
