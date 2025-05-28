package io.metaxk.module.mes.dal.mysql.pro;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkorderBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrderBom;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产工单BOM组成 Mapper
 * @author 万界星空
 */
@Mapper
public interface WorkOrderBomMapper extends BaseMapperX<WorkOrderBom> {

    /**
     * 工单BOM条件分页查询
     * @param reqVO
     * @return PageResult<WorkorderBom>
     */
    default PageResult<WorkOrderBom> selectPage(WorkorderBomQueryVo reqVO) {
        LambdaQueryWrapperX<WorkOrderBom> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotNull(reqVO.getWorkorderId())){
            queryWrapper.eq(WorkOrderBom::getWorkorderId,reqVO.getWorkorderId());
        }
        return  selectPage(reqVO,queryWrapper);
    }


    /**
     * 工单BOM集合
     * @param param
     * @return List<WorkorderBom>
     */
   default List<WorkOrderBom> selectProWorkorderBomList(WorkOrderBom param){
       LambdaQueryWrapperX<WorkOrderBom> queryWrapper = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotNull(param.getWorkorderId())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getWorkorderId,param.getWorkorderId()));
       }
       if(StringUtils.isNotNull(param.getItemId())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getItemId,param.getItemId()));
       }
       if(StringUtils.isNotNull(param.getItemCode())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getItemCode,param.getItemCode()));
       }
       if(StringUtils.isNotNull(param.getItemName())){
           queryWrapper.or(wrapper -> wrapper.like(WorkOrderBom::getItemName,param.getItemName()));
       }
       if(StringUtils.isNotNull(param.getItemSpc())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getItemSpc,param.getItemSpc()));
       }
       if(StringUtils.isNotNull(param.getItemSpc())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getItemSpc,param.getItemSpc()));
       }
       if(StringUtils.isNotNull(param.getUnitOfMeasure())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getUnitOfMeasure,param.getUnitOfMeasure()));
       }
       if(StringUtils.isNotNull(param.getItemOrProduct())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getItemOrProduct,param.getItemOrProduct()));
       }
       if(StringUtils.isNotNull(param.getQuantity())){
           queryWrapper.or(wrapper -> wrapper.eq(WorkOrderBom::getQuantity,param.getQuantity()));
       }
       return  selectList(queryWrapper);
   }


    /**
     * 根据产品id,编号,名称查询订单BOM
     * @param id
     * @param productId
     * @param productCode
     * @param productName
     * @return WorkorderBom
     */
  default WorkOrderBom selectByIdAndCodeAndName(Long id, String productId, String productCode, String productName){
      LambdaQueryWrapperX<WorkOrderBom> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(WorkOrderBom::getWorkorderId,id);
      queryWrapperX.eq(WorkOrderBom::getItemId,productId);
      queryWrapperX.eq(WorkOrderBom::getItemCode,productCode);
      queryWrapperX.eq(WorkOrderBom::getItemName,productName);
      return  selectOne(queryWrapperX);
  }
}
