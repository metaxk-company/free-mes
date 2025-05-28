package io.metaxk.module.mes.controller.admin.wm;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseAreaQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseArea;
import io.metaxk.module.mes.service.wm.WareHouseAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.AREA_EXIST;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.WAREHOUSE_EXIST;


/**
 * @author 万界星空
 * @time 2023/7/13 15:33
 */
@Tag(name = "管理后台 - 库区")
@RestController
@RequestMapping("/mes/wm/warehouse/area")
public class WareHouseAreaController {

    @Resource
    private WareHouseAreaService wareHouseAreaService;




    @GetMapping("/list")
    @Operation(summary = "库区列表")
    public CommonResult<PageResult<WareHouseArea>>list(WareHouseAreaQueryVo wareHouseAreaQueryVo){
        return success(wareHouseAreaService.findPage(wareHouseAreaQueryVo));
    }



    @PostMapping("/save")
    @Operation(summary = "新增库区")
     public CommonResult<Integer> save(@RequestBody WareHouseArea wareHouseArea){
        if(wareHouseAreaService.findWareHouseAreaByName(wareHouseArea.getAreaName()) != null){
            throw exception(AREA_EXIST);
        }
         return success(wareHouseAreaService.saveWareHouseArea(wareHouseArea)).setMsg("新增成功");
     }



    @DeleteMapping("/batch")
    @Operation(summary = "删除库区")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(wareHouseAreaService.removeWareHouseAreaByIds(ids)).setMsg("删除成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改库区")
    public CommonResult<Integer> update(@RequestBody WareHouseArea wareHouseArea){
        return success(wareHouseAreaService.updateWareHouseArea(wareHouseArea)).setMsg("修改成功");
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "库区详情")
    public CommonResult<WareHouseArea> findWareHouseById(@PathVariable Long id){
        return success(wareHouseAreaService.findWareHouseAreaById(id));
    }


}
