package io.metaxk.module.mes.controller.admin.qc;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.WorkShopController;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceQueryVo;
import io.metaxk.module.mes.dal.dataobject.dept.SysDept;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.dal.dataobject.qc.Device;
import io.metaxk.module.mes.service.dept.SysDeptService;
import io.metaxk.module.mes.service.md.WorkShopService;
import io.metaxk.module.mes.service.pro.ProcessService;
import io.metaxk.module.mes.service.qc.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.DEVICE_NAME_EXIST;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.INSPECT_WAY_NAME_EXIST;


/**
 * @author 万界星空
 * @time 2023/7/6 11:18
 */
@Tag(name = "管理后台 - 检测器具")
@RestController
@RequestMapping("/mes/qc/device")
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private ProcessService processService;

    @Resource
    private WorkShopService  workShopService;

    @Resource
    private SysDeptService sysDeptService;


    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody Device device){
        if(deviceService.findDeviceByName(device.getDeviceName()) != null){
            throw exception( DEVICE_NAME_EXIST);
        }
        List<String> processList = device.getProcessList();
        StringBuilder processCodes = new StringBuilder();
        for(String process:processList){
            processCodes.append(process).append(",");
        }
        if (processCodes.length() > 0) {
            processCodes.deleteCharAt(processCodes.length() - 1);
        }
        device.setProcess(String.valueOf(processCodes));
        return success( deviceService.saveInspectDevice(device)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success( deviceService.removeInspectDevice(ids)).setMsg("删除成功");
    }






    @GetMapping("/find/{id}")
    public CommonResult<Device> findInspectDevice(@PathVariable Long id){
        Device device = deviceService.findDeviceById(id);
        SysDept dept = sysDeptService.findDeptById(device.getDepartment());
        device.setDepartment(dept.getName());
        //获取工序编号，将工序转为数组的形式
        String process = device.getProcess();
        String[] processArray = process.split(",");
        StringBuilder processNames = new StringBuilder();
        List<String> processList = new ArrayList<>();
        for (String processCode : processArray) {
            Process processDo = processService.findProcessByCode(processCode);
            if(processDo != null) {
                processNames.append(processDo.getProcessName()).append(",");
                processList.add(processDo.getProcessName());
            }
        }
        if (processNames.length() > 0) {
            processNames.deleteCharAt(processNames.length() - 1);
        }
        device.setProcess(processNames.toString());
        device.setProcessList(processList);
        return success(device );
    }






    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody Device device){
        String department = device.getDepartment();
        if (isChineseString(department)){
            SysDept sysDept = sysDeptService.findDeptByName(device.getDepartment());
            device.setDepartment(sysDept.getId().toString());
        }
        if (isNumericString(department)){
            SysDept sysDept = sysDeptService.findDeptById(device.getDepartment());
            device.setDepartment(sysDept.getId().toString());
        }
         StringBuilder processCodes = new StringBuilder();
         List<String> processList = device.getProcessList();
          for (String processName : processList) {
              if (processName.startsWith("PROCESS")) {
                  Process process =  processService.findProcessByCode(processName);
                  processCodes.append(process.getProcessCode()).append(",");
              } else {
                  Process process = processService.findProcessByName(processName);
                  processCodes.append(process.getProcessCode()).append(",");
              }
        }
        device.setProcess(processCodes.toString());
        return success( deviceService.updateInspectDevice(device)).setMsg("修改成功");
    }


    private boolean isChineseString(String str) {
        // 使用正则表达式判断是否包含中文字符
        return str.matches(".*[\\u4E00-\\u9FA5]+.*");
    }

    private boolean isNumericString(String str) {
        // 使用正则表达式判断是否只包含数字字符
        return str.matches("\\d+");
    }




    @GetMapping("/list")
    public  CommonResult<PageResult<Device>> list(DeviceQueryVo inspectDevicePageReqVO){
        PageResult<Device> pageResult = deviceService.findPage(inspectDevicePageReqVO);
        List<Device> list = pageResult.getList();
        for(Device device:list){
            WorkShop shop = workShopService.findWorkShopByCode(device.getWorkshop());
            device.setWorkshop(shop.getWorkshopName());
            SysDept sysDept =  sysDeptService.findDeptById(device.getDepartment());
            device.setDepartment(sysDept.getName());
            String process = device.getProcess();
            String[] processArray = process.split(",");
            StringBuilder processNames = new StringBuilder();
            for (String processCode : processArray) {
                Process processDo = processService.findProcessByCode(processCode);
                if(processDo != null) {
                    processNames.append(processDo.getProcessName()).append(",");
                }
            }
            if (processNames.length() > 0) {
                processNames.deleteCharAt(processNames.length() - 1);
            }
            device.setProcess(processNames.toString());
        }
        return  success(pageResult);
    }


    @GetMapping("/listAll")
    public CommonResult listAll(DeviceQueryVo inspectDevicePageReqVO){
        return success(deviceService.listAll());
    }


    @GetMapping("/templateData")
    @Operation(summary = "检测器具模板")
    public void exportTemplate(HttpServletResponse response) throws Exception{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("检测器具模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            List<DeviceExcelVo> templateData = new ArrayList<>();
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), DeviceExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("检测器具模板")
                    .doWrite(templateData);
    }



    @PostMapping("/importExcel")
    public CommonResult  importExcel(@RequestParam("file") MultipartFile file) throws  Exception{
            InputStream inputStream = file.getInputStream();
             deviceService.importExcelData(inputStream);
            return  success("数据导入成功");
    }




}
