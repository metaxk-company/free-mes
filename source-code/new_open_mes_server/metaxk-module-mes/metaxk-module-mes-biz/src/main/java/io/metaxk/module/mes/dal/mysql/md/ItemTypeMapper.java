package io.metaxk.module.mes.dal.mysql.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ItemTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ItemType;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物料产品分类 Mapper
 *
 * @author 万界星空
 */
@Mapper
public interface ItemTypeMapper extends BaseMapperX<ItemType> {

    /**
     * 物料产品分类条件分页查询
     * @param itemTypeReqVO
     * @return PageResult<ItemType>
     */
    default PageResult<ItemType> selectPage(ItemTypeQueryVo itemTypeReqVO) {
        LambdaQueryWrapperX<ItemType> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(itemTypeReqVO.getItemTypeName())){
            queryWrapper.like(ItemType::getItemTypeName,itemTypeReqVO.getItemTypeName());
        }
        if(StringUtils.isNotBlank(itemTypeReqVO.getEnableFlag())){
            queryWrapper.eq(ItemType::getEnableFlag,itemTypeReqVO.getEnableFlag());
        }
        if(StringUtils.isNotBlank(itemTypeReqVO.getItemOrProduct())){
            queryWrapper.eq(ItemType::getItemOrProduct,itemTypeReqVO.getItemOrProduct());
        }
        if(StringUtils.isBlank(itemTypeReqVO.getItemTypeName()) && StringUtils.isBlank(itemTypeReqVO.getEnableFlag())
        && StringUtils.isBlank(itemTypeReqVO.getItemOrProduct())){
            queryWrapper.isNotNull(ItemType::getId);
        }
        return  selectPage(itemTypeReqVO,queryWrapper);
    }


    /**
     * 物料产品分类集合
     * @param itemTypeDO
     * @return List<ItemType>
     */
    default List<ItemType> findItemTypeList(ItemType itemTypeDO) {
        return selectList(new LambdaQueryWrapperX<ItemType>()
                .likeIfPresent(ItemType::getItemTypeName, itemTypeDO.getItemTypeName())
                .eqIfPresent(ItemType::getEnableFlag, itemTypeDO.getEnableFlag())
                .orderByDesc(ItemType::getId));
    }


    /**
     * 根据物料产品分类编号以及父类型查询物料产品分类
     * @param itemTypeCode
     * @param parentTypeId
     * @return ItemType
     */
    default ItemType checkItemTypeCodeUnique(String itemTypeCode, Long parentTypeId) {
        return  selectOne(new LambdaQueryWrapperX<ItemType>()
                .eq(ItemType::getItemTypeCode ,itemTypeCode)
                .eq(ItemType::getParentTypeId, parentTypeId)
                .last("LIMIT 1"));
    }


    /**
     * 根据物料产品分类名称以及父类型查询物料产品分类
     * @param itemTypeName
     * @param parentTypeId
     * @return ItemType
     */
    default ItemType checkItemTypeNameUnique(String itemTypeName, Long parentTypeId) {
        return selectOne(new LambdaQueryWrapperX<ItemType>()
                .eqIfPresent(ItemType::getItemTypeName, itemTypeName)
                .eqIfPresent(ItemType::getParentTypeId, parentTypeId)
                .last("LIMIT 1"));
    }


    /**
     * 根据父类型id查询产品分类
     * @param parentTypeId
     * @return Long
     */
    default  Long hasChildByItemTypeId(Long parentTypeId) {
        return  selectCount(new LambdaQueryWrapperX<ItemType>()
                .eqIfPresent(ItemType::getParentTypeId, parentTypeId)
                .last("LIMIT 1"));
    }


    /**
     * 根据父类型id查询产品分类
     * @param parentTypeId
     * @return ItemType
     */
    default ItemType selectItemTypeById(Long parentTypeId) {
        return  selectOne(new LambdaQueryWrapperX<ItemType>()
                .eqIfPresent(ItemType::getId, parentTypeId));
    }

    /**
     * 根据物料产品分类id，编号，名称查询物料产品分类
     * @param itemTypeId
     * @param itemTypeCode
     * @param itemTypeName
     * @return ItemType
     */
  default ItemType findByTypeIdAndTypeCodeAndTypeName(String itemTypeId, String itemTypeCode, String itemTypeName){
      LambdaQueryWrapperX<ItemType> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(ItemType::getId,itemTypeId);
      queryWrapperX.eq(ItemType::getItemTypeCode,itemTypeCode);
      queryWrapperX.eq(ItemType::getItemTypeName,itemTypeName);
      return  selectOne(queryWrapperX);
  }
}
