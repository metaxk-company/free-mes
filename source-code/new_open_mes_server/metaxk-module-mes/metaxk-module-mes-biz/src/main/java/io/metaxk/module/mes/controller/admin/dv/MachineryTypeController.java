package io.metaxk.module.mes.controller.admin.dv;

import io.metaxk.module.mes.common.TreeSelect;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryType;
import io.metaxk.module.mes.service.dv.MachineryService;
import io.metaxk.module.mes.service.dv.MachineryTypeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.MACHINERYTYPEHASMACHINERT;


/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 设备类型")
@RestController
@RequestMapping("/mes/dv/machineryType")
public class MachineryTypeController {

    @Resource
    private MachineryTypeService machineryTypeService;


    @Resource
    private MachineryService machineryService;




    @GetMapping("/listTree")
    @Operation(summary = "设备分类树形显示")
    public CommonResult<List<TreeSelect>>  selectMachineryTreeList(MachineryType machineryTypeDO){
        List<MachineryType> machineryTypeList = machineryTypeService.findItemTypeList(machineryTypeDO);
        List<TreeSelect> treeSelects =   machineryTypeService.buildTreeSelect(machineryTypeList);
        return success(treeSelects);
    }


    @GetMapping("/list")
    @Operation(summary = "设备类型条件分页查询")
    @PreAuthorize("@ss.hasPermission('dv:machinery:type:list')")
    public CommonResult<PageResult<MachineryType>>  getMachineryTypePage( MachineryTypeQueryVo pageVO) {
        PageResult<MachineryType> pageResult = machineryTypeService.getMachineryTypePage(pageVO);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增设备类型")
    @PreAuthorize("@ss.hasPermission('dv:machinery:type:save')")
    public CommonResult<Integer> save( @RequestBody MachineryType machineryTypeCreateReqVO) {
        return success(machineryTypeService.createMachineryType(machineryTypeCreateReqVO)).setMsg("新增成功");
    }




    @GetMapping("/get/{id}")
    @Operation(summary = "设备类型详情")
    @PreAuthorize("@ss.hasPermission('dv:machinery:type:get')")
    public CommonResult<MachineryType>  getMachineryTypeById(@PathVariable Long id) {
        return success(machineryTypeService.getMachineryType(id));
    }




    @PutMapping("/update")
    @Operation(summary = "修改设备类型")
    @PreAuthorize("@ss.hasPermission('dv:machinery:type:update')")
    public CommonResult<Integer>  updateMachineryType(@RequestBody MachineryType machineryTypeDO){
        return success(machineryTypeService.updateMachineryType(machineryTypeDO)).setMsg("修改成功");
    }




    @DeleteMapping("/{machineryTypeIds}")
    public CommonResult<Integer> remove(@PathVariable Long machineryTypeIds)
    {
     List<Machinery>  machinery =  machineryService.selectByMachineryTypeId(machineryTypeIds);
     if(machinery.size()>0){
         throw exception(MACHINERYTYPEHASMACHINERT);
     }
        return success(machineryTypeService.deleteDvMachineryTypeByMachineryTypeId(machineryTypeIds)).setMsg("删除成功");
    }


}
