package io.metaxk.module.mes.dal.mysql.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProductQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProduct;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工艺产品 Mapper
 * @author 万界星空
 */
@Mapper
public interface RouteProductMapper extends BaseMapperX<RouteProduct> {

    /**
     * 工艺产品条件分页查询
     * @param reqVO
     * @return PageResult<RouteProduct>
     */
    default PageResult<RouteProduct> selectPage(RouteProductQueryVo reqVO) {
        LambdaQueryWrapperX<RouteProduct> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(reqVO.getItemCode())){
            queryWrapper.eq(RouteProduct::getItemCode,reqVO.getItemCode());
        }
        if(StringUtils.isNotBlank(reqVO.getItemName())){
            queryWrapper.like(RouteProduct::getItemName,reqVO.getItemName());
        }
        if(StringUtils.isNotNull(reqVO.getRouteId())){
            queryWrapper.eq(RouteProduct::getRouteId,reqVO.getRouteId());
        }
        if(StringUtils.isBlank(reqVO.getItemCode()) && StringUtils.isBlank(reqVO.getItemName()) && StringUtils.isNull(reqVO.getRouteId())){
            queryWrapper.isNotNull(RouteProduct::getId);
        }
       return  selectPage(reqVO,queryWrapper);
    }


    /**
     * 根据产品id查询工艺产品
     * @param createReqVO
     * @return RouteProduct
     */
    default RouteProduct checkItemUnique(RouteProduct createReqVO) {
        return selectOne(new LambdaQueryWrapperX<RouteProduct>()
                .eqIfPresent(RouteProduct::getItemId, createReqVO.getItemId())
                .last("LIMIT 1"));
    }


    /**
     * 根据产品id,编号,名称查询工艺产品信息
     * @param productId
     * @param productCode
     * @param productName
     * @return RouteProduct
     */
     default RouteProduct findByTypeIdAndTypeCodeAndTypeName(String productId, String productCode, String productName){
         LambdaQueryWrapperX<RouteProduct> queryWrapperX = new LambdaQueryWrapperX<>();
         queryWrapperX.eq(RouteProduct::getItemId,productId);
         queryWrapperX.eq(RouteProduct::getItemCode,productCode);
         queryWrapperX.eq(RouteProduct::getItemName,productName);
         return  selectOne(queryWrapperX);
     }

      default   List<RouteProduct> findRouteByItemCode(String itemCode){
             return  selectList(new LambdaQueryWrapperX<RouteProduct>().eq(RouteProduct::getItemCode,itemCode));
      }
}
