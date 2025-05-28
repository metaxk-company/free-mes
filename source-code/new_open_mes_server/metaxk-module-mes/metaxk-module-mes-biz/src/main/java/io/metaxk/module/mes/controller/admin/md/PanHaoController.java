package io.metaxk.module.mes.controller.admin.md;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.PanHao;
import io.metaxk.module.mes.service.md.PanHaoService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.PAN_HAO_EXIST;



/**
 * 盘号Controller
 * @author 万界星空
 */
@Tag(name = "管理后台 - 盘号")
@RestController
@RequestMapping("/mes/md/panhao")
public class PanHaoController {


    @Resource
    private PanHaoService panHaoService;



    @GetMapping("/list")
    @Operation(summary = "盘号列表")
    public CommonResult<PageResult<PanHao>> list(PanHaoQueryVo panHaoQueryVo){
        return success(panHaoService.findPage(panHaoQueryVo));
    }


    @GetMapping("/listAll")
    @Operation(summary = "盘号列表")
    public CommonResult<List<PanHao>> listAll(){
        return success(panHaoService.listAll());
    }



    @PostMapping("/save")
    @Operation(summary = "新增盘号")
    public CommonResult<Integer> save(@RequestBody PanHao panHao){
        if( panHaoService.findPanHaoByNum(panHao.getNumber()) != null){
            throw exception(PAN_HAO_EXIST);
        }
        return success(panHaoService.savePanHao(panHao)).setMsg("新增成功");
    }




    @DeleteMapping("/batch")
    @Operation(summary = "删除盘号")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(panHaoService.removePanHaoByIds(ids)).setMsg("删除成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改盘号")
    public CommonResult<Integer> update(@RequestBody PanHao panHao){
        return success(panHaoService.updatePanHao(panHao)).setMsg("修改成功");
    }

    @GetMapping("/export")
    @Operation(summary = "盘号导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("盘号", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), PanHaoExportVo.class).registerWriteHandler(styleStrategy).sheet("盘号").doWrite(panHaoService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }


}
