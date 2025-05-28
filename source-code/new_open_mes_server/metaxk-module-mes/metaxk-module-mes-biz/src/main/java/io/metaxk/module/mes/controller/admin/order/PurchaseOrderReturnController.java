package io.metaxk.module.mes.controller.admin.order;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderReturnExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderReturnQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteExportVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturn;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturnItem;
import io.metaxk.module.mes.service.order.PurchaseOrderReturnItemService;
import io.metaxk.module.mes.service.order.PurchaseOrderReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;

/**
 * @author 万界星空
 * @time 2023/8/15 11:55
 */

@Tag(name = "管理后台 - 采购退货单")
@RestController
@RequestMapping("/mes/order/po/return")
public class PurchaseOrderReturnController {

    @Resource
    private PurchaseOrderReturnService purchaseOrderReturnService;
    @Resource
    private PurchaseOrderReturnItemService purchaseOrderReturnItemService;


    @GetMapping("/list")
    @Operation(summary = "采购退货单列表")
    public CommonResult<PageResult<PurchaseOrderReturn>> list(PurchaseOrderReturnQueryVo purchaseOrderReturnQueryVo){
        List returnDate = purchaseOrderReturnQueryVo.getReturnDate();
        if (returnDate.size() != 0){
            purchaseOrderReturnQueryVo.setCreateTime(returnDate.get(0).toString());
            purchaseOrderReturnQueryVo.setEndTime(returnDate.get(1).toString());
        }
        PageResult<PurchaseOrderReturn> result = purchaseOrderReturnService.findPage(purchaseOrderReturnQueryVo);
        return success(result);
    }


    @PostMapping("/save")
    @Operation(summary = "新增采购退货单")
    public CommonResult<Integer> save(@RequestBody PurchaseOrderReturn purchaseOrderReturn){
        return  success(purchaseOrderReturnService.savePurchaseOrderReturn(purchaseOrderReturn)).setMsg("新增成功");
    }


    @PostMapping("/update")
    @Operation(summary = "修改采购退货单")
    public CommonResult<Integer> update(@RequestBody PurchaseOrderReturn purchaseOrderReturn){
        return  success(purchaseOrderReturnService.updatePurchaseOrderReturn(purchaseOrderReturn)).setMsg("修改成功");
    }

    @DeleteMapping("/deleteBatch")
    @Operation(summary = "删除采购退货单")
    public CommonResult<Integer> deleteBatch(@RequestBody List<Long> ids){
        for(Long id:ids){
            purchaseOrderReturnItemService.removeByNumber(purchaseOrderReturnService.findPurchaseOrderReturn(id).getNumber());
        }
        return  success(purchaseOrderReturnService.removePurchaseOrderReturn(ids)).setMsg("删除成功");
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "采购退货单详情")
    public CommonResult<PurchaseOrderReturn> findReceiptById(@PathVariable Long id){
        PurchaseOrderReturn purchaseOrderReturn = purchaseOrderReturnService.findPurchaseOrderReturn(id);
        List<PurchaseOrderReturnItem> purchaseOrderReturnItemList =  purchaseOrderReturnItemService.findPurchaseOrderReturnItemByNumber(purchaseOrderReturn.getNumber());
        purchaseOrderReturn.setPurchaseOrderReturnItemList(purchaseOrderReturnItemList);
        return success(purchaseOrderReturn);
    }






    @GetMapping("/export")
    @Operation(summary = "采购退货导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("采购退货单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), PurchaseOrderReturnExcelVo.class).registerWriteHandler(styleStrategy).sheet("采购退货单").doWrite(purchaseOrderReturnService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }







}
