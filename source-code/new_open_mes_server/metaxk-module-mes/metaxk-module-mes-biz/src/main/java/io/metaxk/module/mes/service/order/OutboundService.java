package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.Label;
import io.metaxk.module.mes.dal.dataobject.order.Outbound;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItem;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItemLabel;

import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 16:41
 */
public interface OutboundService {

    Integer saveOutBound(Outbound orderSaleOutbound);

    PageResult<Outbound> findPage(OutboundQueryVo orderSalePriceQueryVo);

    Outbound findOrderOutboundById(Long id);

    Integer removeOrderOutbound(List<Long> ids);

    Integer updateOrderOutbound(Outbound orderOutbound);

    PageResult<OutBoundSaleResVO> findSaleList(OutBoundSaleReqVO outBoundSaleReqVO);

    List<OutBoundSaleItemResVO> findSaleItemList(OutBoundSaleItemResVO  outBoundSaleItemResVO);

    List<OutBoundVo> findOutBoundList(String number);

    List<Label> findLabelList(String model);

    void outBound(String number);

    List<OutboundItemLabel> findItemById(String saleItemNumber, String number);

    Integer saveOutboundDetail(OutboundItem outboundItem);

    Outbound findOutboundById(Long id);

    void stockReturn(String number);

    List<PrintDataVo> printDataInclTax(String number);

    List<PrintDataVo> printDataNoInclTax(String outBoundNumber);

    List<PrintDataVo> printDataNoPrice(String outBoundNumber);

    List<PrintDataVo> printDataInstruct(String outBoundNumber);

    List<PrintDataVo> printDataPoundScale(String outBoundNumber);

    List<OutBoundSaleItemResVO> findSaleItemAll(OutBoundSaleItemResVO outBoundSaleItemResVO);

    List<Outbound> selectList();

    void scanCodeOutBound(String code, String outBoundNumber, String itemNumber);

    List<Outbound> outBoundList();

    List<OutBoundVo> findOutBoundQuantityList(String model, String spec, String lineType,String itemCode,String otmNumber);

    List<Outbound> findOutBoundBySaleNumber(String saleNumber);

    Integer removeByNumber(String number);

    Outbound findOutboundByNumber(String outboundNumber);

    PageResult<Outbound> findPageNoSale(OutboundQueryVo orderOutboundQueryVo);

    List<OutBoundExcelVo> exportData();

    List<OutBoundAllExcelVo> listAllDataByIds(List<Integer> ids);

    Integer updateOutbound(Outbound outboundByNumber);
}
