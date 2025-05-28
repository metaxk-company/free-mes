package io.metaxk.module.mes.controller.admin.cla;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamTypeExcelVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassTeamType;
import io.metaxk.module.mes.service.cla.ClassTeamTypeService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.CAL_TEAM_TYPE_NAME_EXIST;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;



/**
 * @author 万界星空
 * @time 2023/6/25 9:52
 */
@Tag(name = "管理后台 - 班组类型")
@RestController
@RequestMapping("/mes/cal/team/type")
public class ClassTeamTypeController {

    @Resource
    private ClassTeamTypeService calTeamTypeService;

    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody ClassTeamType calTeamType){
        if(calTeamTypeService.findCalTeamTypeByName(calTeamType.getTypeName()) != null){
            throw exception(CAL_TEAM_TYPE_NAME_EXIST);
        }
        return success(calTeamTypeService.saveCalTeamType(calTeamType)).setMsg("新增成功");
    }


    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(calTeamTypeService.removeCalTeamTypeByIds(ids)).setMsg("删除成功");
    }

    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody ClassTeamType calTeamType){
        return success(calTeamTypeService.updateCalTeamType(calTeamType)).setMsg("修改成功");
    }


    @GetMapping("/list")
    public  CommonResult<PageResult<ClassTeamType>> list(ClaTeamTypeQueryVo calTeamTypePageReqVO){
        PageResult<ClassTeamType> pageResult = calTeamTypeService.calTeamTypePage(calTeamTypePageReqVO);
        return  success(pageResult);
    }


    @GetMapping("/get/{id}")
    public CommonResult<ClassTeamType> getCalTeamTypeById(@PathVariable Long id){
        return success(calTeamTypeService.findCalTeamTypeById(id));
    }





    @GetMapping("/export")
    @Operation(summary = "班组类型导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("班组类型", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ClaTeamTypeExcelVo.class).registerWriteHandler(styleStrategy).sheet("班组类型").doWrite(calTeamTypeService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }









}
