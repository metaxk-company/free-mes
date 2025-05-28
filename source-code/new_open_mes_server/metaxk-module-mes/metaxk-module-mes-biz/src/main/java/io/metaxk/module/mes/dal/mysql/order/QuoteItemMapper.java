package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.QuoteItem;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/17 14:43
 */
@Mapper
public interface QuoteItemMapper extends BaseMapperX<QuoteItem> {

   default PageResult<QuoteItem> findPage(QuoteItemQueryVo orderQuoteItemQueryVo){
       LambdaQueryWrapperX<QuoteItem> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(orderQuoteItemQueryVo.getQuoteNumber())){
           queryWrapperX.eq(QuoteItem::getQuoteNumber,orderQuoteItemQueryVo.getQuoteNumber());
       }
       if(StringUtils.isBlank(orderQuoteItemQueryVo.getQuoteNumber())){
           queryWrapperX.isNotNull(QuoteItem::getId);
       }
       return  selectPage(orderQuoteItemQueryVo,queryWrapperX);

   }

}
