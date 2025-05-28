package io.metaxk.module.mes.controller.admin.md;

import io.metaxk.module.mes.controller.admin.md.vo.ProductBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ProductBom;
import io.metaxk.module.mes.service.md.ProductBomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.Operation;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.pojo.CommonResult.success;





/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 物料产品BOM")
@RestController
@RequestMapping("/mes/md/product/bom")
public class ProductBomController {


    @Resource
    private ProductBomService productBomService;



    @GetMapping("/list")
    @Operation(summary = "物料产品BOM列表分页")
    @PreAuthorize("@ss.hasPermission('md:productbom:list')")
    public CommonResult< PageResult<ProductBom>>  findProductBomPage( ProductBomQueryVo page) {
        PageResult<ProductBom> pageResult = productBomService.getProductBomPage(page);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增物料产品BOM关系")
    @PreAuthorize("@ss.hasPermission('md:productbom:save')")
    public CommonResult<Integer>  save( @RequestBody ProductBom productBomDO) {
        return success(productBomService.createProductBom(productBomDO)).setMsg("新增成功");
    }



    @GetMapping("/get/{id}")
    @Operation(summary = "物料产品BOM详情")
    @PreAuthorize("@ss.hasPermission('md:productbom:get')")
    public CommonResult<ProductBom>  getById(@PathVariable Long id) {
        ProductBom productBom = productBomService.getProductBom(id);
        return success(productBom);
    }



    @PutMapping("/update")
    @Operation(summary = "修改产品BOM关系")
    @PreAuthorize("@ss.hasPermission('md:productbom:update')")
    public CommonResult<Integer> update( @RequestBody ProductBom productBomDO) {
        return success(productBomService.updateProductBom(productBomDO)).setMsg("修改成功");
    }


    @DeleteMapping("/batch")
    @Operation(summary = "删除产品BOM关系")
    @PreAuthorize("@ss.hasPermission('md:productbom:delete')")
    public CommonResult<Integer>  batch(@RequestBody List<Long> ids) {
        return success( productBomService.deleteProductBom(ids)).setMsg("删除成功");
    }



}
