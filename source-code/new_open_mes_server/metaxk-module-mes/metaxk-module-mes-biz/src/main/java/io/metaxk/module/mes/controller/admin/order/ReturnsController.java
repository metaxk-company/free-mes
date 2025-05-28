package io.metaxk.module.mes.controller.admin.order;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnsQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItemLabel;
import io.metaxk.module.mes.dal.dataobject.order.Returns;
import io.metaxk.module.mes.dal.dataobject.order.ReturnsItem;
import io.metaxk.module.mes.service.md.ClientService;
import io.metaxk.module.mes.service.order.OutboundItemLabelService;
import io.metaxk.module.mes.service.order.ReturnsItemService;
import io.metaxk.module.mes.service.order.ReturnsService;
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
 * 退货记录 Controller
 * @author 万界星空
 * @time 2023/7/18 16:41
 */
@Tag(name = "管理后台 - 退货单")
@RestController
@RequestMapping("/mes/order/returns")
public class ReturnsController {


    @Resource
    private ReturnsService  returnsService;
    @Resource
    private ReturnsItemService returnsItemService;
    @Resource
    private ClientService clientService;
    @Resource
    private OutboundItemLabelService outboundItemLabelService;



    @GetMapping("/list")
    @Operation(summary = "退货单列表")
    public CommonResult<PageResult<Returns>> list(ReturnsQueryVo returnsQueryVo){
        List returnDate = returnsQueryVo.getReturnDate();
        if (returnDate.size() != 0){
            returnsQueryVo.setCreateTime(returnDate.get(0).toString());
            returnsQueryVo.setEndTime(returnDate.get(1).toString());
        }
        PageResult<Returns> pageResult = returnsService.findPage(returnsQueryVo);
        /*for(Returns returns: pageResult.getList()){
            if(clientService.findClientByName(returns.getCustomerName()) != null) {
                returns.setCustomerNumber(clientService.findClientByName(returns.getCustomerName()).getClientCode());
            }
        }*/
        return success(pageResult);
    }




    @PostMapping("/save")
    @Operation(summary = "新增退货单")
    public CommonResult<Integer> save(@RequestBody Returns returns){
        return  success(returnsService.saveReturns(returns)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    @Operation(summary = "删除退货单")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            returnsItemService.removeByNumber(returnsService.findReturnById(id).getNumber());
        }
        return  success(returnsService.removeReturns(ids)).setMsg("删除成功");
    }




    @Operation(summary = "修改退货单")
    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody Returns returns){
        return  success(returnsService.updateReturns(returns)).setMsg("修改成功");
    }




    @GetMapping("/find/{id}")
    @Operation(summary = "退货单详情")
    public CommonResult<Returns> findReceiptById(@PathVariable Long id){
        Returns returns = returnsService.findReturnById(id);
        List<ReturnsItem> returnsItemList =  returnsItemService.findReturnsItemByNum(returns.getNumber());
        returns.setReturnsItemList(returnsItemList);
        return success(returns);
    }


    @GetMapping("/getOutboundItemLabel/{boxNumber}")
    @Operation(summary = "根据箱号获取产品信息")
    public CommonResult getOutboundItemLabel(@PathVariable String boxNumber){
        OutboundItemLabel outboundItemLabel = new OutboundItemLabel();
        outboundItemLabel.setBoxNumber(boxNumber);
        outboundItemLabel.setStatus("2");//已出库

        //查询已出库的信息
        return success(outboundItemLabelService.getOutboundItemLabel(outboundItemLabel));
    }







    @GetMapping("/export")
    @Operation(summary = "客户退货单导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("客户退货单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ReturnExcelVo.class).registerWriteHandler(styleStrategy).sheet("客户退货单").doWrite(returnsService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }




}
