package io.metaxk.module.mes.controller.admin.md;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ColorExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.ColorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Color;
import io.metaxk.module.mes.service.md.ColorService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.COLOR_EXIST;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;


/**
 * 颜色Controller
 * @author 万界星空
 */
@Tag(name = "管理后台 - 颜色")
@RestController
@RequestMapping("/mes/md/color")
public class ColorController {

    @Resource
    private ColorService colorService;


    @GetMapping("/list")
    @Operation(summary = "颜色列表")
    public CommonResult<PageResult<Color>> list(ColorQueryVo colorQueryVo){
        return success(colorService.findPage(colorQueryVo));
    }


    @GetMapping("/listAll")
    @Operation(summary = "颜色列表")
    public CommonResult<List<Color>> listAll(ColorQueryVo colorQueryVo){
        return success(colorService.listAll());
    }


    @PostMapping("/save")
    @Operation(summary = "新增颜色")
    public CommonResult<Integer> save(@RequestBody Color color){
        if(colorService.findColorByName(color.getName()) != null){
            throw exception(COLOR_EXIST);
        }
        return success(colorService.saveColor(color)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    @Operation(summary = "删除颜色")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(colorService.removeColorByIds(ids)).setMsg("删除成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改颜色")
    public CommonResult<Integer> update(@RequestBody Color color){
        return success(colorService.updateColor(color)).setMsg("修改成功");
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "颜色详情")
    public CommonResult<Color> findColorById(@PathVariable Long id){
        return success(colorService.findColorById(id));
    }

    @GetMapping("/export")
    @Operation(summary = "颜色导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("颜色", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ColorExportVo.class).registerWriteHandler(styleStrategy).sheet("颜色").doWrite(colorService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

}
