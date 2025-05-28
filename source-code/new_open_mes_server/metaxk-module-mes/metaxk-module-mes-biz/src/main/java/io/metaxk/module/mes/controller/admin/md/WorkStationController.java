package io.metaxk.module.mes.controller.admin.md;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.md.vo.WorkstationExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.WorkstationQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.service.md.*;
import io.metaxk.module.mes.service.pro.ProcessService;
import io.metaxk.module.mes.service.wm.WareHouseLocationService;
import io.metaxk.module.mes.service.wm.WareHouseAreaService;
import io.metaxk.module.mes.service.wm.WareHouseService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 工作站")
@RestController
@RequestMapping("/mes/md/workstation")
public class WorkStationController {

    @Resource
    private WorkStationService workstationService;

    @Resource
    private WorkStationMachineService workstationMachineService;

    @Resource
    private ProcessService processService;

    @Resource
    private WorkShopService workshopService;

    @Resource
    private WareHouseService warehouseService;

    @Resource
    private WareHouseLocationService storageAreaService;

    @Resource
    private WareHouseAreaService storageLocationService;


    @GetMapping("/list")
    @Operation(summary = "工作站条件分页")
    public CommonResult<PageResult<WorkStation>> getWorkstationPage(WorkstationQueryVo pageVO) {
        PageResult<WorkStation> pageResult = workstationService.getWorkstationPage(pageVO);
        return success(pageResult);
    }




    @DeleteMapping("/batch")
    @Operation(summary = "删除工作站")
    public CommonResult<Boolean>  batch(@RequestBody List<Long> workstationIds) {
        for (Long workstationId: workstationIds
        ) {
             workstationMachineService.deleteByWorkstationId(workstationId);
        }
        return success( workstationService.removeBatchByIds(workstationIds)).setMsg("删除成功");
    }


    @PostMapping("/save")
    @Operation(summary = "新增工作站")
    public CommonResult<Integer>  save( @RequestBody WorkStation workstation) {
        if(UserConstants.NOT_UNIQUE.equals(workstationService.checkWorkStationCodeUnique(workstation))){
            throw exception(WORKSTATION_CODE_EXIST);
        }
        if(UserConstants.NOT_UNIQUE.equals(workstationService.checkWorkStationNameUnique(workstation))){
            throw exception(WORKSTATION_NAME_EXIST);
        }
        return success(workstationService.insert(workstation)).setMsg("添加成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改工作站")
    public CommonResult<Integer> update( @RequestBody WorkStation createReqVO) {
        return success(workstationService.updateWorkstations(createReqVO)).setMsg("修改成功");
    }






    @GetMapping("/get/{id}")
    @Operation(summary = "工作站详情")
    public CommonResult<WorkStation>  getWorkstationById(@PathVariable Long id) {
        return success(workstationService.getWorkstation(id));
    }



    @GetMapping("/export")
    @Operation(summary = "工作站导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("工作站管理", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), WorkstationExcelVo.class).registerWriteHandler(styleStrategy).sheet("工作站数据").doWrite(workstationService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }







}
