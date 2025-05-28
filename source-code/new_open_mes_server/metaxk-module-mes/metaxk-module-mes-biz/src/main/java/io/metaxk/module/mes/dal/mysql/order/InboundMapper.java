package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.InboundQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.InboundVo;
import io.metaxk.module.mes.dal.dataobject.order.Inbound;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * 采购入库Mapper
 * @author 万界星空MES
 */
@Mapper
public interface InboundMapper extends BaseMapperX<Inbound> {

      default PageResult<Inbound> findPage(InboundQueryVo inboundQueryVo) {
        LambdaQueryWrapperX<Inbound> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(inboundQueryVo.getInNumber())){
            queryWrapperX.eq(Inbound::getInNumber, inboundQueryVo.getInNumber());
        }
        if(StringUtils.isNotBlank(inboundQueryVo.getReceiptNumber())){
            queryWrapperX.eq(Inbound::getReceiptNumber, inboundQueryVo.getReceiptNumber());
        }
        if(StringUtils.isNotBlank(inboundQueryVo.getWareHouse())){
            queryWrapperX.eq(Inbound::getWareHouse, inboundQueryVo.getWareHouse());
        }
        if(StringUtils.isNotBlank(inboundQueryVo.getStatus())){
            queryWrapperX.eq(Inbound::getStatus, inboundQueryVo.getStatus());
        }
          if(StringUtils.isNotBlank(inboundQueryVo.getSource())){
              queryWrapperX.eq(Inbound::getSource, inboundQueryVo.getSource());
          }
        if(StringUtils.isNotBlank(inboundQueryVo.getStartTime())){
            LocalDate requestDate = LocalDate.parse(inboundQueryVo.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.ge(Inbound::getDeliveryDate, requestDate);
        }
        if(StringUtils.isNotBlank(inboundQueryVo.getEndTime())){
            LocalDate requestDate = LocalDate.parse(inboundQueryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.le(Inbound::getDeliveryDate, requestDate);
        }
        if(StringUtils.isBlank(inboundQueryVo.getInNumber()) && StringUtils.isNotBlank(inboundQueryVo.getSource()) && StringUtils.isBlank(inboundQueryVo.getWareHouse()) && StringUtils.isBlank(inboundQueryVo.getStatus()) && StringUtils.isBlank(inboundQueryVo.getStartTime()) && StringUtils.isBlank(inboundQueryVo.getEndTime())){
            queryWrapperX.isNotNull(Inbound::getId);
        }
        return  selectPage(inboundQueryVo, queryWrapperX);
      }


      default   Inbound findInboundByNum(String number){
          return  selectOne(new LambdaQueryWrapperX<Inbound>().eq(Inbound::getInNumber,number));
      }

    List<InboundVo> inBoundPrint(String number);
}
