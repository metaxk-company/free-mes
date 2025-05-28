package io.metaxk.module.mes.controller.admin.wm;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseLocationQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseLocation;
import io.metaxk.module.mes.service.wm.WareHouseLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.LOCATION_EXIST;


/**
 * @author 万界星空
 * @time 2023/7/13 15:33
 */
@Tag(name = "管理后台 - 库位")
@RestController
@RequestMapping("/mes/wm/warehouse/location")
public class WareHouseLocationController {


    @Resource
    private WareHouseLocationService wareHouseLocationService;



    @GetMapping("/list")
    @Operation(summary = "库位列表")
    public CommonResult<PageResult<WareHouseLocation>> list(WareHouseLocationQueryVo wareHouseLocationQueryVo){
        return success(wareHouseLocationService.findPage(wareHouseLocationQueryVo));
    }



    @PostMapping("/save")
    @Operation(summary = "新增库位")
    public CommonResult<Integer> save(@RequestBody WareHouseLocation wareHouseLocation){
        if(wareHouseLocationService.findWareHouseLocationByName(wareHouseLocation.getLocationName()) != null){
            throw exception(LOCATION_EXIST);
        }
        return success(wareHouseLocationService.saveWareHouseLocation(wareHouseLocation)).setMsg("新增成功");
    }


    @DeleteMapping("/batch")
    @Operation(summary = "删除库位")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(wareHouseLocationService.removeWareHouseLocationByIds(ids)).setMsg("删除成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改库位")
    public CommonResult<Integer> update(@RequestBody WareHouseLocation wareHouseLocation){
        return success(wareHouseLocationService.updateWareHouseLocation(wareHouseLocation)).setMsg("修改成功");
    }



    @GetMapping("/find/{id}")
    @Operation(summary = "库位详情")
    public CommonResult<WareHouseLocation> findWareHouseById(@PathVariable Long id){
        return success(wareHouseLocationService.findWareHouseLocationById(id));
    }


}
