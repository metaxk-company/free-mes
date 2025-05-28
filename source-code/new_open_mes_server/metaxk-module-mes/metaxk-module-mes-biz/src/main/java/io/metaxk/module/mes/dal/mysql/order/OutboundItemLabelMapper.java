package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.Label;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItemLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/31 15:31
 */
@Mapper
public interface OutboundItemLabelMapper extends BaseMapperX<OutboundItemLabel> {

    List<OutboundItemLabel> findOutboundItemLabelList(@Param("id") Long id);




   default List<OutboundItemLabel> findBoundItemLabelList(String number){
       LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(OutboundItemLabel::getOutboundNumber,number);
       return  selectList(queryWrapperX);
   }


    default   List<OutboundItemLabel>  selectLabel(Long id, String saleItemNumber){
       return selectList(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getId,id).eq(OutboundItemLabel::getSaleItemNumber,saleItemNumber));
    }

   default List<OutboundItemLabel> findboundItemLabeByItemNum(String outBoundItemNumber){
       LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(OutboundItemLabel::getOutboundItemNumber,outBoundItemNumber);
       return  selectList(queryWrapperX);
   }

   default    List<OutboundItemLabel> findboundItemLabeBySaleItemNum(String saleItemNumber){
       LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(OutboundItemLabel::getSaleItemNumber,saleItemNumber);
       return  selectList(queryWrapperX);
   }

//   default OutboundItemLabel findboundItemLabelBySaleItemNum(String saleItemNumber){
//       LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
//       queryWrapperX.eq(OutboundItemLabel::getSaleItemNumber,saleItemNumber);
//       return  selectOne(queryWrapperX);
//   }
}
