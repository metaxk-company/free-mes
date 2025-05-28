package io.metaxk.module.mes.controller.admin.order;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabel;
import io.metaxk.module.mes.service.md.ItemService;
import io.metaxk.module.mes.service.order.SemiLabelItemService;
import io.metaxk.module.mes.service.order.SemiLabelService;
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
 * @time 2023/8/9 17:06
 */
@Tag(name = "管理后台 - 标签打印 - 半成品入库")
@RestController
@RequestMapping("/mes/order/semi/label")
public class SemiLabelController {

    @Resource
    private SemiLabelService semiLabelService;
    @Resource
    private SemiLabelItemService semiLabelItemService;
    @Resource
    private ItemService itemService;

    @GetMapping("/list")
    @Operation(summary = "半成品入库列表")
    public CommonResult<PageResult<SemiLabel>> list(SemiLabelQueryVo semiLabelQueryVo){
        PageResult<SemiLabel>  pageResult = semiLabelService.findPage(semiLabelQueryVo);
        return success(pageResult);
    }


    /*@GetMapping("/getItem")
    @Operation(summary = "根据产品编码获取产品信息")
    public CommonResult getItem(String itemCode){
        Item item = itemService.selectMdItemByItemCode(itemCode);
        SemiLabelItem semiLabelItem = new SemiLabelItem();
        semiLabelItem.setModel(item.getModel());
        semiLabelItem.setSpec(item.getSpec());
        semiLabelItem.setLineType(item.getLineType());
        semiLabelItem.setItemCode(item.getItemCode());//产品编号
        semiLabelItem.setItemName(item.getItemName());
        semiLabelItem.setItemType(item.getItemTypeName());//产品分类
        semiLabelItem.setUnitOfMeasure(item.getUnitOfMeasure());//产品单位
        semiLabelItem.setQuantity("0");//数量默认0
        return success(semiLabelItem);
    }*/


    @PostMapping("/save")
    @Operation(summary = "新增半成品入库")
    public CommonResult<Integer> save(@RequestBody SemiLabel semiLabel){
        return success(semiLabelService.saveSemiLabel(semiLabel)).setMsg("新增成功");
    }


    @PostMapping("/update")
    @Operation(summary = "修改半成品入库")
    public CommonResult<Integer> update(@RequestBody SemiLabel semiLabel){
        return success(semiLabelService.updateSemiLabel(semiLabel)).setMsg("修改成功");
    }


    @DeleteMapping("/deleteBatch")
    @Operation(summary = "删除半成品入库")
    public CommonResult<Integer>  deleteBatch(@RequestBody List<Long> ids){
        for (Long id:ids){
            SemiLabel semiLabel = semiLabelService.findSemiLabelById(id);
            semiLabelItemService.deleteSemiLabelItem(semiLabel.getNumber());
        }
        return success(semiLabelService.removeSemiLabelByIds(ids)).setMsg("删除成功");
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "半成品入库详情")
    public CommonResult<SemiLabel> findLabelById(@PathVariable Long id){
        SemiLabel semiLabel = semiLabelService.findSemiLabelById(id);
        return success(semiLabel);
    }

    @GetMapping("/export")
    @Operation(summary = "半成品入库导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("半成品入库", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), SemiLabelExportVo.class).registerWriteHandler(styleStrategy).sheet("半成品入库").doWrite(semiLabelService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

    @GetMapping("/exportAllByIds")
    @Operation(summary = "半成品入库全部内容导出")
    public void exportAllByIds(HttpServletResponse response, @RequestBody List<Integer> ids){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("半成品入库全部内容", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), SemiLabelAllExportVo.class).registerWriteHandler(styleStrategy).sheet("半成品入库全部内容").doWrite(semiLabelService.listAllDataByIds(ids));
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

}
