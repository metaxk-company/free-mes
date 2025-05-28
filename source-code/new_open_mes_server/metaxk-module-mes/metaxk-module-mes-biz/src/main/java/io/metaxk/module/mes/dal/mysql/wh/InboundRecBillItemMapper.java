package io.metaxk.module.mes.dal.mysql.wh;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBillItem;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 到货通知Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface InboundRecBillItemMapper extends BaseMapperX<InboundRecBillItem> {

    default PageResult<InboundRecBillItem> findPage(InboundRecBillItemQueryVo queryVo,String flag){
        LambdaQueryWrapperX<InboundRecBillItem> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(queryVo.getRecNumber())){
            queryWrapperX.eq(InboundRecBillItem::getRecNumber,queryVo.getRecNumber());
        }
        if(StringUtils.isNotBlank(queryVo.getItemCode())){
            queryWrapperX.eq(InboundRecBillItem::getItemCode,queryVo.getItemCode());
        }
        if(StringUtils.isNotBlank(queryVo.getItemName())){
            queryWrapperX.eq(InboundRecBillItem::getItemName,queryVo.getItemName());
        }
        if(StringUtils.isNotBlank(queryVo.getModel())){
            queryWrapperX.eq(InboundRecBillItem::getModel,queryVo.getModel());
        }
        if(StringUtils.isNotBlank(queryVo.getSpec())){
            queryWrapperX.eq(InboundRecBillItem::getSpec,queryVo.getSpec());
        }

        if(StringUtils.isBlank(queryVo.getRecNumber()) && StringUtils.isNotBlank(queryVo.getItemCode()) &&  StringUtils.isBlank(queryVo.getItemName()) && StringUtils.isBlank(queryVo.getModel())){
            queryWrapperX.isNotNull(InboundRecBillItem::getId);
        }

        if ("2".equals(flag)){
            queryWrapperX.eq(InboundRecBillItem::getState, "1");
            queryWrapperX.eq(InboundRecBillItem::getStatus, "0");
        }

        return  selectPage(queryVo,queryWrapperX);
    }

    List<InboundRecBillItemVo> findInboundRecBillItem(@Param("receiptNumber")String receiptNumber);

    default InboundRecBillItem findInboundRecBillItem(String itemCode, String recNumber){
        LambdaQueryWrapperX<InboundRecBillItem> lambdaQueryWrapperX =  new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(InboundRecBillItem::getItemCode,itemCode);
        lambdaQueryWrapperX.eq(InboundRecBillItem::getRecNumber,recNumber);
        return selectOne(lambdaQueryWrapperX);
    }
}
