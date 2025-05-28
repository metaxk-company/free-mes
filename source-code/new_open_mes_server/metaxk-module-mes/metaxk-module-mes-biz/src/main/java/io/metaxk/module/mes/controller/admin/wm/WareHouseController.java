package io.metaxk.module.mes.controller.admin.wm;


import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouse;
import io.metaxk.module.mes.service.wm.WareHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.WAREHOUSE_EXIST;


/**
 * @author 万界星空
 * @time 2023/7/13 15:33
 */
@Tag(name = "管理后台 - 仓库")
@RestController
@RequestMapping("/mes/wm/warehouse")
public class WareHouseController {

    @Resource
    private WareHouseService warehouseService;



    @GetMapping("/list")
    @Operation(summary = "仓库列表")
    public CommonResult<PageResult<WareHouse>>  list(WareHouseQueryVo wareHouseQueryVo){
        return success(warehouseService.findPage(wareHouseQueryVo));
    }



    @PostMapping("/save")
    @Operation(summary = "新增仓库")
    public CommonResult<Integer> save(@RequestBody WareHouse wareHouse){
        if(warehouseService.findWareHouseByName(wareHouse.getWarehouseName()) != null){
            throw exception(WAREHOUSE_EXIST);
        }
        return success(warehouseService.saveWareHouse(wareHouse)).setMsg("新增成功");
    }


    @DeleteMapping("/batch")
    @Operation(summary = "删除仓库")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(warehouseService.removeWareHouseByIds(ids)).setMsg("删除成功");
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "仓库详情")
    public CommonResult<WareHouse> findWareHouseById(@PathVariable Long id){
        return success(warehouseService.findWareHouseById(id));
    }


    @PutMapping("/update")
    @Operation(summary = "修改仓库")
    public CommonResult<Integer> update(@RequestBody WareHouse wareHouse){
        return success(warehouseService.updateWareHouse(wareHouse)).setMsg("修改成功");
    }


    @GetMapping("/listAll")
    @Operation(summary = "所有仓库列表")
    public CommonResult<List<WareHouse>>  listAll(){
        return success(warehouseService.listAll());
    }






}
