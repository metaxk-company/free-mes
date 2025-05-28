package io.metaxk.module.mes.dal.mysql.wh;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 到货通知明细Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface InboundRecBillMapper extends BaseMapperX<InboundRecBill> {

    default PageResult<InboundRecBill> findPage(InboundRecBillQueryVo queryVo){
        LambdaQueryWrapperX<InboundRecBill> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(queryVo.getNumber())){
            queryWrapperX.eq(InboundRecBill::getNumber,queryVo.getNumber());
        }
        if(StringUtils.isNotBlank(queryVo.getReceiptNumber())){
            queryWrapperX.eq(InboundRecBill::getReceiptNumber,queryVo.getReceiptNumber());
        }
        if(StringUtils.isNotBlank(queryVo.getVendorName())){
            queryWrapperX.eq(InboundRecBill::getVendorName,queryVo.getVendorName());
        }
        if(StringUtils.isNotBlank(queryVo.getWareHouse())){
            queryWrapperX.eq(InboundRecBill::getWareHouse,queryVo.getWareHouse());
        }

        if(StringUtils.isBlank(queryVo.getNumber()) &&  StringUtils.isBlank(queryVo.getReceiptNumber())
                && StringUtils.isBlank(queryVo.getVendorName()) && StringUtils.isNotBlank(queryVo.getWareHouse())){
            queryWrapperX.isNotNull(InboundRecBill::getId);
        }
        return  selectPage(queryVo,queryWrapperX);
    }
}
