package io.metaxk.module.mes.service.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.common.TreeSelect;
import io.metaxk.module.mes.controller.admin.md.vo.ItemTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ItemType;

/**
 * 物料产品分类 Service 接口
 * @author 万界星空MES
 */
public interface ItemTypeService {


    /**
     * 修改物料产品分类
     * @param itemTypeDO
     * @return Integer
     */
    Integer updateItemType( ItemType itemTypeDO);


    /**
     * 删除物料产品分类
     * @param ids
     * @return Integer
     */
    Integer deleteItemType(Long ids);





    /**
     * 物料产品分类条件分页查询
     * @param pageReqVO
     * @return PageResult<ItemType>
     */
    PageResult<ItemType> getItemTypePage(ItemTypeQueryVo pageReqVO);




    /**
     * 物料产品分类列表
     * @param itemTypeDO
     * @return List<ItemType>
     */
    List<ItemType> findItemTypeList(ItemType itemTypeDO);

    /**
     * 构建物料产品分类树形结构
     * @param list
     * @return List<TreeSelect>
     */
    List<TreeSelect> buildTreeSelect(List<ItemType> list);

    /**
     * 校验物料产品分类
     * @param itemTypeDO
     * @return String
     */
    String checkItemTypeCodeUnique(ItemType itemTypeDO);


    /**
     * 校验物料产品分类
     * @param itemTypeDO
     * @return String
     */
    String checkItemTypeNameUnique(ItemType itemTypeDO);


    /**
     * 新增物料产品分类
     * @param itemTypeDO
     * @return Integer
     */
    Integer insertItemType(ItemType itemTypeDO);

    /**
     * 校验物料产品分类是否有子分类
     * @param itemTypeId
     * @return boolean
     */
    boolean checkHasChild(Long itemTypeId);

    /**
     * 校验物料产品分类下是否有产品
     * @param itemTypeId
     * @return boolean
     */
    boolean checkHasItem(Long itemTypeId);


    /**
     * 根据物料产品分类id查询物料产品分类
     * @param itemTypeId
     * @return ItemType
     */
    ItemType selectItemByTypeId(Long itemTypeId);


    /**
     * 根据id查询物料产品分类
     * @param id
     * @return ItemType
     */
    ItemType selectItemTypeById(Long id);

    /**
     * 物料产品分类列表
     * @param itemTypeDO
     * @return List<ItemType>
     */
    List<ItemType> selectItemTypeLists(ItemType itemTypeDO);

    /**
     * 根据编号，名称查询物料产品分类
     * @param itemTypeCode
     * @param itemTypeName
     * @return ItemType
     */
    ItemType findByItemCodeAndName(String itemTypeCode, String itemTypeName);

    List<ItemType> itemTypeEnableList();
}
