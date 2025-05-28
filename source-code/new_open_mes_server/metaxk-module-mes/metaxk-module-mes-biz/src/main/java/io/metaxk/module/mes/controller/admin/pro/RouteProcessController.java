package io.metaxk.module.mes.controller.admin.pro;

import cn.hutool.core.collection.CollUtil;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProcessQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProcess;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProduct;
import io.metaxk.module.mes.service.pro.ProcessService;
import io.metaxk.module.mes.service.pro.RouteProcessService;
import io.metaxk.module.mes.service.pro.RouteProductService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
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
@Tag(name = "管理后台 - 工艺工序")
@RestController
@RequestMapping("/mes/pro/routeProcess")
public class RouteProcessController {

    @Resource
    private RouteProcessService proRouteProcessService;

    @Resource
    private ProcessService processService;

    @Resource
    private RouteProductService routeProductService;


    @GetMapping("/list")
    @Operation(summary = "工艺工序条件分页查询")
    @PreAuthorize("@ss.hasPermission('pro:route:process:list')")
    public CommonResult<PageResult<RouteProcess>>  getRouteProcessPage(@Valid RouteProcessQueryVo pageVO) {
        PageResult<RouteProcess> pageResult = proRouteProcessService.getRouteProcessPage(pageVO);
        return success(pageResult);
    }





    @GetMapping("/listProductProcess/{productId}")
    @Operation(summary = "查询指定产品的工艺组成")
    public CommonResult<List<RouteProcess>> listProductProcess(@PathVariable("productId") Long productId){
        RouteProduct proRouteProduct = new RouteProduct();
        proRouteProduct.setItemId(productId);
        List<RouteProduct> products = routeProductService.selectProRouteProductList(proRouteProduct);
        if(CollUtil.isNotEmpty(products)){
            RouteProduct product = products.get(0);
            RouteProcess param = new RouteProcess();
            param.setRouteId(product.getRouteId());
            return success(proRouteProcessService.selectProRouteProcessList(param));
        }else {
            throw  exception(NO_ROUTER);
        }
    }






    @PostMapping("/save")
    @Operation(summary = "新增工艺工序")
    @PreAuthorize("@ss.hasPermission('pro:route:process:save')")
    public CommonResult<Integer>  createRouteProcess(@RequestBody RouteProcess createReqVO) {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkOrderNumExists(createReqVO))){
            throw exception(ROUTE_PROCESS_CODE_EXIST);
        }
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkProcessExists(createReqVO))){
            throw exception(NO_INSERTS);
        }
        if(UserConstants.YES.equals(createReqVO.getKeyFlag()) && UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkUpdateFlagUnique(createReqVO))){
            throw exception(ALERY_SPECIFIED);
        }
        Process process = processService.selectProProcessByProcessId(createReqVO.getProcessId());
        createReqVO.setProcessCode(process.getProcessCode());
        createReqVO.setProcessName(process.getProcessName());
       // 更新上一个工序的nextProcess
        RouteProcess preProcess = proRouteProcessService.findPreProcess(createReqVO);
        if(StringUtils.isNotNull(preProcess)){
            preProcess.setNextProcessId(createReqVO.getProcessId());
            preProcess.setNextProcessCode(createReqVO.getProcessCode());
            preProcess.setNextProcessName(createReqVO.getProcessName());
            proRouteProcessService.updateProRouteProcess(preProcess);
        }
        //设置当前工序的nextProcess
        RouteProcess nextProcess = proRouteProcessService.findNextProcess(createReqVO);
        if(StringUtils.isNotNull(nextProcess)){
            createReqVO.setNextProcessId(nextProcess.getProcessId());
            createReqVO.setNextProcessCode(nextProcess.getProcessCode());
            createReqVO.setNextProcessName(nextProcess.getProcessName());
        }else{
            createReqVO.setNextProcessId(0L);
            createReqVO.setNextProcessName("无");
        }
        return  success(proRouteProcessService.insertProRouteProcess(createReqVO)).setMsg("新增成功");
    }





    @DeleteMapping("/batch")
    @Operation(summary = "删除工艺工序")
    @PreAuthorize("@ss.hasPermission('pro:route:process:delete')")
    public CommonResult<Integer>  batch(@RequestBody List<Long> id) {
        return success(proRouteProcessService.deleteRouteProcess(id)).setMsg("删除成功");
    }





    @PutMapping("/update")
    @Operation(summary = "修改工艺工序")
    @PreAuthorize("@ss.hasPermission('pro:route:process:update')")
    public CommonResult<Integer>  update( @RequestBody RouteProcess createReqVO) {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkOrderNumExists(createReqVO))){
            throw exception(ROUTE_PROCESS_CODE_EXIST);
        }
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkProcessExists(createReqVO))){
            throw exception(NO_INSERTS);
        }
        if(UserConstants.YES.equals(createReqVO.getKeyFlag()) && UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkUpdateFlagUnique(createReqVO))){
            throw exception(ALERY_SPECIFIED);
        }
        Process process = processService.selectProProcessByProcessId(createReqVO.getProcessId());
        createReqVO.setProcessCode(process.getProcessCode());
        createReqVO.setProcessName(process.getProcessName());
        // 更新上一个工序的nextProcess
        RouteProcess preProcess = proRouteProcessService.findPreProcess(createReqVO);
        if(StringUtils.isNotNull(preProcess)){
            preProcess.setNextProcessId(createReqVO.getProcessId());
            preProcess.setNextProcessCode(createReqVO.getProcessCode());
            preProcess.setNextProcessName(createReqVO.getProcessName());
            proRouteProcessService.updateProRouteProcess(preProcess);
        }
        //设置当前工序的nextProcess
        RouteProcess nextProcess = proRouteProcessService.findNextProcess(createReqVO);
        if(StringUtils.isNotNull(nextProcess)){
            createReqVO.setNextProcessId(nextProcess.getProcessId());
            createReqVO.setNextProcessCode(nextProcess.getProcessCode());
            createReqVO.setNextProcessName(nextProcess.getProcessName());
        }else{
            createReqVO.setNextProcessId(0L);
            createReqVO.setNextProcessName("无");
        }
        return  success(proRouteProcessService.updateProRouteProcess(createReqVO)).setMsg("修改成功");
    }




    @GetMapping("/get/{id}")
    @Operation(summary = "工艺工序详情")
    @PreAuthorize("@ss.hasPermission('pro:route:process:get')")
    public CommonResult<RouteProcess> getRouteProcess(@PathVariable Long id) {
        return success(proRouteProcessService.getRouteProcess(id));
    }



}
