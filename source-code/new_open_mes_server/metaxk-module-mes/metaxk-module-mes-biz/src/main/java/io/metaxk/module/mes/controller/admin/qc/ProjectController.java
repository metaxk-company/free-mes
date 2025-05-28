package io.metaxk.module.mes.controller.admin.qc;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.InspectDeviceVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Device;
import io.metaxk.module.mes.dal.dataobject.qc.Project;
import io.metaxk.module.mes.dal.dataobject.qc.ProjectDevice;
import io.metaxk.module.mes.service.qc.DeviceService;
import io.metaxk.module.mes.service.qc.ProjectDeviceService;
import io.metaxk.module.mes.service.qc.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectExcelVo;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.PROJECT_NAME_EXIST;



/**
 * @author 万界星空
 * @time 2023/7/6 13:22
 */
@Tag(name = "管理后台 - 检验项")
@RestController
@RequestMapping("/mes/qc/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    @Resource
    private ProjectDeviceService projectDeviceService;



    @GetMapping("/list")
    @Operation(summary = "检测项列表")
    public CommonResult<PageResult<Project>> list(ProjectQueryVo inspectProjectPageReqVO) {
        PageResult<Project> pageResult = projectService.findPage(inspectProjectPageReqVO);
        //获取分页中的集合
        List<Project> projectList = pageResult.getList();
        Map<String, List<String>> deviceNameMap = new HashMap<>();
        for (Project project : projectList) {
            List<ProjectDevice> projectDeviceList = projectDeviceService.findProjectDeviceByCode(project.getProjectCode());
            List<String> deviceNames = new ArrayList<>();
            for (ProjectDevice projectDevice : projectDeviceList) {
                deviceNames.add(projectDevice.getDeviceName());
            }
            deviceNameMap.put(project.getProjectCode(), deviceNames);
        }
        for (Project project : projectList) {
            StringBuilder deviceNamesString = new StringBuilder();
            List<String> deviceNames = deviceNameMap.get(project.getProjectCode());
            if (deviceNames != null) {
                for (String deviceName : deviceNames) {
                    deviceNamesString.append(deviceName).append(",");
                }
            }
            if (deviceNamesString.length() > 0) {
                deviceNamesString.deleteCharAt(deviceNamesString.length() - 1);
            }
            project.setInspectDevice(deviceNamesString.toString());
        }
        return success(pageResult);
    }








    @PostMapping("/save")
    @Operation(summary = "新增检测项")
    public CommonResult<Integer> save(@RequestBody Project project){
        if(projectService.findInspectProjectByName(project.getProjectName()) != null){
            throw exception(PROJECT_NAME_EXIST);
        }
        List<InspectDeviceVo> inspectDeviceList = project.getInspectDeviceList();
        for(InspectDeviceVo inspectDeviceVo:inspectDeviceList){
            ProjectDevice projectDevice = new ProjectDevice();
            projectDevice
                        .setProjectCode(project.getProjectCode())
                        .setDeviceCode(inspectDeviceVo.getDeviceCode())
                        .setDeviceName(inspectDeviceVo.getDeviceName())
                        .setAgreement(inspectDeviceVo.getAgreement());
            projectDeviceService.saveProjectDevice(projectDevice);
        }
        return success( projectService.saveInspectProject(project)).setMsg("新增成功");
    }





    @DeleteMapping("/batch")
    @Operation(summary = "删除检测项")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            Project project = projectService.findInspectProjectById(id);
            //根据检查项编号删除检测项目中的器具
            projectDeviceService.removeProjectDeviceByCode(project.getProjectCode());
        }
        return success( projectService.removeInspectProject(ids)).setMsg("删除成功");
    }




    @DeleteMapping("/remove/{code}/{projectCode}")
    @Operation(summary = "根据器具编号移除检查项中的器具")
    public CommonResult<Integer> removeProjectDevice(@PathVariable String code,@PathVariable String projectCode){
        return success(projectDeviceService.removeDeviceByDeviceCode(code,projectCode)).setMsg("移除成功");
    }





    @GetMapping("/find/{id}")
    @Operation(summary = "查询检查项详情")
    public CommonResult<Project> batch(@PathVariable Long id){
        Project project = projectService.findInspectProjectById(id);
        String projectCode = project.getProjectCode();
        List<ProjectDevice> projectDeviceList = projectDeviceService.findProjectDeviceByCode(projectCode);
        ArrayList<InspectDeviceVo> arrayList = new ArrayList<>();
        for(ProjectDevice projectDevice:projectDeviceList){
            InspectDeviceVo inspectDeviceVo = new InspectDeviceVo();
            inspectDeviceVo.setDeviceCode(projectDevice.getDeviceCode());
            inspectDeviceVo.setDeviceName(projectDevice.getDeviceName());
            inspectDeviceVo.setAgreement(projectDevice.getAgreement());
            arrayList.add(inspectDeviceVo);
        }
        project.setInspectDeviceList(arrayList);
        return success(project);
    }





    @PutMapping("/update")
    @Operation(summary = "修改检测项")
    public CommonResult<Integer> update(@RequestBody Project inspectProject){
        return success( projectService.updateInspectProject(inspectProject)).setMsg("修改成功");
    }





    @GetMapping("/templateData")
    @Operation(summary = "检测项下载模板")
    public void exportTemplate(HttpServletResponse response) throws Exception{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("检测项模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            List<ProjectExcelVo> templateData = new ArrayList<>();
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ProjectExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("检测项模板")
                    .doWrite(templateData);
    }



    @PostMapping("/importExcel")
    @Operation(summary = "导入检测项")
    public CommonResult<Object>  importExcel(@RequestParam("file") MultipartFile file) throws Exception{
            InputStream inputStream = file.getInputStream();
        projectService.imporExceltData(inputStream);
        return  success(null).setMsg("导入成功");
    }




}
