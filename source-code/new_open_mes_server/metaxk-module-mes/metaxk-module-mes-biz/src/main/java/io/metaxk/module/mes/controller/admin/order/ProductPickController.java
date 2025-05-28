package io.metaxk.module.mes.controller.admin.order;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.ProductPick;
import io.metaxk.module.mes.dal.dataobject.order.ProductPickItem;
import io.metaxk.module.mes.service.order.ProductPickItemService;
import io.metaxk.module.mes.service.order.ProductPickService;
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
 * @time 2023/7/21 14:12
 */
@Tag(name = "管理后台 - 生产领料")
@RestController
@RequestMapping("/mes/product/pick")
public class ProductPickController {

    @Resource
    private ProductPickService productPickService;

    @Resource
    private ProductPickItemService productPickItemService;

    @GetMapping("/list")
    @Operation(summary = "生产领料列表")
    public CommonResult<PageResult<ProductPick>> list(ProductPickQueryVo productPickQueryVo){
        List pickDate = productPickQueryVo.getPickDate();
        if (pickDate.size() != 0){
            productPickQueryVo.setStartTime(pickDate.get(0).toString());
            productPickQueryVo.setEndTime(pickDate.get(1).toString());
        }
        PageResult<ProductPick> result = productPickService.findPage(productPickQueryVo);
        return success(result);
    }


    @PostMapping("/save")
    @Operation(summary = "新增生产领料")
    public CommonResult<Integer> save(@RequestBody ProductPick productPick){
        return  success(productPickService.saveProductPick(productPick)).setMsg("新增成功");
    }


    @DeleteMapping("/batch")
    @Operation(summary = "删除生产领料")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            productPickItemService.removeByNumber(productPickService.findProductPickById(id).getNumber());
        }
        return  success(productPickService.removeProductPick(ids)).setMsg("删除成功");
    }

    @Operation(summary = "修改生产领料")
    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody ProductPick productPick){
        return  success(productPickService.updateProductPick(productPick)).setMsg("修改成功");
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "生产领料详情")
    public CommonResult<ProductPick> findReceiptById(@PathVariable Long id){
        ProductPick productPick = productPickService.findProductPickById(id);
        List<ProductPickItem> receiptItemList =  productPickItemService.findProductPickItemByNum(productPick.getNumber());
        productPick.setProductPickItemList(receiptItemList);
        return success(productPick);
    }

    @GetMapping("/export")
    @Operation(summary = "生产领料导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("生产领料", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ProductPickExportVo.class).registerWriteHandler(styleStrategy).sheet("生产领料").doWrite(productPickService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

    @GetMapping("/exportAllByIds")
    @Operation(summary = "生产领料全部内容导出")
    public void exportAllByIds(HttpServletResponse response, @RequestBody List<Integer> ids){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("生产领料全部内容", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ProductPickAllExportVo.class).registerWriteHandler(styleStrategy).sheet("生产领料全部内容").doWrite(productPickService.listAllDataByIds(ids));
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

}
