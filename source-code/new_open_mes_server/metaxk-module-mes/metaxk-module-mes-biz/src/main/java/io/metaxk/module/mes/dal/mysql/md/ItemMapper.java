package io.metaxk.module.mes.dal.mysql.md;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ItemExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Item;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 物料产品 Mapper
 * @author 万界星空
 */
@Mapper
public interface ItemMapper extends BaseMapperX<Item> {


    /**
     * 物料产品管理条件分页查询
     * @param reqVO
     * @return PageResult<Item>
     */
    default PageResult<Item> selectPage(ItemQueryVo reqVO) {
        LambdaQueryWrapper<Item> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(reqVO.getItemCode())) {
            queryWrapper.like(Item::getItemCode, reqVO.getItemCode());
        }
        if (StringUtils.isNotEmpty(reqVO.getSpecification())) {
            queryWrapper.like(Item::getSpec, reqVO.getSpecification());
        }
        if (reqVO.getItemTypeId() != null && reqVO.getItemTypeId() != 0) {
            queryWrapper.and(qw -> qw.eq(Item::getItemTypeId, reqVO.getItemTypeId())
                    .or().inSql(Item::getItemTypeId, "select id from md_item_type where find_in_set(" + reqVO.getItemTypeId() + ", ancestors)"));
        }
        if (StringUtils.isNotEmpty(reqVO.getItemName())) {
            queryWrapper.like(Item::getItemName, reqVO.getItemName());
        }
        if (StringUtils.isNotEmpty(reqVO.getEnableFlag())) {
            queryWrapper.eq(Item::getEnableFlag, reqVO.getEnableFlag());
        }
        if (StringUtils.isNotEmpty(reqVO.getVendor())) {
            queryWrapper.like(Item::getVendor, reqVO.getVendor());
        }
        return selectPage(reqVO, queryWrapper);
    }


    /**
     * 根据产品类型id进行查询
     * @param itemTypeId
     * @return Long
     */
    default Long hasItemByItemTypeId(Long itemTypeId) {
        return selectCount(new LambdaQueryWrapperX<Item>()
                .eqIfPresent(Item::getItemTypeId, itemTypeId)
                .last("LIMIT 1"));
    }


    /**
     * excel导入
     * @param list
     */
    void insertBatchs(List<ItemExcelVo> list);

    /**
     * 根据产品id,编号,名称进行查询产品信息
     * @param productId
     * @param productCode
     * @param productName
     * @return Item
     */
   default Item findByIdAndItemCodeAndItemNameAndSpc(String productId, String productCode, String productName){
       LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(Item::getId,productId);
       queryWrapperX.eq(Item::getItemCode,productCode);
       queryWrapperX.eq(Item::getItemName,productName);
       return  selectOne(queryWrapperX);
   }

      default   Item findItemByName(String itemName){
          LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
          queryWrapperX.eq(Item::getItemName,itemName);
          return  selectOne(queryWrapperX);
      }

  default   PageResult<Item> findItemPage(ItemQueryVo itemPage){
      LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(itemPage.getItemTypeCode())){
          queryWrapperX.eq(Item::getItemTypeCode,itemPage.getItemTypeCode());
      }

      if (StringUtils.isBlank(itemPage.getItemTypeCode())) {
          queryWrapperX.isNotNull(Item::getId);
      }
      return  selectPage(itemPage,queryWrapperX);
  }

    @Select("select item_code from md_item where model = #{model}")
    String selectCodeByModel(String model);

}
