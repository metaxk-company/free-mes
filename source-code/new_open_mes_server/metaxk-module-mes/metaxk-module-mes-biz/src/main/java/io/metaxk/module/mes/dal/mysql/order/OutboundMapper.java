package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.Outbound;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;




/**
 * @author 万界星空
 * @time 2023/7/17 16:41
 */
@Mapper
public interface OutboundMapper extends BaseMapperX<Outbound> {

  default PageResult<Outbound> findPage(OutboundQueryVo queryVo){
      LambdaQueryWrapperX<Outbound> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(queryVo.getNumber())){
          queryWrapperX.eq(Outbound::getNumber,queryVo.getNumber());
      }
      if(StringUtils.isNotBlank(queryVo.getSaleNumber())){
          queryWrapperX.eq(Outbound::getSaleNumber,queryVo.getSaleNumber());
      }
      if(StringUtils.isNotBlank(queryVo.getCustomerName())){
          queryWrapperX.like(Outbound::getCustomerName,queryVo.getCustomerName());
      }
      if(StringUtils.isNotBlank(queryVo.getStatus())){
          queryWrapperX.eq(Outbound::getStatus,queryVo.getStatus());
      }
      if(StringUtils.isBlank(queryVo.getNumber())
              && StringUtils.isBlank(queryVo.getSaleNumber())
              && StringUtils.isBlank(queryVo.getCustomerName())
              && StringUtils.isBlank(queryVo.getStatus())){
          queryWrapperX.isNotNull(Outbound::getId);
      }
      queryWrapperX.eq(Outbound::getIsSales,"1");
      return  selectPage(queryVo,queryWrapperX);
  }



    List<OutBoundVo> findOutBoundList(String number);

    List<OutBoundSaleItemResVO> findSaleItemByNum(@Param("saleNumber") String saleNumber,@Param("lineType")  String lineType, @Param("model")  String model, @Param("spec")  String spec,@Param("customerNumber")  String customerNumber);

    List<PrintDataVo> printDataInclTax(String number);

    List<PrintDataVo> printDataNoInclTax(String outBoundNumber);

    List<PrintDataVo> printDataNoPrice(String outBoundNumber);


    List<PrintDataVo> printDataInstruct(String outBoundNumber);

    List<PrintDataVo> printDataPoundScale(String outBoundNumber);

   default Outbound findOutBoundByNum(String outboundNumber){
       LambdaQueryWrapperX<Outbound> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(Outbound::getNumber,outboundNumber);
       return  selectOne(queryWrapperX);
   }

    List<OutBoundSaleItemResVO> findSaleItemAll(OutBoundSaleItemResVO outBoundSaleItemResVO);

    List<OutBoundVo> findOutBoundQuantityList(@Param("model") String model,@Param("spec") String spec, @Param("lineType")String lineType,@Param("itemCode") String itemCode,@Param("otmNumber")String otmNumber);

   default PageResult<Outbound> findPageNoSale(OutboundQueryVo queryVo){
       LambdaQueryWrapperX<Outbound> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(queryVo.getNumber())){
           queryWrapperX.eq(Outbound::getNumber,queryVo.getNumber());
       }
       if(StringUtils.isNotBlank(queryVo.getSaleNumber())){
           queryWrapperX.eq(Outbound::getSaleNumber,queryVo.getSaleNumber());
       }
       if(StringUtils.isNotBlank(queryVo.getCustomerName())){
           queryWrapperX.like(Outbound::getCustomerName,queryVo.getCustomerName());
       }
       if(StringUtils.isNotBlank(queryVo.getStatus())){
           queryWrapperX.eq(Outbound::getStatus,queryVo.getStatus());
       }
       if(StringUtils.isBlank(queryVo.getNumber()) && StringUtils.isBlank(queryVo.getSaleNumber()) && StringUtils.isBlank(queryVo.getCustomerName()) && StringUtils.isBlank(queryVo.getStatus())){
           queryWrapperX.isNotNull(Outbound::getId);
       }
       queryWrapperX.eq(Outbound::getIsSales,"0");
       return  selectPage(queryVo,queryWrapperX);
   }

    @Select("<script>" +
            "SELECT oo.number,oo.outbound_total_price,oo.customer_name,oo.is_tax,oo.status,oo.send_out,oo.no_send,oo.remark,ooi.number as item_Number,ooi.sale_number,ooi.model,ooi.customer_name as item_customer_name,ooi.spec,ooi.price,ooi.line_type,ooi.customer_code,ooi.color,ooi.panhao,ooi.total_price,ooi.unit,ooi.quantity,ooi.send_out as item_send_out,ooi.no_send as item_no_send,ooi.total_weight,ooi.tare,ooi.total_tare,ooi.pieces,ooi.remark as item_remark\n" +
            "FROM order_outbound AS oo\n" +
            "LEFT JOIN order_outbound_item AS ooi ON oo.number = ooi.outbound_number\n" +
            "<if test='ids.size() != 0'>" +
            "WHERE oo.id IN\n" +
            "<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'>#{id}</foreach>\n" +
            "</if>" +
            "</script>")
    List<OutBoundAllExcelVo> exportAllByIds(@Param("ids") List<Integer> ids);
}
