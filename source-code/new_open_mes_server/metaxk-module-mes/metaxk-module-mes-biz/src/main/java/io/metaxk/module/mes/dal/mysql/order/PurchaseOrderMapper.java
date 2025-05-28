package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderVo;
import io.metaxk.module.mes.controller.admin.order.vo.ReceiptQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrder;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/18 15:10
 */
@Mapper
public interface PurchaseOrderMapper extends BaseMapperX<PurchaseOrder> {


   default PageResult<PurchaseOrder> findPage(ReceiptQueryVo queryVo){
       LambdaQueryWrapperX<PurchaseOrder> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(queryVo.getNumber())){
           queryWrapperX.eq(PurchaseOrder::getNumber,queryVo.getNumber());
       }
       if(StringUtils.isNotBlank(queryVo.getCreateTime())){
           LocalDate requestDate = LocalDate.parse(queryVo.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
           queryWrapperX.ge(PurchaseOrder::getDeliveryDate, requestDate);
       }
       if(StringUtils.isNotBlank(queryVo.getEndTime())){
           LocalDate requestDate = LocalDate.parse(queryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
           queryWrapperX.le(PurchaseOrder::getDeliveryDate, requestDate);
       }

       if(StringUtils.isNotBlank(queryVo.getVendorName())){
           queryWrapperX.eq(PurchaseOrder::getVendorName,queryVo.getVendorName());
       }
       if(StringUtils.isBlank(queryVo.getNumber()) && StringUtils.isBlank(queryVo.getVendorName()) && StringUtils.isBlank(queryVo.getCreateTime())){
           queryWrapperX.isNotNull(PurchaseOrder::getId);
       }
       return  selectPage(queryVo,queryWrapperX);
   }


  default   PurchaseOrder findPurchaseByNum(String number){
       return  selectOne(new LambdaQueryWrapperX<PurchaseOrder>().eq(PurchaseOrder::getNumber,number));
  }

    List<PurchaseOrderVo> PurchaseOrderPriant(String number);

    List<PurchaseOrderExcelVo> exportData(@Param("number") List<String> number);

    List<PurchaseOrderExcelVo> exportAllData();
}
