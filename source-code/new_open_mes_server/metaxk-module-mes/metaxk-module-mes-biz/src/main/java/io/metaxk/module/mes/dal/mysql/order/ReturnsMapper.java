package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnsQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrder;
import io.metaxk.module.mes.dal.dataobject.order.Returns;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 退货记录 Mapper
 * @author 万界星空
 * @time 2023/7/18 16:40
 */
@Mapper
public interface ReturnsMapper extends BaseMapperX<Returns> {


    default  PageResult<Returns> findPage(ReturnsQueryVo queryVo){
        LambdaQueryWrapperX<Returns> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(queryVo.getNumber())){
            queryWrapperX.eq(Returns::getNumber,queryVo.getNumber());
        }
        if(StringUtils.isNotBlank(queryVo.getCustomerNumber())){
            queryWrapperX.eq(Returns::getCustomerNumber,queryVo.getCustomerNumber());
        }
        if(StringUtils.isNotBlank(queryVo.getCustomerName())){
            queryWrapperX.eq(Returns::getCustomerName,queryVo.getCustomerName());
        }

        if(StringUtils.isNotBlank(queryVo.getCreateTime())){
            LocalDate requestDate = LocalDate.parse(queryVo.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.ge(Returns::getReturnDate, requestDate);
        }
        if(StringUtils.isNotBlank(queryVo.getEndTime())){
            LocalDate requestDate = LocalDate.parse(queryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //queryWrapperX.le(Returns::getReturnDate, requestDate.plusDays(1));
            queryWrapperX.le(Returns::getReturnDate, requestDate);
        }

        if(StringUtils.isNotBlank(queryVo.getNumber()) && StringUtils.isBlank(queryVo.getCustomerNumber()) && StringUtils.isBlank(queryVo.getCustomerName())
                && StringUtils.isNotBlank(queryVo.getCreateTime()) && StringUtils.isNotBlank(queryVo.getEndTime())){
            queryWrapperX.isNotNull(Returns::getId);
        }
        //queryWrapperX.orderByDesc(Returns::getCreateTime);
        return  selectPage(queryVo,queryWrapperX);
    }



}
