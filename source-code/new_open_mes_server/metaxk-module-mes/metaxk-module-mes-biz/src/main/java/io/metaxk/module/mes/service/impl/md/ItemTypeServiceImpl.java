package io.metaxk.module.mes.service.impl.md;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.TreeSelect;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.md.vo.ItemTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ItemType;
import io.metaxk.module.mes.dal.mysql.md.ItemMapper;
import io.metaxk.module.mes.dal.mysql.md.ItemTypeMapper;
import io.metaxk.module.mes.service.md.ItemTypeService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.ITEM_TYPE_NOT_EXISTS;

/**
 * 物料产品分类 Service 实现类
 *
 * @author 万界星空MES
 */
@Service
public class ItemTypeServiceImpl implements ItemTypeService {

    @Resource
    private ItemTypeMapper itemTypeMapper;
    private List<ItemType> itemTypes;

    @Resource
    private ItemMapper itemMapper;



    @Override
    public Integer updateItemType(ItemType itemType) {
        ItemType itemTypeDO = itemTypeMapper.selectById(itemType.getId());
        itemType.setCreateTime(itemTypeDO.getCreateTime());

        itemType.setId(itemType.getId());
        itemType.setUpdateTime(new Date());
      return itemTypeMapper.updateById(itemType);
    }

    @Override
    public Integer deleteItemType(Long id) {
      return  itemTypeMapper.deleteById(id);
    }


    private void validateItemTypeExists(Long id) {
        if (itemTypeMapper.selectById(id) == null) {
            throw exception(ITEM_TYPE_NOT_EXISTS);
        }
    }




    @Override
    public PageResult<ItemType> getItemTypePage(ItemTypeQueryVo pageReqVO) {
        pageReqVO.setPageSize(100000);//设置最大分类数量，代表不分页
        return itemTypeMapper.selectPage(pageReqVO);
    }





    @Override
    public List<ItemType> findItemTypeList(ItemType itemTypeDO) {
        return itemTypeMapper.findItemTypeList(itemTypeDO);
    }


    /*
    *
    * 构建树形结构
    * */
    @Override
    public List<TreeSelect> buildTreeSelect(List<ItemType> list) {
        List<ItemType> itemTypes = buildTree(list);
        return itemTypes.stream().map(TreeSelect::new).collect(Collectors.toList());
    }




    @Override
    public String checkItemTypeCodeUnique(ItemType itemTypeDO) {
        ItemType itemType =itemTypeMapper.checkItemTypeCodeUnique(itemTypeDO.getItemTypeCode(),itemTypeDO.getParentTypeId());
        Long itemTypeId1 = itemTypeDO.getId() ==null? -1L:itemTypeDO.getId();
        if(StringUtils.isNotNull(itemType)&& itemTypeId1.longValue() != itemType.getId().longValue()){
            return  UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /*
    * 根据分类名称父类校验
    * */
    @Override
    public String checkItemTypeNameUnique(ItemType itemTypeDO) {
        ItemType itemType =itemTypeMapper.checkItemTypeNameUnique(itemTypeDO.getItemTypeName(),itemTypeDO.getParentTypeId());
        Long itemTypeId = itemTypeDO.getId() ==null? -1L:itemTypeDO.getId();
        if(StringUtils.isNotNull(itemType)&& itemTypeId.longValue() != itemType.getId().longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /*
    * 添加产品分类
    * */
    @Override
    public Integer insertItemType(ItemType itemType) {
        if(itemType.getParentTypeId()!= null){
            ItemType parent = itemTypeMapper.selectItemTypeById(itemType.getParentTypeId());
            if(StringUtils.isNotNull(parent)){
                itemType.setAncestors(parent.getAncestors()+","+parent.getId());
            }
        }
        itemType.setCreateTime(new Date());
        return itemTypeMapper.insert(itemType);
    }

    @Override
    public boolean checkHasChild(Long itemTypeId) {
        long num =itemTypeMapper.hasChildByItemTypeId(itemTypeId);
        return num >0 ? true:false;
    }

    @Override
    public boolean checkHasItem(Long itemTypeId) {
        long num =itemMapper.hasItemByItemTypeId(itemTypeId);
        return num >0 ? true:false;
    }

    @Override
    public ItemType selectItemByTypeId(Long itemTypeId) {
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",itemTypeId);
        return itemTypeMapper.selectOne(queryWrapper);
    }



    @Override
    public ItemType selectItemTypeById(Long id) {
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return itemTypeMapper.selectOne(queryWrapper);
    }

    @Override
    public List<ItemType> selectItemTypeLists(ItemType itemTypeDO) {
        return itemTypeMapper.selectList();
    }

    @Override
    public ItemType findByItemCodeAndName(String itemTypeCode, String itemTypeName) {
        LambdaQueryWrapperX<ItemType> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ItemType::getItemTypeCode,itemTypeCode);
        queryWrapperX.eq(ItemType::getItemTypeName,itemTypeName);
        return itemTypeMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<ItemType> itemTypeEnableList() {
        LambdaQueryWrapperX<ItemType> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ItemType::getEnableFlag,"Y");
        return itemTypeMapper.selectList(queryWrapperX);
    }


    private List<ItemType> buildTree(List<ItemType> itemTypes){
       this.itemTypes = itemTypes;
        List<ItemType> returnList = new ArrayList<ItemType>();
        List<Long> tempList = new ArrayList<Long>();
        for(ItemType it : itemTypes){
            tempList.add(it.getId());
        }

        for(ItemType it : itemTypes){
            if(!tempList.contains(it.getParentTypeId())){
                recursionFn(itemTypes,it);
                returnList.add(it);
            }
        }
        if(returnList.isEmpty()){
            returnList = itemTypes;
        }
        return returnList;
    }


    /**
     * 递归列表
     */
    private void recursionFn(List<ItemType> list, ItemType t)
    {
        // 得到子节点列表
        List<ItemType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ItemType tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }


    /**
     * 得到子节点列表
     */
    private List<ItemType> getChildList(List<ItemType> list, ItemType t)
    {
        List<ItemType> tlist = new ArrayList<ItemType>();
        Iterator<ItemType> it = list.iterator();
        while (it.hasNext())
        {
            ItemType n = (ItemType) it.next();
            if (StringUtils.isNotNull(n.getParentTypeId()) && n.getParentTypeId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ItemType> list, ItemType t)
    {
        return getChildList(list, t).size() > 0;
    }






}
