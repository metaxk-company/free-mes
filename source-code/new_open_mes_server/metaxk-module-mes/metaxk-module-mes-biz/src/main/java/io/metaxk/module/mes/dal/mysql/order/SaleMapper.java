package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.Sale;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 销售订单Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface SaleMapper extends BaseMapperX<Sale> {
    default PageResult<Sale> findPage(SaleQueryVo orderSaleQueryVo) {

        LambdaQueryWrapperX<Sale> queryWrapperX = new LambdaQueryWrapperX<>();

        if(StringUtils.isNotBlank(orderSaleQueryVo.getSaleNumber())){
            queryWrapperX.like(Sale::getNumber, orderSaleQueryVo.getSaleNumber());
        }
        if(StringUtils.isNotBlank(orderSaleQueryVo.getCustomerName())){
            queryWrapperX.like(Sale::getCustomerName, orderSaleQueryVo.getCustomerName());
        }
        if(StringUtils.isNotBlank(orderSaleQueryVo.getStatus())){
            queryWrapperX.eq(Sale::getStatus, orderSaleQueryVo.getStatus());
        }
        if(StringUtils.isNotBlank(orderSaleQueryVo.getStartTime())){
            LocalDate requestDate = LocalDate.parse(orderSaleQueryVo.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.ge(Sale::getCreateTime, requestDate);
        }
        if(StringUtils.isNotBlank(orderSaleQueryVo.getEndTime())){
            LocalDate requestDate = LocalDate.parse(orderSaleQueryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.le(Sale::getCreateTime, requestDate.plusDays(1));
        }

        if(StringUtils.isBlank(orderSaleQueryVo.getSaleNumber())
                && StringUtils.isBlank(orderSaleQueryVo.getCustomerName())
                && StringUtils.isBlank(orderSaleQueryVo.getStatus())
                && StringUtils.isBlank(orderSaleQueryVo.getStartTime())
                && StringUtils.isBlank(orderSaleQueryVo.getEndTime())){
            queryWrapperX.isNotNull(Sale::getId);
        }

        return  selectPage(orderSaleQueryVo, queryWrapperX);

    }

    List<SaleProgressResVo> findProgressBySaleNumber( SaleProgressResVo saleProgressResVo);


    List<ProductResVo> findProductList(  ProductResVo pv);





   default Sale findSaleByNumber(String saleNumber){
       LambdaQueryWrapperX<Sale> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(Sale::getNumber,saleNumber);
       return  selectOne(queryWrapperX);
   }

    List<PrintSaleDateVo> printPurchaseOrder(String saleNumber);

    List<ProductResVo> findProductListAll(ProductResVo pv);

   default PageResult<Sale> saleCountPage(SaleCountQueryVo queryVo){
       LambdaQueryWrapperX<Sale> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(queryVo.getCustomerNumber())){
           queryWrapperX.eq(Sale::getCustomerNumber,queryVo.getCustomerNumber());
       }
       if(StringUtils.isNotBlank(queryVo.getCustomerName())){
           queryWrapperX.eq(Sale::getCustomerName,queryVo.getCustomerName());
       }
       if(StringUtils.isBlank(queryVo.getCustomerNumber()) && StringUtils.isBlank(queryVo.getCustomerName())){
           queryWrapperX.isNotNull(Sale::getId);
       }
       return  selectPage(queryVo,queryWrapperX);
   }

    List<SaleCountVo> findCountOutBound(SaleCountQueryVo saleCountQueryVo);

   default   List<Sale> selectByCustomerNameAndNum(String customerNumber, String customerName){
       LambdaQueryWrapperX<Sale> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(customerNumber)){
           queryWrapperX.eq(Sale::getCustomerNumber,customerNumber);
       }
       if(StringUtils.isNotBlank(customerName)){
           queryWrapperX.eq(Sale::getCustomerName,customerName);
       }
       return  selectList(queryWrapperX);
   }

   @Select("<script>" +
           "SELECT os.number,os.customer_number,os.customer_name,os.customer_order_number,os.quantity,IF(price_model=\"weight\", \"重量\", \"件数\") as price_model,IF(os.is_tax=\"Y\", os.include_tax, os.no_include_tax) as price,os.status,os.create_time,os.update_time,os.remark,osi.product_number,osi.line_type,osi.model,osi.quantity as item_quantity,osi.spec,osi.raw_price,osi.processing_fee,osi.price as item_price,osi.unit,osi.stocks,osi.color,osi.panhao,osi.customer_code,osi.inventory_number,osi.warrant_number,osi.remark as item_remark\n" +
           "FROM order_sale AS os\n" +
           "LEFT JOIN order_sale_item AS osi ON os.number = osi.sale_number\n" +
           "<if test='ids.size() != 0'>" +
           "WHERE os.id IN\n" +
           "<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'>#{id}</foreach>\n" +
           "</if>" +
           "</script>")
    List<SaleAllExcelVo> exportAllByIds(@Param("ids") List<Integer> ids);
}
