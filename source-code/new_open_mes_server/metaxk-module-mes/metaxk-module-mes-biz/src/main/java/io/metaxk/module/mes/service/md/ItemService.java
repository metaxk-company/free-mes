package io.metaxk.module.mes.service.md;

import java.io.InputStream;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ItemExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Item;

/**
 * 物料产品 Service 接口
 * @author 万界星空
 */
public interface ItemService {


    /**
     * 根据id查询物料产品
     * @param id
     * @return Item
     */
    Item getItem(Long id);

    /**
     * 物料产品列表
     * @param ids
     * @return List<Item>
     */
    List<Item> getItemList(Collection<Long> ids);

    /**
     * 物料产品条件分页查询
     * @param pageReqVO
     * @return PageResult<Item>
     */
    PageResult<Item> getItemPage(ItemQueryVo pageReqVO);

    /**
     * 删除物料产品
     * @param ids
     * @return Integer
     */
    Integer removeItemByIds(List<Long> ids);

    /**
     * 新增物料产品
     * @param itemDO
     * @return Integer
     */
    Integer insertItem(Item itemDO);

    /**
     * 修改物料产品
     * @param itemDO
     * @return Integer
     */
    Integer updateItems(Item itemDO);

    /**
     * 导出物料产品
     * @return List<ItemExcelVO>
     */
    List<ItemExcelVo> exportData();

    /**
     * 根据产品编号查询物料产品
     * @param productCode
     * @return Item
     */
    Item selectMdItemByItemCode(String productCode);

    /**
     * 新增物料产品
     * @param inputStream
     */
    void importData(InputStream inputStream);

    /**
     * 根据产品编号，名称，规格型号查询物料产品
     * @param itemCode
     * @param itemName
     * @param specification
     * @return Item
     */
    Item findByCodeAndNameSpc(String itemCode, String itemName, String specification);

    /**
     * 根据产品编号查询物料产品
     * @param itemCode
     * @return Item
     */
    Item findItemByCode(String itemCode);

    /**
     * 根据产品名称查询物料产品
     * @param itemName
     * @return Item
     */
    Item findItemByName(String itemName);

    PageResult<Item> findItemPage(ItemQueryVo itemPage);
}
