package io.metaxk.module.mes.dal.mysql.pro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.ProWorkorderVo;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkOrderQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrder;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 生产工单 Mapper
 * @author 万界星空
 */
@Mapper
public interface WorkOrderMapper extends BaseMapperX<WorkOrder> {


    /**
     * 生产工单条件分页查询
     * @param reqVO
     * @return PageResult<Workorder>
     */
    default PageResult<WorkOrder> selectPage(WorkOrderQueryVo reqVO) {
        LambdaQueryWrapperX<WorkOrder> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(reqVO.getWorkorderCode())){
          queryWrapper.eq(WorkOrder::getWorkorderCode,reqVO.getWorkorderCode());
        }
        if(StringUtils.isNotBlank(reqVO.getWorkorderName())){
            queryWrapper.like(WorkOrder::getWorkorderName,reqVO.getWorkorderName());
        }
        if(StringUtils.isNotBlank(reqVO.getSourceCode())){
            queryWrapper.eq(WorkOrder::getOrderSource,reqVO.getOrderSource());
        }
        if(StringUtils.isNotBlank(reqVO.getProductCode())){
             queryWrapper.eq(WorkOrder::getProductCode,reqVO.getProductCode());
        }
        if(StringUtils.isNotBlank(reqVO.getProductName())){
           queryWrapper.like(WorkOrder::getProductName,reqVO.getProductName());
        }
        if(StringUtils.isNotBlank(reqVO.getClientCode())){
          queryWrapper.eq(WorkOrder::getClientCode,reqVO.getClientCode());
        }
        if(StringUtils.isNotBlank(reqVO.getClientName())){
           queryWrapper.like(WorkOrder::getClientName,reqVO.getClientName());
        }
        if(StringUtils.isNotBlank(reqVO.getRequestDate())){
            LocalDate requestDate = LocalDate.parse(reqVO.getRequestDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapper.eq(WorkOrder::getRequestDate, requestDate);
        }
        if(StringUtils.isNotBlank(reqVO.getOrderDate())){
            LocalDate orderDate = LocalDate.parse(reqVO.getOrderDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapper.eq(WorkOrder::getOrderDate, orderDate);
        }
        if(StringUtils.isNotBlank(reqVO.getProduceDate())){
            LocalDate productDate = LocalDate.parse(reqVO.getProduceDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapper.eq(WorkOrder::getProduceDate, productDate);
        }
        if(StringUtils.isBlank(reqVO.getWorkorderCode()) && StringUtils.isBlank(reqVO.getWorkorderName())
        && StringUtils.isBlank(reqVO.getSourceCode()) && StringUtils.isBlank(reqVO.getProductCode())
                && StringUtils.isBlank(reqVO.getProductName()) &&
                StringUtils.isBlank(reqVO.getClientCode()) && StringUtils.isBlank(reqVO.getClientName())
                && StringUtils.isBlank(reqVO.getRequestDate()) && StringUtils.isBlank(reqVO.getOrderDate()) && StringUtils.isBlank(reqVO.getProduceDate())){
                queryWrapper.isNotNull(WorkOrder::getId);
        }
        return   selectPage(reqVO,queryWrapper);
    }


    /**
     * 查询订单列表
     * @param proWorkorderVo
     * @return List<ProWorkorderVo>
     */
    List<ProWorkorderVo> synchronizeOrders(ProWorkorderVo proWorkorderVo);


    /**
     * 根据订单号查询订单信息
     * @param createReqVO
     * @return Workorder
     */
    default WorkOrder checkWorkorderCodeUnique(WorkOrder createReqVO) {
        return selectOne(new LambdaQueryWrapperX<WorkOrder>()
                .eqIfPresent(WorkOrder::getWorkorderCode, createReqVO.getWorkorderCode())
                .last("LIMIT 1"));
    }


    /**
     * 根据订单号查询订单信息
     * @param workorderCode
     * @return Workorder
     */
   default WorkOrder selectProWorkorderByWorkorderCode(String workorderCode){
       LambdaQueryWrapperX<WorkOrder> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(WorkOrder::getWorkorderCode,workorderCode);
       return  selectOne(queryWrapperX);
   }


    /**
     * 查询同步订单列表
     * @return List<ProWorkorderVo>
     */
    List<ProWorkorderVo> synchronizeOrdersList();


    /**
     * 查询订单列表
     * @param proWorkorderVo
     * @return  List<Workorder>
     */
    default   List<WorkOrder> synchronizeOrdersListAll(ProWorkorderVo proWorkorderVo){
      LambdaQueryWrapperX<WorkOrder> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(proWorkorderVo.getWorkorderCode())){
          queryWrapperX.eq(WorkOrder::getWorkorderCode,proWorkorderVo.getWorkorderCode());
      }

      if(StringUtils.isNotBlank(proWorkorderVo.getRequestDate())){
          queryWrapperX.eq(WorkOrder::getRequestDate,proWorkorderVo.getRequestDate());
      }

      if(StringUtils.isNotBlank(proWorkorderVo.getProductName())){
          queryWrapperX.eq(WorkOrder::getProductName,proWorkorderVo.getProductName());
      }
      if(StringUtils.isBlank(proWorkorderVo.getWorkorderCode()) && StringUtils.isBlank(proWorkorderVo.getStartTime())&&
      StringUtils.isBlank(proWorkorderVo.getRequestDate()) && StringUtils.isBlank(proWorkorderVo.getProductName())){
          queryWrapperX.isNotNull(WorkOrder::getId);
      }
      queryWrapperX.eq(WorkOrder::getAttr1, 1);
      return  selectList(queryWrapperX);
  }


}
