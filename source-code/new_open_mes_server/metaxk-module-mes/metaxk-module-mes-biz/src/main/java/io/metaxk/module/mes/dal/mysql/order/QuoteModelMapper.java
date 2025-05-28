package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteModelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Quote;
import io.metaxk.module.mes.dal.dataobject.order.QuoteModel;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author 万界星空
 * @time 2023/7/17 15:03
 */
@Mapper
public interface QuoteModelMapper extends BaseMapperX<QuoteModel> {

   default PageResult<QuoteModel> findPage(QuoteModelQueryVo queryVo){
       LambdaQueryWrapperX<QuoteModel> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(queryVo.getModel())){
           queryWrapperX.like(QuoteModel::getModel,queryVo.getModel());
       }
       if(StringUtils.isNotBlank(queryVo.getCreateTime())){
           LocalDate requestDate = LocalDate.parse(queryVo.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
           queryWrapperX.ge(QuoteModel::getCreateTime, requestDate);
       }
       if(StringUtils.isNotBlank(queryVo.getEndTime())){
           LocalDate requestDate = LocalDate.parse(queryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
           queryWrapperX.le(QuoteModel::getCreateTime, requestDate.plusDays(1));
       }
       if(StringUtils.isBlank(queryVo.getModel())){
           queryWrapperX.isNotNull(QuoteModel::getId);
       }
       return  selectPage(queryVo,queryWrapperX);
   }


}
