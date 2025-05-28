package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.OtherInboundQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Inbound;
import io.metaxk.module.mes.dal.dataobject.order.OtherInbound;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author 万界星空
 * @time 2023/8/22 16:11
 */
@Mapper
public interface OtherInboundMapper extends BaseMapperX<OtherInbound> {

    default PageResult<OtherInbound> findPage(OtherInboundQueryVo otherInboundQueryVo){
        LambdaQueryWrapperX<OtherInbound> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(otherInboundQueryVo.getInNumber())){
            queryWrapperX.eq(OtherInbound::getInNumber, otherInboundQueryVo.getInNumber());
        }
        if(StringUtils.isNotBlank(otherInboundQueryVo.getWareHouse())){
            queryWrapperX.eq(OtherInbound::getWareHouse, otherInboundQueryVo.getWareHouse());
        }
        if(StringUtils.isNotBlank(otherInboundQueryVo.getStatus())){
            queryWrapperX.eq(OtherInbound::getStatus, otherInboundQueryVo.getStatus());
        }
        if(StringUtils.isNotBlank(otherInboundQueryVo.getSource())){
            queryWrapperX.eq(OtherInbound::getSource, otherInboundQueryVo.getSource());
        }
        /*if(StringUtils.isNotBlank(otherInboundQueryVo.getStartTime())){
            LocalDate requestDate = LocalDate.parse(otherInboundQueryVo.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.ge(OtherInbound::getDeliveryDate, requestDate);
        }
        if(StringUtils.isNotBlank(otherInboundQueryVo.getEndTime())){
            LocalDate requestDate = LocalDate.parse(otherInboundQueryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.le(OtherInbound::getDeliveryDate, requestDate);
        }*/
        if(StringUtils.isBlank(otherInboundQueryVo.getInNumber()) && StringUtils.isNotBlank(otherInboundQueryVo.getSource()) && StringUtils.isBlank(otherInboundQueryVo.getWareHouse()) && StringUtils.isBlank(otherInboundQueryVo.getStatus())){
            queryWrapperX.isNotNull(OtherInbound::getId);
        }
        return  selectPage(otherInboundQueryVo, queryWrapperX);
    }
}
