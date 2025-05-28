package io.metaxk.module.mes.controller.admin.md;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.module.mes.controller.admin.md.vo.ItemExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Item;
import io.metaxk.module.mes.dal.dataobject.md.ItemType;
import io.metaxk.module.mes.dal.dataobject.md.UnitMeasure;
import io.metaxk.module.mes.service.md.ItemService;
import io.metaxk.module.mes.service.md.ItemTypeService;
import io.metaxk.module.mes.service.md.ProductBomService;
import io.metaxk.module.mes.service.md.UnitMeasureService;
import io.metaxk.module.mes.utils.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.*;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.io.IOException;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import org.springframework.web.multipart.MultipartFile;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.error;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;


/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 物料产品管理")
@RestController
@RequestMapping("/mes/md/item")
public class ItemController {


    @Resource
    private ItemService itemService;

    @Resource
    private ItemTypeService itemTypeService;

    @Resource
    private ProductBomService  productBomService;

    @Resource
    private UnitMeasureService measureService;





    @GetMapping("/list")
    @Operation(summary = "物料产品管理条件分页查询")
    public CommonResult<PageResult<Item>> getItemPage(ItemQueryVo itemPage) {
        PageResult<Item> pageResult = itemService.getItemPage(itemPage);
        return success(pageResult);
    }



    @DeleteMapping("/batch")
    @Operation(summary = "删除物料产品管理")
    public CommonResult<Integer>  batch(@RequestBody  List<Long> ids) {
        productBomService.deleteByItemId(ids);
        return success(itemService.removeItemByIds(ids)).setMsg("删除成功");
    }




    @PostMapping("/save")
    @Operation(summary = "新增物料产品管理")
    public CommonResult<Integer>  save( @RequestBody Item item) {
        if(itemService.findItemByCode(item.getItemCode()) != null){
            throw  exception(ITEM_CODE);
        }
       if(itemService.findItemByName(item.getItemName()) != null){
           throw  exception(ITEM_NAME);
       }
       ItemType itemTypeDO = itemTypeService.selectItemByTypeId(item.getItemTypeId());
      if(StringUtils.isNotNull(itemTypeDO)){
          item.setItemTypeCode(itemTypeDO.getItemTypeCode());
          item.setItemTypeName(itemTypeDO.getItemTypeName());
          item.setItemOrProduct(itemTypeDO.getItemOrProduct());
      }
        return success(itemService.insertItem(item)).setMsg("新增成功");
    }






    @PutMapping("/update")
    @Operation(summary = "修改物料产品管理")
    public CommonResult<Integer>  update(@RequestBody Item item) {
        ItemType itemTypeDO = itemTypeService.selectItemByTypeId(item.getItemTypeId());
        if(StringUtils.isNotNull(itemTypeDO)){
            item.setItemTypeCode(itemTypeDO.getItemTypeCode());
            item.setItemTypeName(itemTypeDO.getItemTypeName());
            item.setItemOrProduct(itemTypeDO.getItemOrProduct());
        }
        if(StringUtils.isNotNull(item.getSafeStockFlag())&& "N".equals(item.getSafeStockFlag())){
            item.setMinStock(0D);
            item.setMaxStock(0D);
        }
        return success(itemService.updateItems(item)).setMsg("修改成功");
    }





    @GetMapping("/get/{id}")
    @Operation(summary = "物料产品管理详情")
    public CommonResult<Item>  getItemById(@PathVariable Long id) {
        return success(itemService.getItem(id));
    }





    @GetMapping("/export")
    @Operation(summary = "导出物料产品管理")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("物料产品管理", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ItemExcelVo.class).registerWriteHandler(styleStrategy).sheet("物料产品管理").doWrite(itemService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }




    @GetMapping("/templateData")
    @Operation(summary = "导出物料产品管理模板")
    public void exportTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("物料产品管理模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            List<ItemExcelVo> templateData = new ArrayList<>();
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ItemExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("物料产品管理模板")
                    .doWrite(templateData);
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }






    @PostMapping("/import")
    @Operation(summary = "导入物料产品管理")
    public CommonResult<Integer>  importItem( MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            List<ItemExcelVo> itemList = EasyExcel.read(inputStream).head(ItemExcelVo.class).sheet().doReadSync();
            for(ItemExcelVo list:itemList){
                UnitMeasure measure = measureService.selectByUnitOfMeasure(list.getUnitOfMeasure());
                if(measure == null){
                    UnitMeasure measureDo = new UnitMeasure();
                    measureDo.setMeasureCode(list.getUnitOfMeasure())
                             .setMeasureName(list.getUnitOfMeasure());
                    measureService.createUnitMeasure(measureDo);
                }
                String itemOrProduct = list.getItemOrProduct();
                if(!"ITEM".equals(itemOrProduct) && !"PRODUCT".equals(itemOrProduct)  ){
                    return  error(500,"物料或产品填写的格式为ITEM或PRODUCT");
                }
              ItemType itemType =  itemTypeService.findByItemCodeAndName(list.getItemTypeCode(),list.getItemTypeName());
                if(itemType == null){
                  ItemType itemTypeDo = new ItemType();
                  itemTypeDo.setItemOrProduct(itemOrProduct)
                            .setAncestors(0+","+200)
                            .setParentTypeId(200L)
                            .setItemTypeCode(list.getItemTypeCode())
                            .setItemTypeName(list.getItemTypeName());
                  itemTypeService.insertItemType(itemTypeDo);
                }

           Item item =   itemService.findByCodeAndNameSpc(list.getItemCode(),list.getItemName(),list.getSpecification());
            if(item == null){
                Item item1 = new Item();
                item1.setItemCode(list.getItemCode())
                        .setItemName(list.getItemName())
                        .setUnitOfMeasure(list.getUnitOfMeasure())
                        .setItemOrProduct(itemOrProduct)
                        .setEnableFlag("Y")
                        .setSafeStockFlag("Y")
                        .setSpec(list.getSpecification());
                itemService.insertItem(item1);
            }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  success(200).setMsg("导入成功");
    }

















}
