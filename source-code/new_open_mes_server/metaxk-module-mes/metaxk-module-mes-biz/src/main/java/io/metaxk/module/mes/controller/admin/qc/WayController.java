package io.metaxk.module.mes.controller.admin.qc;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.WayExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.WayQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Way;
import io.metaxk.module.mes.service.qc.WayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.IMPORT_DATA_ERROR;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.INSPECT_WAY_NAME_EXIST;


/**
 * @author 万界星空
 * @time 2023/7/6 10:55
 */
@Tag(name = "管理后台 - 检验方式")
@RestController
@RequestMapping("/mes/qc/way")
public class WayController {


    @Resource
    private WayService inspectWayService;



    @GetMapping("/list")
    public CommonResult<PageResult<Way>> list(WayQueryVo wayQueryVo){
      PageResult<Way> pageResult = inspectWayService.findPage(wayQueryVo);
      return success(pageResult);
    }


    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody Way way){
        if(inspectWayService.findMethodByName(way.getInspectName()) != null){
            throw exception(INSPECT_WAY_NAME_EXIST);
        }
        return success( inspectWayService.saveInspectMethod(way)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success( inspectWayService.removeInspectMethod(ids)).setMsg("删除成功");
    }



    @GetMapping("/find/{id}")
    public  CommonResult<Way> findInspectMethod(@PathVariable Long id){
        return success( inspectWayService.findMethodById(id));
    }


    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody Way inspectMethod){
        return success( inspectWayService.updateInspectMethod(inspectMethod)).setMsg("修改成功");
    }




    @PostMapping("/importExcel")
    public CommonResult<Object>  importExcel(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            inspectWayService.importExcelData(inputStream);
        }catch (Exception e){
            throw exception(IMPORT_DATA_ERROR);
        }
        return  success(null).setMsg("导入成功");
    }








    @GetMapping("/templateData")
    @Operation(summary = "检验方式模板")
    public void exportTemplate(HttpServletResponse response) throws  Exception{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("检验方式模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            List<WayExcelVo> templateData = new ArrayList<>();
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), WayExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("检验方式模板")
                    .doWrite(templateData);
    }





}
