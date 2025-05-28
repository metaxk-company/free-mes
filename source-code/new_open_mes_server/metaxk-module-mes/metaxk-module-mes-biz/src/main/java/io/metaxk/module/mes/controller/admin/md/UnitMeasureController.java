package io.metaxk.module.mes.controller.admin.md;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.module.mes.controller.admin.md.vo.UnitMeasureExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.UnitMeasureQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.UnitMeasure;
import io.metaxk.module.mes.service.md.UnitMeasureService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.util.*;
import java.io.IOException;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 计量单位")
@RestController
@RequestMapping("/mes/md/unit/measure")
public class UnitMeasureController {

    @Resource
    private UnitMeasureService unitMeasureService;





    @GetMapping("/enableList")
    @Operation(summary = "计量单位已启用条件分页查询")
    @PreAuthorize("@ss.hasPermission('md:unitmeasure:enableList')")
    public CommonResult<List<UnitMeasure>>  enableList() {
        List<UnitMeasure> list = unitMeasureService.getEnableList();
        return success(list);
    }



    @GetMapping("/list")
    @Operation(summary = "计量单位条件分页查询")
    @PreAuthorize("@ss.hasPermission('md:unitmeasure:list')")
    public CommonResult<PageResult<UnitMeasure>>  list(UnitMeasureQueryVo page) {
        PageResult<UnitMeasure> pageResult = unitMeasureService.getUnitMeasurePage(page);
        return success(pageResult);
    }




    @PostMapping("/save")
    @Operation(summary = "新增计量单位")
    @PreAuthorize("@ss.hasPermission('md:unitmeasure:save')")
    public CommonResult<Integer>  save( @RequestBody UnitMeasure measure) {
        measure.setCreateTime(new Date());
        measure.setMeasureCode(measure.getMeasureName());
        return success(unitMeasureService.createUnitMeasure(measure)).setMsg("新增成功");
    }






    @PutMapping("/update")
    @Operation(summary = "修改计量单位")
    @PreAuthorize("@ss.hasPermission('md:unitmeasure:update')")
    public CommonResult<Integer> update( @RequestBody UnitMeasure measure) {
        measure.setMeasureCode(measure.getMeasureName());
        return success(unitMeasureService.updateUnitMeasure(measure)).setMsg("修改成功");
    }





    @DeleteMapping("/batch")
    @PreAuthorize("@ss.hasPermission('md:unitmeasure:delete')")
    @Operation(summary = "删除计量单位")
    public  CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success( unitMeasureService.removeUnitMeasures(ids)).setMsg("删除成功");
    }



    @GetMapping("/get/{id}")
    @Operation(summary = "计量单位详情")
    @PreAuthorize("@ss.hasPermission('md:unitmeasure:get')")
    public CommonResult<UnitMeasure>  getById(@PathVariable Long id) {
        return success(unitMeasureService.getUnitMeasure(id));
    }



    @GetMapping("/find/{measureCode}")
    @Operation(summary = "根据单位编号得到单位名称")
    public CommonResult<List<UnitMeasure>> findMeasureCode(@PathVariable String measureCode){
        return success(unitMeasureService.findMeasureNameByMeasureCode(measureCode));
    }


    @GetMapping("/selectall")
    @Operation(summary = "计量单位查询全部")
    public CommonResult<List<UnitMeasure>> selectAll(){
        List<UnitMeasure> list = unitMeasureService.selectMdUnitMeasureList();
        return success(list);
    }



    @GetMapping("/listprimary")
    public CommonResult<List<UnitMeasure>> listPrimary(){
        List<UnitMeasure> list = unitMeasureService.selectMdUnitMeasureList();
        return success(list);
    }



    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('mes:md:unitmeasure:export')")
    @Operation(summary = "计量单位导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("计量单位", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), UnitMeasureExcelVo.class).registerWriteHandler(styleStrategy).sheet("计量单位").doWrite(unitMeasureService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }


}
