package io.metaxk.module.mes.controller.admin.pro;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.RoutePageReqVo;
import io.metaxk.module.mes.dal.dataobject.pro.Route;
import io.metaxk.module.mes.service.pro.RouteProcessService;
import io.metaxk.module.mes.service.pro.RouteProductService;
import io.metaxk.module.mes.service.pro.RouteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.io.IOException;
import java.util.List;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 工艺路线")
@RestController
@RequestMapping("/mes/pro/route")
public class RouteController {



    @Resource
    private RouteService routeService;

    @Resource
    private RouteProcessService routeProcessService;

    @Resource
    private RouteProductService routeProductService;





    @GetMapping("/list")
    @Operation(summary = "工艺路线条件分页查询")
    @PreAuthorize("@ss.hasPermission('pro:route:list')")
    public CommonResult<PageResult<Route>>  getRoutePage( RoutePageReqVo routePageReqVO) {
        PageResult<Route> pageResult = routeService.getRoutePage(routePageReqVO);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增工艺路线")
    @PreAuthorize("@ss.hasPermission('pro:route:save')")
    public CommonResult<Integer> save( @RequestBody Route route) {
        if(routeService.findRouteByCode(route.getRouteCode()) != null){
            throw exception(ROUTE_CODE_EXIST);
        }
        return success(routeService.insertRoute(route)).setMsg("新增成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改工艺路线")
    @PreAuthorize("@ss.hasPermission('pro:route:update')")
    public CommonResult<Integer>  update( @RequestBody Route route) {
        return success(routeService.updateRoutes(route)).setMsg("修改成功");
    }





    @DeleteMapping("/batch")
    @Operation(summary = "删除工艺路线")
    @PreAuthorize("@ss.hasPermission('pro:route:delete')")
    public CommonResult<Integer>  batch(@RequestBody List<Long> routeIds) {
        for (Long routeId:routeIds) {
            routeProcessService.deleteByRouteId(routeId);
            routeProductService.deleteByRouteId(routeId);
        }
        return success(routeService.deleteRoutes(routeIds)).setMsg("删除成功");
    }



    @GetMapping("/get/{id}")
    @Operation(summary = "工艺路线详情")
    @PreAuthorize("@ss.hasPermission('pro:route:get')")
    public CommonResult<Route>  getRouteById(@PathVariable Long id) {
        return success(routeService.getRoute(id));
    }





    @GetMapping("/export")
    @Operation(summary = "工艺路线导出")
    @PreAuthorize("@ss.hasPermission('pro:route:export')")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("工艺路线", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), RouteExcelVo.class).registerWriteHandler(styleStrategy).sheet("工艺路线").doWrite(routeService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }


}
