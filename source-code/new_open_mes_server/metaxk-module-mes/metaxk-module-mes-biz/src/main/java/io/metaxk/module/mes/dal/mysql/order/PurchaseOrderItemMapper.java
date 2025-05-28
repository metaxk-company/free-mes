package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.ReceiptItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecordItem;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 15:18
 */
@Mapper
public interface PurchaseOrderItemMapper extends BaseMapperX<PurchaseOrderItem> {


    /**
     *  采购单条件分页查询
     * @param queryVo
     * @return PageResult<ReceiptItem>
     */
    default PageResult<PurchaseOrderItem> selectByReceiptNumber( ReceiptItemQueryVo queryVo) {
        LambdaQueryWrapperX<PurchaseOrderItem> queryWrapperX = new LambdaQueryWrapperX<>();
        //先筛选编号
        if(StringUtils.isNotBlank(queryVo.getReceiptNumber())) {
            queryWrapperX.eq(PurchaseOrderItem::getReceiptNumber, queryVo.getReceiptNumber());
        }
        if (StringUtils.isNotBlank(queryVo.getNumber())) {
            queryWrapperX.eq(PurchaseOrderItem::getItemCode, queryVo.getNumber());
        }
        if (StringUtils.isNotBlank(queryVo.getName())) {
            queryWrapperX.eq(PurchaseOrderItem::getItemName, queryVo.getName());
        }
        if (StringUtils.isNotBlank(queryVo.getModel())) {
            queryWrapperX.eq(PurchaseOrderItem::getModel, queryVo.getModel());
        }
        if (StringUtils.isNotBlank(queryVo.getSpec())) {
            queryWrapperX.eq(PurchaseOrderItem::getSpec, queryVo.getSpec());
        }
        if (StringUtils.isNotBlank(queryVo.getVendor())) {
            queryWrapperX.eq(PurchaseOrderItem::getVendor, queryVo.getVendor());
        }
        if ( StringUtils.isBlank(queryVo.getReceiptNumber()) && StringUtils.isBlank(queryVo.getNumber()) && StringUtils.isBlank(queryVo.getName()) && StringUtils.isBlank(queryVo.getModel()) && StringUtils.isBlank(queryVo.getSpec()) && StringUtils.isBlank(queryVo.getVendor())) {
            queryWrapperX.isNotNull(PurchaseOrderItem::getId);
        }
        if(StringUtils.isBlank(queryVo.getReceiptNumber())){
            queryWrapperX.eq(PurchaseOrderItem::getStatus, "0");
        }
        return  selectPage(queryVo, queryWrapperX);
    }


    default List<PurchaseOrderItem> selectReceiptItemByReceiptNumber(String receiptNumber){
        LambdaQueryWrapperX<PurchaseOrderItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PurchaseOrderItem::getReceiptNumber,receiptNumber);
        return selectList(queryWrapperX);
    }

   default PurchaseOrderItem findPurchaseOrderItem(String itemCode, String receiptNumber){
        return  selectOne(new LambdaQueryWrapperX<PurchaseOrderItem>().eq(PurchaseOrderItem::getItemCode,itemCode).eq(PurchaseOrderItem::getReceiptNumber,receiptNumber));
   }
}
