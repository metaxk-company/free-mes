package io.metaxk.module.mes.controller.admin.data;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.dal.dataobject.data.CustomDictData;
import io.metaxk.module.mes.service.data.CustomDictDataService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import static io.metaxk.framework.common.pojo.CommonResult.success;



/**
 * @author 万界星空
 */
@RestController
@RequestMapping("/mes/dict/data")
public class CustomDictDataController {

    @Resource
    private CustomDictDataService dictDataService;





    @GetMapping("/listData")
    @Operation(summary = "暂停原因数据字典列表")
    public CommonResult<List<CustomDictData>>  findListData(){
        List<CustomDictData> list = dictDataService.findListData();
        return success(list);
    }



    @GetMapping("/listLineType")
    @Operation(summary = "线别数据字典列表")
    public CommonResult<List<CustomDictData>>  findListLineType(){
        List<CustomDictData> list = dictDataService.listLineType();
        return success(list);
    }



    @GetMapping("/listCategory")
    @Operation(summary = "品类数据字典列表")
    public CommonResult<List<CustomDictData>>  findListCategory(){
        List<CustomDictData> list = dictDataService.listCategory();
        return success(list);
    }



    @GetMapping("/listKind")
    @Operation(summary = "类别数据字典列表")
    public CommonResult<List<CustomDictData>>  findListKind(){
        List<CustomDictData> list = dictDataService.listKind();
        return success(list);
    }
    @GetMapping("/outBoundLimit")
    @Operation(summary = "类别数据字典列表")
    public CommonResult<CustomDictData>  findOutBoundLimit(){
        CustomDictData customDictData  = dictDataService.findOutBoundLimit();
        return success(customDictData);
    }

}
