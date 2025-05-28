package io.metaxk.module.mes.controller.admin.order;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.DetailedInventoryResponseVO;
import io.metaxk.module.mes.controller.admin.order.vo.LabelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.LabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Label;
import io.metaxk.module.mes.service.order.LabelService;
import io.metaxk.module.mes.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.error;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;



/**
 * @author 万界星空
 * @time 2023/7/27 15:33
 */
@Tag(name = "管理后台 - 标签打印 - 成品入库")
@RestController
@RequestMapping("/mes/order/label")
public class LabelController {


    @Resource
    private LabelService labelService;


    @GetMapping("/list")
    @Operation(summary = "标签打印列表")
    public CommonResult<PageResult<Label>>  list(LabelQueryVo labelVo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(labelVo.getEndDate())) {
            try {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(sdf.parse(labelVo.getEndDate()));
                calendar.add(Calendar.DATE, 1);
                labelVo.setEndDate(sdf.format(calendar.getTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        PageResult<Label>  pageResult = labelService.findPage(labelVo);
        return  success(pageResult);
    }



    @GetMapping("/repackagedList")
    @Operation(summary = "标签打印列表")
    public CommonResult<PageResult<Label>> repackagedList(LabelQueryVo labelVo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(labelVo.getEndDate())) {
            try {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(sdf.parse(labelVo.getEndDate()));
                calendar.add(Calendar.DATE, 1);
                labelVo.setEndDate(sdf.format(calendar.getTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        PageResult<Label>  pageResult = labelService.findRepackagedListPage(labelVo);
        return  success(pageResult);
    }



    @Operation(summary = "取消重包操作")
    @GetMapping("/cancelRepackage/{id}")
    public CommonResult<Object>  cancelRepackage(@PathVariable Long id){
        Label label = labelService.findLabelById(id);
        if("1".equals(label.getStatus()) || "2".equals(label.getStatus())){
            return  error(500,"状态为已入库或已出库，无法取消重包");
        }else {
            label.setStatus("1");
            labelService.updateLabel(label);
            return  success(null).setMsg("取消重包成功");
        }
    }




    @PostMapping("/save")
    @Operation(summary = "新增标签打印")
    public CommonResult<Integer>  save(@RequestBody Label label){
        return success(labelService.saveLabel(label)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    @Operation(summary = "删除标签打印")
    public CommonResult<Integer>  batch(@RequestBody List<Long> ids){
        return success(labelService.removeLabelByIds(ids)).setMsg("删除成功");
    }



    @PutMapping("/update")
    @Operation(summary = "修改标签打印")
    public CommonResult<Integer>  update(@RequestBody Label label){
        return success(labelService.updateLabel(label)).setMsg("修改成功");
    }



    @PutMapping("/setStatus")
    @Operation(summary = "设置状态")
    public CommonResult<Integer> setStatus( Long id, String status){
        return success(labelService.updateStatus(id, status));
    }



    @GetMapping("/statusList")
    @Operation(summary = "根据状态查找Label")
    public CommonResult<List<Label>> findStatusList(String status){
        return success(labelService.findLabelByStatus(status));
    }



    @GetMapping("/find/{id}")
    @Operation(summary = "标签打印详情")
    public CommonResult<Label> findLabelById(@PathVariable Long id){
        Label label = labelService.findLabelById(id);
       // label.setItemTypeName(itemService.findItemByCode(label.getItemCode()).getItemTypeName());
        return success(label);
    }

    @GetMapping("/export")
    @Operation(summary = "成品入库导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("成品入库", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), LabelExportVo.class).registerWriteHandler(styleStrategy).sheet("成品入库").doWrite(labelService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

    @GetMapping("/detailedInventory")
    @Operation(summary = "详细库存查询")
    public CommonResult<List<DetailedInventoryResponseVO>> detailedInventory(String lineType, String model, String spec){
        return success(labelService.detailedInventory(lineType, model, spec));
    }

}
