package io.metaxk.module.mes.controller.admin.order;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsQueryVO;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsVO;
import io.metaxk.module.mes.controller.admin.order.vo.SaleCountQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.SaleCountVo;
import io.metaxk.module.mes.dal.dataobject.order.Sale;
import io.metaxk.module.mes.service.order.InventoryStatisticsService;
import io.metaxk.module.mes.service.order.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.pojo.CommonResult.success;



/**
 * @author xx
 * @time 2023/8/17 13:22
 */
@Tag(name = "管理后台 - 库存查询")
@RestController
@RequestMapping("/mes/order/statistics")
public class InventoryStatisticsController {

    @Resource
    private InventoryStatisticsService inventoryStatisticsService;

    @Resource
    private SaleService  saleService;

    @GetMapping("/list")
    @Operation(summary = "库存查询列表")
    public CommonResult<PageResult<InventoryStatisticsVO>> list(String mode, InventoryStatisticsQueryVO inventoryStatisticsQueryVO){
        return success(inventoryStatisticsService.InventoryStatistics(mode, inventoryStatisticsQueryVO));
    }




    @GetMapping("/saleOrderCountList")
    @Operation(summary = "统计查询销售订单列表")
    public CommonResult<PageResult<Sale>> saleOrderCountList(SaleCountQueryVo saleCountQueryVo){
        PageResult<Sale> pageResult = saleService.saleCountPage(saleCountQueryVo);
        return success(pageResult);
    }



    @GetMapping("/findCountOutBound")
    @Operation(summary = "根据出库单号统计查询销售出库列表")
    public CommonResult<PageResult<SaleCountVo>> findCountOutBound(SaleCountQueryVo  saleCountQueryVo){
        List<SaleCountVo> saleCountVoList =  saleService.findOutBound(saleCountQueryVo);
        PageResult<SaleCountVo> pageResult = new PageResult<>();
        pageResult.setTotal((long) saleCountVoList.size());
        int firstIndex = (saleCountQueryVo.getPageNo()-1)*saleCountQueryVo.getPageSize();
        int lastIndex = saleCountQueryVo.getPageNo()*saleCountQueryVo.getPageSize();
        if (saleCountVoList.size() != 0 && saleCountVoList.size() >= 1){
            List<SaleCountVo>  list = new ArrayList<>();
            if (saleCountVoList.size() < lastIndex){
                list = saleCountVoList.subList(firstIndex,saleCountVoList.size());
            }else{
                list = saleCountVoList.subList(firstIndex,lastIndex);
            }
            pageResult.setList(list);
        }else{
            pageResult.setList(saleCountVoList);
        }
        return success(pageResult);
    }










}
