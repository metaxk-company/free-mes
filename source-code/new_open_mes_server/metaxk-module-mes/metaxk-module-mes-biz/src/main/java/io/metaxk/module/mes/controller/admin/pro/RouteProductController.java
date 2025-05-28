package io.metaxk.module.mes.controller.admin.pro;


import cn.hutool.core.collection.CollUtil;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProductQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProduct;
import io.metaxk.module.mes.service.pro.RouteProductService;
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
@Tag(name = "管理后台 - 工艺产品")
@RestController
@RequestMapping("/mes/pro/routeProduct")
public class RouteProductController {


    @Resource
    private RouteProductService routeProductService;




    @GetMapping("/list")
    @Operation(summary = "工艺产品分页")
    @PreAuthorize("@ss.hasPermission('pro:route:product:list')")
    public CommonResult<PageResult<RouteProduct>> routeProductPage(@Valid RouteProductQueryVo pageVO) {
        PageResult<RouteProduct> pageResult = routeProductService.getRouteProductPage(pageVO);
        return success(pageResult);
    }




    @PostMapping("/save")
    @Operation(summary = "新增工艺产品")
    @PreAuthorize("@ss.hasPermission('pro:route:product:save')")
    public CommonResult<Integer>  save(@RequestBody RouteProduct routeProduct) {
        if(UserConstants.NOT_UNIQUE.equals(routeProductService.checkItemUnique(routeProduct))){
            throw exception(ROUTE_PRODUCT_ALERY_EXIST);
        }
        return success(routeProductService.createRouteProduct(routeProduct)).setMsg("新增成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改工艺产品")
    @PreAuthorize("@ss.hasPermission('pro:route-product:update')")
    public CommonResult<Integer>  updateRouteProduct( @RequestBody RouteProduct updateReqVO) {
        if(UserConstants.NOT_UNIQUE.equals(routeProductService.checkItemUnique(updateReqVO))){
            return CommonResult.error(500,"此产品已配置了工艺路线");
        }
        return success(routeProductService.updateRouteProduct(updateReqVO)).setMsg("修改成功");
    }





    @DeleteMapping("/batch")
    @Operation(summary = "删除工艺产品")
    @PreAuthorize("@ss.hasPermission('pro:route:product:delete')")
    public CommonResult<Integer> batch(@RequestBody List<Long> recordIds) {
        for (Long recordId:recordIds
        ) {
            RouteProduct product = routeProductService.selectProRouteProductByRecordId(recordId);
        }
        return success(routeProductService.deleteRouteProduct(recordIds)).setMsg("删除成功");
    }




    @PutMapping("/move")
    public CommonResult<Integer> move(@RequestBody RouteProduct routeProductDO){
        RouteProduct param = new RouteProduct();
        param.setItemId(routeProductDO.getItemId());
        param.setRouteId(routeProductDO.getRouteId());
        List<RouteProduct> products = routeProductService.selectProRouteProductList(param);
        int ret =1;
        if(CollUtil.isNotEmpty(products)){
            RouteProduct product = products.get(0);
            product.setRouteId(routeProductDO.getRouteId());
            ret =routeProductService.updateRouteProduct(product);
        }
        return success(ret);
    }





    @GetMapping("/get/{id}")
    @Operation(summary = "工艺产品详情")
    @PreAuthorize("@ss.hasPermission('pro:route:product:get')")
    public CommonResult<RouteProduct>  getRouteProduct(@PathVariable Long id) {
        return success( routeProductService.getRouteProduct(id));
    }




}
