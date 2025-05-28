package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.ReturnsItem;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabelItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 17:03
 */
@Mapper
public interface ReturnsItemMapper extends BaseMapperX<ReturnsItem> {

    default List<ReturnsItem> selectReturnsItemByReturnNumber(String returnNumber){
        LambdaQueryWrapperX<ReturnsItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReturnsItem::getReturnNumber,returnNumber);
        return selectList(queryWrapperX);
    }
}
