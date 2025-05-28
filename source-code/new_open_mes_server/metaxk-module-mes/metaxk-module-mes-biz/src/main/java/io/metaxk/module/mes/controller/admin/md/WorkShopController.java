package io.metaxk.module.mes.controller.admin.md;

import io.metaxk.module.mes.controller.admin.md.vo.WorkShopQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.service.md.WorkShopService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 车间管理")
@RestController
@RequestMapping("/mes/md/workshop")
public class WorkShopController {


    @Resource
    private WorkShopService workshopService;



    @GetMapping("/availableList")
    @Operation(summary = "查询可用车间")
    public CommonResult<List<WorkShop>>  findAvailableList() {
        List<WorkShop> list = workshopService.findavailableList();
        return success(list);
    }





    @GetMapping("/list")
    @Operation(summary = "车间条件分页查询")
    @PreAuthorize("@ss.hasPermission('md:workshop:list')")
    public CommonResult<PageResult<WorkShop>>  findWorkshopPage(WorkShopQueryVo workshopPage) {
        PageResult<WorkShop> pageResult = workshopService.listWorkshopPage(workshopPage);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增车间")
    @PreAuthorize("@ss.hasPermission('md:workshop:save')")
    public CommonResult<Integer>  save( @RequestBody WorkShop workShop) {
        if(workshopService.findWorkShopByCode(workShop.getWorkshopCode()) != null){
            throw exception(SHOP_CODE_EXIST);
        }
        if(workshopService.findWorkShopByName(workShop.getWorkshopName()) != null){
            throw exception(SHOP_NAME_EXIST);
        }
        return success(workshopService.insertWorkshopDO(workShop)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    @Operation(summary = "删除车间")
    @PreAuthorize("@ss.hasPermission('md:workshop:delete')")
    public CommonResult<Integer> batch(@RequestBody  List<Long> ids) {
        return success( workshopService.removeWorkshop(ids)).setMsg("删除成功");
    }



    @GetMapping("/get/{id}")
    @Operation(summary = "车间详情")
    @PreAuthorize("@ss.hasPermission('md:workshop:get')")
    public CommonResult<WorkShop> getWorkshop(@PathVariable Long id) {
        return success(workshopService.findWorkshopById(id));
    }




    @PutMapping("/update")
    @Operation(summary = "修改车间")
    @PreAuthorize("@ss.hasPermission('md:workshop:update')")
    public CommonResult<Integer> update( @RequestBody WorkShop workShop) {
        return success(workshopService.updateWorkshops(workShop)).setMsg("修改成功");
    }




    @Operation(summary = "获取所有可用车间")
    @GetMapping("/listAll")
    public CommonResult<List<WorkShop>> listAll(){
        WorkShop mdWorkshop = new WorkShop();
        mdWorkshop.setEnableFlag("Y");
        return success(workshopService.selectMdWorkshopList(mdWorkshop));
    }

}
