package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 17:30
 */
@Mapper
public interface OutboundItemMapper extends BaseMapperX<OutboundItem> {

  default   List<OutboundItem> findOutBoundItemByNum(String number){
      LambdaQueryWrapperX<OutboundItem> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(OutboundItem::getOutboundNumber,number);
      return  selectList(queryWrapperX);
  }


  default   OutboundItem findOutBoundItemByNumAndOutNum(String outBoundNumber, String itemNumber){
      LambdaQueryWrapperX<OutboundItem> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(OutboundItem::getOutboundNumber,outBoundNumber);
      queryWrapperX.eq(OutboundItem::getNumber,itemNumber);
      return  selectOne(queryWrapperX);
  }


  default   OutboundItem findOutItemByNum(String s){
      LambdaQueryWrapperX<OutboundItem> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(OutboundItem::getNumber,s);
      return  selectOne(queryWrapperX);
  }

   default OutboundItem findNumberBySaleItemNum(String saleItemNumber){
      return  selectOne(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getSaleItemNumber,saleItemNumber));
   }

   default List<OutboundItem> findOutItemBySaleNum(String saleNumber){
       return  selectList(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getSaleNumber,saleNumber));
   }

   default List<OutboundItem> findOutItemByNumAndItemCode(String saleNumber, String productNumber){
       return  selectList(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getSaleNumber,saleNumber).eq(OutboundItem::getItemCode,productNumber));
   }
}
