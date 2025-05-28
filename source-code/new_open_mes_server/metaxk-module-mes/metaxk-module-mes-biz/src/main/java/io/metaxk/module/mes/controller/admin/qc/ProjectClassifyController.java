package io.metaxk.module.mes.controller.admin.qc;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProjectClassify;
import io.metaxk.module.mes.service.qc.ProjectClassifyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.CLASSIFY_NAME_EXIST;
import io.swagger.v3.oas.annotations.Operation;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyExcelVo;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLEncoder;


/**
 * @author 万界星空
 * @time 2023/7/6 11:41
 */
@Tag(name = "管理后台 - 检测项目分类")
@RestController
@RequestMapping("/mes/qc/project/classify")
public class ProjectClassifyController {

    @Resource
    private ProjectClassifyService projectClassifyService;

    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody ProjectClassify projectClassify){
        if(projectClassifyService.findProjectClassifyByName(projectClassify.getProjectName()) != null){
            throw exception(CLASSIFY_NAME_EXIST);
        }
        return success( projectClassifyService.saveInspectProjectClassify(projectClassify)).setMsg("新增成功");
    }


    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success( projectClassifyService.removeInspectProjectClassify(ids)).setMsg("删除成功");
    }

    @GetMapping("/find/{id}")
    public CommonResult<ProjectClassify> findInspectProjectClassify(@PathVariable Long id){
        return success( projectClassifyService.findInspectProjectClassify(id));
    }

    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody ProjectClassify inspectProjectClassify){
        return success( projectClassifyService.updateInspectProjectClassify(inspectProjectClassify)).setMsg("修改成功");
    }


    @GetMapping("/list")
    public  CommonResult<PageResult<ProjectClassify>> list(ProjectClassifyQueryVo inspectProjectClassifyPageVO){
     PageResult<ProjectClassify> pageResult =  projectClassifyService.findPage(inspectProjectClassifyPageVO);
     return success(pageResult);
    }


    @GetMapping("/templateData")
    @Operation(summary = "检测项目分类模板")
    public void exportTemplate(HttpServletResponse response)throws  Exception {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("检测项目分类模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            List<ProjectClassifyExcelVo> templateData = new ArrayList<>();
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ProjectClassifyExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("检测项目分类模板")
                    .doWrite(templateData);
    }



    @PostMapping("/importExcel")
    public CommonResult  importExcel(@RequestParam("file") MultipartFile file) throws  Exception{
            InputStream inputStream = file.getInputStream();
            projectClassifyService.importExcelData(inputStream);
         return  success("数据导入成功");
    }



}
