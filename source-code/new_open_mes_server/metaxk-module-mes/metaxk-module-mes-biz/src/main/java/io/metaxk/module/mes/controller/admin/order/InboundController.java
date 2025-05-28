package io.metaxk.module.mes.controller.admin.order;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.Inbound;
import io.metaxk.module.mes.dal.dataobject.order.InboundItem;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.service.order.InboundItemService;
import io.metaxk.module.mes.service.order.InboundService;
import io.metaxk.module.mes.service.order.PurchaseOrderItemService;
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
 * 采购入库Controller
 * @author 万界星空
 */
@Tag(name = "管理后台 - 采购入库")
@RestController
@RequestMapping("/mes/order/inbound")
public class InboundController {

    @Resource
    private InboundService inboundService;


    @Resource
    private InboundItemService  inboundItemService;


    @Resource
    private PurchaseOrderItemService receiptItemService;





    @PostMapping("/save")
    @Operation(summary = "新增采购入库")
    public CommonResult<Integer> save(@RequestBody Inbound inbound){
        return success(inboundService.saveInbound(inbound)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    @Operation(summary = "删除采购入库")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            Inbound inbound = inboundService.findInboundById(id);
            List<InboundItem> itemList = inboundItemService.findItemByNum(inbound.getInNumber());
            for(InboundItem inboundItem :itemList){
                PurchaseOrderItem purchaseOrderItem =  receiptItemService.findReceiptItemByNumAndCode(inboundItem.getItemCode(),inboundItem.getReceiptNumber());
                purchaseOrderItem.setStatus("0");
                receiptItemService.updatePurchaseOrderItem(purchaseOrderItem);
            }
        }
        return success(inboundService.removeInboundByIds(ids)).setMsg("删除成功");
    }


    @PutMapping("/update")
    @Operation(summary = "修改采购入库")
    public CommonResult<Integer> update(@RequestBody Inbound inbound){
        return success(inboundService.updateInbound(inbound)).setMsg("修改成功");
    }



    @GetMapping("/find/{id}")
    @Operation(summary = "查询采购入库详情")
    public CommonResult<Inbound> findInboundById(@PathVariable Long id){
        return success(inboundService.findInboundById(id));
    }



    @GetMapping("/list")
    @Operation(summary = "采购入库列表")
    public CommonResult<PageResult<Inbound>> list(InboundQueryVo inboundQueryVo){
        List deliveryDate = inboundQueryVo.getDeliveryDate();
        if (deliveryDate.size() != 0){
            inboundQueryVo.setStartTime(deliveryDate.get(0).toString());
            inboundQueryVo.setEndTime(deliveryDate.get(1).toString());
        }
        PageResult<Inbound> pageResult = inboundService.findPage(inboundQueryVo);
        BigDecimal totalQuantity = BigDecimal.ZERO;
        for(Inbound inbound :pageResult.getList()){
            List<InboundItem> itemList =   inboundItemService.findItemByNum(inbound.getInNumber());
            for(InboundItem inboundItem:itemList){
                totalQuantity = totalQuantity.add(inboundItem.getQuantity());
            }
            inbound.setQuantity(totalQuantity);
        }
        return success(pageResult);
    }




    @GetMapping("/receiptList")
    @Operation(summary = "根据采购单号查询采购入库条件分页查询")
    public CommonResult<PageResult<PurchaseOrderItem>> getReceiptList(ReceiptItemQueryVo receiptItemQueryVo){
        PageResult<PurchaseOrderItem> pageResult = receiptItemService.findPageByReceiptNumber(receiptItemQueryVo);
        for(PurchaseOrderItem purchaseOrderItem:pageResult.getList()){
            if(purchaseOrderItem.getNoIncludTax() == null) {
                purchaseOrderItem.setTotalPrice(purchaseOrderItem.getIncludTax());
            }else {
                purchaseOrderItem.setTotalPrice(purchaseOrderItem.getNoIncludTax());
            }
        }
        return success(pageResult);
    }



      @GetMapping("/inBoundPrint")
      @Operation(summary = "采购入库打印")
      public CommonResult<List<InboundVo>> inBoundPrint(String number){
        List<InboundVo> inboundList = inboundService.inBoundPrint(number);
        return success(inboundList);
      }



    @GetMapping("/export")
    @Operation(summary = "采购入库导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("入库单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), InboundExcelVo.class).registerWriteHandler(styleStrategy).sheet("入库单").doWrite(inboundService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }



}
