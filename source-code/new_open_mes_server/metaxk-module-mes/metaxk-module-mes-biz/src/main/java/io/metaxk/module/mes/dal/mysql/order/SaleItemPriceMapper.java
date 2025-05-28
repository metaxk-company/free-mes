package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.SaleItemPriceQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.SaleItemPriceVo;
import io.metaxk.module.mes.dal.dataobject.order.SaleItemPrice;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 15:29
 */
@Mapper
public interface SaleItemPriceMapper extends BaseMapperX<SaleItemPrice> {


    /**
     * 物料价格条件分页查询
     * @param queryVo
     * @return PageResult<SaleItemPrice>
     */
    default PageResult<SaleItemPrice> findPage(SaleItemPriceQueryVo queryVo){
        LambdaQueryWrapperX<SaleItemPrice> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(queryVo.getCreateTime())){
            LocalDate createTime = LocalDate.parse(queryVo.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.like(SaleItemPrice::getCreateTime, createTime);
        }
        if(StringUtils.isNotBlank(queryVo.getCategory())){
            queryWrapperX.like(SaleItemPrice::getCategory,queryVo.getCategory());
        }
        if(StringUtils.isBlank(queryVo.getCreateTime()) && StringUtils.isBlank(queryVo.getCategory())){
            queryWrapperX.isNotNull(SaleItemPrice::getId);
        }
        return  selectPage(queryVo,queryWrapperX);
    }


    List<SaleItemPriceVo> findPrice(@Param("param1") String copper);

}
