package io.metaxk.module.mes.controller.admin.pro;

import io.metaxk.module.mes.controller.admin.pro.vo.WorkorderBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrderBom;
import io.metaxk.module.mes.service.pro.WorkOrderBomService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.pojo.CommonResult.success;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 生产工单BOM组成")
@RestController
@RequestMapping("/mes/pro/workorder/bom")
public class WorkOrderBomController {

    @Resource
    private WorkOrderBomService workorderBomService;



    @GetMapping("/list")
    @Operation(summary = "获得生产工单BOM组成分页")
    public CommonResult<PageResult<WorkOrderBom>>  getWorkOrderBomPage( WorkorderBomQueryVo pageVO) {
       PageResult<WorkOrderBom> pageResult = workorderBomService.getWorkorderBomPage(pageVO);
        return success(pageResult);
    }




    @PostMapping("/create")
    @Operation(summary = "创建生产工单BOM组成")
    @PreAuthorize("@ss.hasPermission('pro:workorder-bom:create')")
    public CommonResult<Integer> createWorkorderBom( @RequestBody WorkOrderBom createReqVO) {
        return success(workorderBomService.createWorkorderBom(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产工单BOM组成")
    @PreAuthorize("@ss.hasPermission('pro:workorder-bom:update')")
    public CommonResult<Boolean> updateWorkorderBom( @RequestBody WorkOrderBom updateReqVO) {
        workorderBomService.updateWorkorderBom(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产工单BOM组成")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:workorder-bom:delete')")
    public CommonResult<Boolean> deleteWorkOrderBom(@RequestParam("id") Long id) {
        workorderBomService.deleteWorkorderBom(id);
        return success(true);
    }


}
