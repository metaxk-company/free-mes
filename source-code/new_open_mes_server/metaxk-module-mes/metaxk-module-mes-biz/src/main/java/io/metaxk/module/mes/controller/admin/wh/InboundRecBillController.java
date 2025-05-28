package io.metaxk.module.mes.controller.admin.wh;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.ReceiptItemQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecordResult;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBillItem;
import io.metaxk.module.mes.service.qc.ReceiveRecordResultService;
import io.metaxk.module.mes.service.wh.InboundRecBillItemService;
import io.metaxk.module.mes.service.wh.InboundRecBillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static io.metaxk.framework.common.pojo.CommonResult.success;

/**
 * @author 万界星空
 * @time 2023/7/18 15:11
 */
@Tag(name = "管理后台 - 到货通知单")
@RestController
@RequestMapping("/mes/wh/rec/bill")
public class InboundRecBillController {

    @Resource
    private InboundRecBillService inboundRecBillService;
    @Resource
    private InboundRecBillItemService inboundRecBillItemService;
    @Resource
    private ReceiveRecordResultService receiveRecordResultService;


    @GetMapping("/list")
    @Operation(summary = "到货通知单列表显示")
    public CommonResult<PageResult<InboundRecBill>> list(InboundRecBillQueryVo inboundRecBillQueryVo){
        PageResult<InboundRecBill> pageResult = inboundRecBillService.findPage(inboundRecBillQueryVo);
        return success(pageResult);
    }


    @GetMapping("/detail")
    @Operation(summary = "到货通知单-详情列表显示")
    public CommonResult<PageResult<InboundRecBillItem>> detailList(InboundRecBillItemQueryVo inboundRecBillItemQueryVo){
        PageResult<InboundRecBillItem> pageResult = inboundRecBillItemService.findPage(inboundRecBillItemQueryVo,"1");
        for(InboundRecBillItem inboundRecBillItem:pageResult.getList()){
            if(inboundRecBillItem.getNoIncludTax() == null) {
                inboundRecBillItem.setTotalPrice(inboundRecBillItem.getIncludTax());
            }else {
                inboundRecBillItem.setTotalPrice(inboundRecBillItem.getNoIncludTax());
            }
        }
        return success(pageResult);
    }


    @GetMapping("/receiveDetailList")
    @Operation(summary = "到货通知单-入库时显示")
    public CommonResult<PageResult<InboundRecBillItem>> receiveList(InboundRecBillItemQueryVo inboundRecBillItemQueryVo){
        //查询未加入到采购入库中，且已质检的信息
        PageResult<InboundRecBillItem> pageResult = inboundRecBillItemService.findPage(inboundRecBillItemQueryVo,"2");
        for(InboundRecBillItem inboundRecBillItem:pageResult.getList()){
            if(inboundRecBillItem.getNoIncludTax() == null) {
                inboundRecBillItem.setTotalPrice(inboundRecBillItem.getIncludTax());
            }else {
                inboundRecBillItem.setTotalPrice(inboundRecBillItem.getNoIncludTax());
            }
            InboundRecBill inboundRecBill = inboundRecBillService.selectByNumber(inboundRecBillItem.getRecNumber());
            //采购订单编号
            String receiptNumber = inboundRecBill.getReceiptNumber();
            inboundRecBillItem.setReceiptNumber(receiptNumber);

            /*List<ReceiveRecordResult> list1 = receiveRecordResultService.selectReceiveRecordResult(inboundRecBillItem.getItemCode(),inboundRecBillItem.getRecNumber(),"1");
            if (list1 != null){
                //入库数量(合格数量)
                inboundRecBillItem.setAmount(new BigDecimal(list1.size()));

            }else {
                //入库数量(合格数量)
                inboundRecBillItem.setAmount(new BigDecimal(0));
            }*/

            List<ReceiveRecordResult> list2 = receiveRecordResultService.selectReceiveRecordResult(inboundRecBillItem.getItemCode(),inboundRecBillItem.getRecNumber(),"0");
            if (list2 != null){
                //不合格数
                inboundRecBillItem.setUnqualifiedNumber(new BigDecimal(list2.size()));
            }else {
                //不合格数
                inboundRecBillItem.setUnqualifiedNumber(new BigDecimal(0));
            }

            //入库数量(到库数-不合格数)
            BigDecimal totalQuantity = inboundRecBillItem.getQuantity().subtract(inboundRecBillItem.getUnqualifiedNumber());
            inboundRecBillItem.setAmount(totalQuantity);
        }
        return success(pageResult);
    }


    @PostMapping("/receiveSome")
    @Operation(summary = "部分到货")
    @PreAuthorize("@ss.hasPermission('wh:rec:bill:receiveSome')")
    public CommonResult receiveSome(@RequestBody InboundRecBillVo inboundRecBillVo)throws Exception{

        inboundRecBillService.insertInboundRecBill(inboundRecBillVo);
        return success(200);
    }

    @PostMapping("/receiveAll")
    @Operation(summary = "全部到货")
    @PreAuthorize("@ss.hasPermission('wh:rec:bill:receiveAll')")
    public CommonResult receiveAll(@RequestBody InboundRecBillVo inboundRecBillVo)throws Exception{
        inboundRecBillService.insertInboundRecBillAll(inboundRecBillVo);
        return success(200);
    }


}
