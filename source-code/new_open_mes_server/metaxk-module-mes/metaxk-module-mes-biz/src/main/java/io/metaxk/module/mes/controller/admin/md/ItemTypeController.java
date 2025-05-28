package io.metaxk.module.mes.controller.admin.md;

import io.metaxk.module.mes.common.TreeSelect;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.md.vo.ItemTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ItemType;
import io.metaxk.module.mes.service.md.ItemTypeService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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
@Tag(name = "管理后台 - 物料产品分类")
@RestController
@RequestMapping("/mes/md/itemType")
public class ItemTypeController {

    @Resource
    private ItemTypeService itemTypeService;

    @Resource
    private AutoCodeUtil autoCodeUtil;




    @GetMapping("/treeSelectEnable")
    @Operation(summary = "已启用物料产品分类树形显示")
    public CommonResult<List<TreeSelect>>  treeSelectEnable() {
        List<ItemType> list = itemTypeService.itemTypeEnableList();
        List<TreeSelect>  treeSelectList =  itemTypeService.buildTreeSelect(list);
        return success(treeSelectList);
    }


    @GetMapping("/treeSelect")
    @Operation(summary = "物料产品分类树形显示")
    public CommonResult<List<TreeSelect>>  getItemTypeList( ItemType itemType) {
        List<ItemType> list = itemTypeService.findItemTypeList(itemType);
        List<TreeSelect>  treeSelectList =  itemTypeService.buildTreeSelect(list);
        return success(treeSelectList);
    }



    @GetMapping("/list")
    @Operation(summary = "物料产品分类条件分页查询")
    @PreAuthorize("@ss.hasPermission('md:itemType:list')")
    public CommonResult<PageResult<ItemType>> list(ItemTypeQueryVo itemTypePage) {
        PageResult<ItemType> pageResult = itemTypeService.getItemTypePage(itemTypePage);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增物料产品分类")
    @PreAuthorize("@ss.hasPermission('md:itemType:save')")
    public CommonResult<Integer> insertItemType( @RequestBody ItemType itemTypeDO) {
        itemTypeDO.setItemTypeCode(autoCodeUtil.genSerialCode(UserConstants.ITEM_TYPE_CODE,null));
        if(UserConstants.NOT_UNIQUE.equals(itemTypeService.checkItemTypeCodeUnique(itemTypeDO))){
            throw  exception(ITEM_TYPE_CODE);
        }
        if(UserConstants.NOT_UNIQUE.equals(itemTypeService.checkItemTypeNameUnique(itemTypeDO))){
            throw  exception(ITEM_TYPE_NAME);
        }
        if(itemTypeDO.getParentTypeId() ==null || itemTypeDO.getParentTypeId()==0){
            itemTypeDO.setParentTypeId(0L);
        }
        return success( itemTypeService.insertItemType(itemTypeDO)).setMsg("新增成功");
    }




    @PutMapping("/update")
    @Operation(summary = "修改物料产品分类")
    @PreAuthorize("@ss.hasPermission('md:itemType:update')")
    public CommonResult<Integer> updateItemType(@Validated @RequestBody ItemType itemTypeDO){
        if(UserConstants.NOT_UNIQUE.equals(itemTypeService.checkItemTypeCodeUnique(itemTypeDO))){
            throw  exception(ITEM_TYPE_CODE);
        }
        if(UserConstants.NOT_UNIQUE.equals(itemTypeService.checkItemTypeNameUnique(itemTypeDO))){
            throw  exception(ITEM_TYPE_NAME);
        }
        return success(itemTypeService.updateItemType(itemTypeDO)).setMsg("修改成功");
    }



    @GetMapping(value = "/get/{id}")
    @Operation(summary = "物料产品分类详情")
    public CommonResult<ItemType> getInfo(@PathVariable Long id){
        return success(itemTypeService.selectItemTypeById(id));
    }




    @GetMapping("/list/exclude/{itemTypeId}")
    @Operation(summary = "根据物料产品分类ID进行查询")
    public CommonResult<List<ItemType>> excludeChild(@PathVariable Long itemTypeId){
        List<ItemType> list = itemTypeService.selectItemTypeLists(new ItemType());
        Iterator<ItemType> it = list.iterator();
        Long parentTypeId =0L;
        while (it.hasNext()){
            ItemType itemType = (ItemType) it.next();
            if(itemType.getId().equals(itemTypeId)){
                parentTypeId = itemType.getParentTypeId();
                it.remove();
            }
            if(itemType.getId().equals(parentTypeId)){
                it.remove();
            }
        }
        return success(list);
    }





    @DeleteMapping("/{itemTypeId}")
    @Operation(summary = "删除物料产品分类")
    @PreAuthorize("@ss.hasPermission('md:itemtype:delete')")
    public CommonResult<Integer>  delete(@PathVariable  Long itemTypeId){
        if(itemTypeService.checkHasChild(itemTypeId)){
            throw  exception(ITEM_TYPE_IS_CHILD);
        }
        if(itemTypeService.checkHasItem(itemTypeId)){
            throw  exception(ITEM_TYPE_IS_ITEM);
        }
        return success(itemTypeService.deleteItemType(itemTypeId)).setMsg("删除成功");
    }



}
