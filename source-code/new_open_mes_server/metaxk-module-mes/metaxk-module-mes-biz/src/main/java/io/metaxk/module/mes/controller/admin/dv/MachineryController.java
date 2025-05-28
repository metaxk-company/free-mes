package io.metaxk.module.mes.controller.admin.dv;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryExcelVo;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryType;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.service.dv.MachineryService;
import io.metaxk.module.mes.service.dv.MachineryTypeService;
import io.metaxk.module.mes.service.md.WorkShopService;
import io.metaxk.module.mes.service.md.WorkStationMachineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.*;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.io.IOException;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import org.springframework.web.multipart.MultipartFile;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 设备")
@RestController
@RequestMapping("/mes/dv/machinery")
public class MachineryController {

    @Resource
    private MachineryService machineryService;

    @Resource
    private MachineryTypeService machineryTypeService;

    @Resource
    private WorkShopService workshopService;

    @Resource
    private WorkStationMachineService stationService;




    @GetMapping("/findStatus")
    public CommonResult<HashMap<String,Object>>  findMachineryStatus(){
      List<Machinery>  list =  machineryService.findMachineryStatus();
        ArrayList<String> arrayList = new ArrayList<>();
        HashSet<String> statusSet = new HashSet<>();
        for(Machinery machineryList:list){
            String status = machineryList.getStatus();
            if (!statusSet.contains(status)) {
                arrayList.add(status);
                statusSet.add(status);
            }
        }
        Long sumMachinery =  machineryService.findOnLineMachinery();
        Long offlineMachinery =  machineryService.findOfflineMachinery();
        Long faultMachinery =  machineryService.findFaultMachinery();
        ArrayList<Long> countMachinery = new ArrayList<>();
        countMachinery.add(sumMachinery);
        countMachinery.add(offlineMachinery);
        countMachinery.add(faultMachinery);
        HashMap<String, Object> map = new HashMap<>(200);
        map.put("name",arrayList);
        map.put("numData",countMachinery);
        return  success(map);
    }





    @GetMapping("/list")
    @Operation(summary = "设备条件分页查询")
    public CommonResult<PageResult<Machinery>>  getMachineryPage(MachineryQueryVo page) {
     PageResult<Machinery> pageResult = machineryService.getMachineryPage(page);
        return success(pageResult);
    }





    @PostMapping("/save")
    @Operation(summary = "新增设备")
    @PreAuthorize("@ss.hasPermission('dv:machinery:save')")
    public CommonResult<Integer> save( @RequestBody Machinery machinery) {
        return success(machineryService.createMachinery(machinery)).setMsg("新增成功");
    }







    @PutMapping("/update")
    @Operation(summary = "修改设备")
    @PreAuthorize("@ss.hasPermission('dv:machinery:update')")
    public CommonResult<Integer>  update( @RequestBody Machinery machinery) {
        return success( machineryService.updateMachinery(machinery)).setMsg("修改成功");
    }








    @DeleteMapping("/batch")
    @Operation(summary = "删除设备")
    @PreAuthorize("@ss.hasPermission('dv:machinery:delete')")
    public  CommonResult<Integer>   batch(@RequestBody List<Long> ids) {
        List<Machinery> machineryList =   machineryService.findMachineryByIds(ids);
        for(Machinery machinery :machineryList){
            stationService.removeByMachineryCode(machinery.getMachineryCode());
        }
        return success(machineryService.deleteMachinery(ids)).setMsg("删除成功");
    }






    @GetMapping("/get/{id}")
    @Operation(summary = "设备详情")
    @PreAuthorize("@ss.hasPermission('dv:machinery:get')")
    public CommonResult<Machinery> getMachinery(@PathVariable  Long id) {
        return success(machineryService.getMachinery(id));
    }





    @GetMapping("/export")
    @Operation(summary = "设备导出")
    @PreAuthorize("@ss.hasPermission('dv:machinery:export')")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("设备台账", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), MachineryExcelVo.class).registerWriteHandler(styleStrategy).sheet("设备台账").doWrite(machineryService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }





    @GetMapping("/templateData")
    @Operation(summary = "设备台账导出模板")
    public void exportTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("设备台账导出模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            List<MachineryExcelVo> templateData = new ArrayList<>();
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), MachineryExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("设备台账导出模板")
                    .doWrite(templateData);
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }







    @PostMapping("/import")
    @Operation(summary = "导入设备台账")
    public void   importItem( MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            List<MachineryExcelVo> itemList = EasyExcel.read(inputStream).head(MachineryExcelVo.class).sheet().doReadSync();
            for(MachineryExcelVo list:itemList){
                String machineryCode = list.getMachineryCode();
                String machineryName = list.getMachineryName();
                String remark = list.getRemark();
                String machineryBrand = list.getMachineryBrand();
                String machineryTypeCode = list.getMachineryTypeCode();
                String machineryTypeName = list.getMachineryTypeName();
                String workshopCode = list.getWorkshopCode();
                String workshopName = list.getWorkshopName();
                String status = list.getStatus();
                String location = list.getLocation();

                System.out.println("==========1==========="+ machineryCode);
                System.out.println("==========2==========="+ machineryName );
                System.out.println("==========3===========" + remark);
                System.out.println("==========4===========" + machineryBrand);
                System.out.println("==========5===========" + machineryTypeCode);
                System.out.println("==========6===========" + machineryTypeName);
                System.out.println("==========7===========" + workshopCode);
                System.out.println("==========8===========" + workshopName);
                System.out.println("==========9===========" + status);
                System.out.println("==========10===========" + location);


            Machinery machinery =    machineryService.findByCodeAndName(machineryCode,machineryName);
            if(machinery == null){
                Machinery machinery1 = new Machinery();
                machinery1
                .setMachineryCode(machineryCode)
                .setMachineryName(machineryName)
                .setRemark(remark)
                .setMachineryBrand(machineryBrand)
                .setStatus(status)
                .setLocation(location)
                .setMachineryTypeId(246L);
                machineryService.createMachinery(machinery1);
            }
           MachineryType machineryType = machineryTypeService.findByCodeAndName(machineryTypeCode,machineryTypeName);
            if(machineryType == null){
                MachineryType machineryType1 = new MachineryType();
                machineryType1.setMachineryTypeCode(machineryTypeCode)
                        .setMachineryTypeName(machineryTypeName)
                        .setAncestors(0+","+200)
                        .setEnableFlag("Y");
                machineryTypeService.createMachineryType(machineryType1);
            }
             WorkShop workshop = workshopService.findByCodeAndName(workshopCode,workshopName);
            if(workshop == null){
                WorkShop workshop1 = new WorkShop();
                workshop1.setWorkshopCode(workshopCode)
                        .setWorkshopName(workshopName)
                        .setEnableFlag("Y");
                workshopService.insertWorkshopDO(workshop1);
            }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
