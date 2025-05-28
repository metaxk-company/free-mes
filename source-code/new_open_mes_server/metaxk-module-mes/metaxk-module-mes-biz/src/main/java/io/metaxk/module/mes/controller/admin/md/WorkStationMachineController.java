package io.metaxk.module.mes.controller.admin.md;

import io.metaxk.module.mes.controller.admin.md.vo.*;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.dal.dataobject.md.WorkStationMachine;
import io.metaxk.module.mes.service.md.WorkStationMachineService;
import io.metaxk.module.mes.service.md.WorkStationService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.MACHINEWORKSTATION;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 设备资源")
@RestController
@RequestMapping("/mes/md/workstationMachine")
public class WorkStationMachineController {

    @Resource
    private WorkStationMachineService workstationMachineService;

    @Resource
    private WorkStationService workstationService;


    @GetMapping("/list")
    @Operation(summary = "获得设备资源分页")
    @PreAuthorize("@ss.hasPermission('md:workstation:machine:query')")
    public CommonResult<PageResult<WorkStationMachine>>  getWorkstationMachinePage(WorkstationMachineQueryVo workstationMachine) {
        PageResult<WorkStationMachine> pageResult = workstationMachineService.getWorkstationMachinePage(workstationMachine);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增设备资源")
    @PreAuthorize("@ss.hasPermission('md:workstation:machine:save')")
    public CommonResult<Integer> save( @RequestBody WorkStationMachine workstationMachine) {
        WorkStationMachine machine = workstationMachineService.checkMachineryExists(workstationMachine);
        if(StringUtils.isNotNull(machine)){
            WorkStation workstation = workstationService.selectMdWorkstationByWorkstationId(machine.getWorkstationId());
            throw  exception(MACHINEWORKSTATION);
        }
        int save = 0;
        try {
            save  = workstationMachineService.save(workstationMachine);
        }catch(DuplicateKeyException e) {
        }
        return success(save).setMsg("新增成功");
    }





    @DeleteMapping("/batch")
    @Operation(summary = "删除设备资源")
    @PreAuthorize("@ss.hasPermission('md:workstation:machine:delete')")
    public CommonResult<Integer>  deleteWorkstationMachine(@RequestBody List<Long>  ids) {
        return success( workstationMachineService.deleteWorkstationMachine(ids));
    }



    @GetMapping("/get/{id}")
    @Operation(summary = "设备资源详情")
    @PreAuthorize("@ss.hasPermission('md:workstation-machine:get')")
    public CommonResult<WorkStationMachine>  getWorkstationMachine(@PathVariable Long id) {
        return success(workstationMachineService.getWorkstationMachine(id));
    }



}
