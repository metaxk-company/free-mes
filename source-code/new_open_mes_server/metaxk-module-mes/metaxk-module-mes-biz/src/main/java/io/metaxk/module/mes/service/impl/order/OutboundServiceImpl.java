package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.order.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.*;
import io.metaxk.module.mes.dal.mysql.md.ClientMapper;
import io.metaxk.module.mes.dal.mysql.order.*;
import io.metaxk.module.mes.service.order.LabelService;
import io.metaxk.module.mes.service.order.OutboundItemLabelService;
import io.metaxk.module.mes.service.order.OutboundService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.StringUtils;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 16:42
 */
@Service
public class OutboundServiceImpl implements OutboundService {

    @Resource
    private OutboundMapper outboundMapper;

    @Resource
    private OutboundItemMapper outboundItemMapper;

    @Resource
    private AutoCodeUtil  autoCodeUtil;

    @Resource
    private SaleMapper saleMapper;

    @Resource
    private SaleItemMapper saleItemMapper;


    @Resource
    private LabelMapper  labelMapper;


    @Resource
    private LabelService labelService;

    @Resource
    private OutboundItemLabelMapper outboundItemLabelMapper;

    private OutboundItemLabelService outboundItemLabelService;

    @Resource
    private ClientMapper clientMapper;



    @Override
    public Integer saveOutBound(Outbound orderOutbound) {
        BigDecimal totalNoSend = new BigDecimal(BigInteger.ZERO);
        String customerName = null; String saleNumber = null;
        for(OutboundItem orderOutboundItem : orderOutbound.getOrderOutboundItemList()) {
            customerName = orderOutboundItem.getCustomerName();   saleNumber = orderOutboundItem.getSaleNumber();
            if(orderOutboundItem.getNoSend()!=null) {
                totalNoSend = totalNoSend.add(orderOutboundItem.getNoSend());
            }
            OutboundItem outboundItem = new OutboundItem();
            outboundItem.setNumber(autoCodeUtil.genSerialCode(UserConstants.ORDER_ITEM_OUTBOUND,null));
            outboundItem.setOutboundNumber(orderOutbound.getNumber())
                    .setItemCode(orderOutboundItem.getItemCode()).setModel(orderOutboundItem.getModel())
                    .setSpec(orderOutboundItem.getSpec()).setPrice(orderOutboundItem.getPrice())
                    .setLineType(orderOutboundItem.getLineType()).setCustomerCode(orderOutboundItem.getCustomerCode())
                    .setColor(orderOutboundItem.getColor()).setPanhao(orderOutboundItem.getPanhao())
                    .setTotalPrice(orderOutboundItem.getTotalPrice()).setUnit(orderOutboundItem.getUnit())
                    .setQuantity(orderOutboundItem.getQuantity()).setSendOut(orderOutboundItem.getSendOut()).setNoSend(orderOutboundItem.getNoSend())
                    .setTotalWeight(orderOutboundItem.getTotalWeight()).setTare(orderOutboundItem.getTare())
                    .setTotalTare(orderOutboundItem.getTotalTare()).setPieces(orderOutboundItem.getPieces())
                    .setSaleItemNumber(orderOutboundItem.getSaleItemNumber()).setCustomerName(orderOutboundItem.getCustomerName())
                    .setRemark(orderOutboundItem.getRemark()).setSaleNumber( saleItemMapper.findSaleItemBySaleNumber( outboundItem.getSaleItemNumber()).getSaleNumber());
            outboundItemMapper.insert(outboundItem);


            SaleItem saleItem = saleItemMapper.findSaleItemBySaleNumber(orderOutboundItem.getSaleItemNumber());
            saleItem.setStatus("已完成");
            saleItemMapper.updateById(saleItem);
        }
        if(orderOutbound.getSaleNumber() == null){
            orderOutbound.setIsSales("0");
        }else {
            orderOutbound.setIsSales("1");
        }
        Sale sale = saleMapper.findSaleByNumber(saleNumber);
        orderOutbound.setNoSend(totalNoSend).setQuantity(orderOutbound.getNoSend());
        orderOutbound.setCustomerName(customerName).setCustomerNumber(clientMapper.findCodeByName(customerName)).setSaleNumber(sale.getNumber()).setCustomerOrderNumber(sale.getCustomerOrderNumber())
                     .setDeliveryDate(sale.getDeliveryDate()).setIsTax(sale.getIsTax());
        return  outboundMapper.insert(orderOutbound);
    }




    @Override
    public PageResult<Outbound> findPage(OutboundQueryVo orderSalePriceQueryVo) {
        return outboundMapper.findPage(orderSalePriceQueryVo);
    }


    @Override
    public Outbound findOrderOutboundById(Long id) {
        Outbound outbound = outboundMapper.selectById(id);

        LambdaQueryWrapperX<OutboundItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItem::getOutboundNumber,outbound.getNumber());
        List<OutboundItem> outboundItemList = outboundItemMapper.selectList(queryWrapperX);
        outbound.setOrderOutboundItemList(outboundItemList);
        return  outbound;
    }




    @Override
    public Integer removeOrderOutbound(List<Long> ids) {
        return outboundMapper.deleteBatchIds(ids);
    }






    @Override
    public Integer updateOrderOutbound(Outbound orderOutbound) {
        List<OutboundItem> outboundItemList = outboundItemMapper.selectList(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getOutboundNumber, orderOutbound.getNumber()));
        for(OutboundItem outboundItem:outboundItemList){
            outboundItemMapper.delete(new LambdaQueryWrapperX<OutboundItem>().eq(OutboundItem::getOutboundNumber,outboundItem.getOutboundNumber()));
        }
        BigDecimal totalSendOut = new BigDecimal(BigInteger.ZERO);
        for(OutboundItem orderOutboundItem : orderOutbound.getOrderOutboundItemList()) {
            OutboundItem outboundItem = new OutboundItem();
            outboundItem.setNumber(autoCodeUtil.genSerialCode(UserConstants.ORDER_ITEM_OUTBOUND,null));
            outboundItem.setOutboundNumber(orderOutbound.getNumber())
                    .setItemCode(orderOutboundItem.getItemCode()).setModel(orderOutboundItem.getModel())
                    .setSpec(orderOutboundItem.getSpec()).setPrice(orderOutboundItem.getPrice())
                    .setLineType(orderOutboundItem.getLineType()).setCustomerCode(orderOutboundItem.getCustomerCode())
                    .setColor(orderOutboundItem.getColor()).setPanhao(orderOutboundItem.getPanhao())
                    .setTotalPrice(orderOutboundItem.getTotalPrice()).setUnit(orderOutboundItem.getUnit())
                    .setQuantity(orderOutboundItem.getQuantity()).setSendOut(orderOutboundItem.getSendOut()).setNoSend(orderOutboundItem.getNoSend())
                    .setTotalWeight(orderOutboundItem.getTotalWeight()).setTare(orderOutboundItem.getTare()).setCustomerName(orderOutboundItem.getCustomerName())
                    .setPieces(orderOutboundItem.getPieces()).setSaleItemNumber(orderOutboundItem.getSaleItemNumber()).setSaleNumber(saleItemMapper.findSaleItemBySaleNumber(outboundItem.getSaleItemNumber()).getSaleNumber())
                    .setRemark(orderOutboundItem.getRemark());
            BigDecimal bigDecimal = new BigDecimal(outboundItem.getPieces());

            outboundItem.setTotalTare(orderOutboundItem.getTare().multiply(bigDecimal));
            //新加
            totalSendOut = totalSendOut.add(outboundItem.getSendOut());

            BigDecimal increaseAmount = outboundItem.getQuantity().multiply(new BigDecimal("0.20"));
            // 将增加量加到原有数量上
            BigDecimal newQuantity = outboundItem.getQuantity().add(increaseAmount);
            if (outboundItem.getSendOut().compareTo(newQuantity) > 0) {
                outboundItem.setStatus("已完成");
                SaleItem saleItem = saleItemMapper.findSaleItemBySaleNumber(outboundItem.getSaleItemNumber());
                saleItem.setOutboundStatus("1");
                saleItemMapper.updateById(saleItem);



            }
            outboundItem.setNoSend(outboundItem.getQuantity().subtract(outboundItem.getSendOut()));
            outboundItemMapper.insert(outboundItem);

            String saleItemNumber = outboundItem.getSaleItemNumber();
            OutboundItem outboundItemBySaleItemNum = outboundItemMapper.findNumberBySaleItemNum(saleItemNumber);
            if(outboundItemBySaleItemNum != null) {
                List<OutboundItemLabel> outboundItemLabel = outboundItemLabelMapper.findboundItemLabeBySaleItemNum(saleItemNumber);
                if (outboundItemLabel.size() > 0) {
                    for(OutboundItemLabel label:outboundItemLabel) {
                        OutboundItem outboundItem1 = outboundItemMapper.findNumberBySaleItemNum(saleItemNumber);
                        OutboundItemLabel outboundItemLabelDo = outboundItemLabelMapper.selectOne(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getOutBoundLabelId,label.getOutBoundLabelId()));
                        outboundItemLabelDo.setOutboundItemNumber(outboundItem1.getNumber());
                        outboundItemLabelMapper.update(outboundItemLabelDo,new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getOutBoundLabelId,label.getOutBoundLabelId()));
                    }
                }
            }




        }
        //获取出库的数量  当扫码已发数量超过出库数量+出库数量乘以百分之二十之后修改出库状态为已出库
        List<OutboundItem> itemList = outboundItemMapper.findOutBoundItemByNum(orderOutbound.getNumber());
        if(itemList.size() >0) {
            boolean allCompleted = true;
            for (OutboundItem item : itemList) {
                if (!"已完成".equals(item.getStatus())) {
                    allCompleted = false;
                    break;
                }
            }
            if (allCompleted) {
                orderOutbound.setStatus("已出库");
                List<OutboundItem> outBoundItemByNum = outboundItemMapper.findOutBoundItemByNum(orderOutbound.getNumber());
                for (OutboundItem outboundItem : outBoundItemByNum) {
                    String saleNumber = outboundItem.getSaleNumber();
                    List<SaleItem> saleItemList = saleItemMapper.findSaleItemListBySaleNumber(saleNumber);
                    boolean outBoundStatus = true;
                    for (SaleItem item : saleItemList) {
                        if (!"1".equals(item.getOutboundStatus())) {
                            outBoundStatus = false;
                            break;
                        }
                    }
                    if (outBoundStatus) {
                        Sale sale = saleMapper.findSaleByNumber(outboundItem.getSaleNumber());
                        sale.setId(sale.getId());
                        sale.setStatus("已完成");
                        saleMapper.updateById(sale);
                    }
                }
            }
        }
        return  outboundMapper.updateById(orderOutbound);
    }






    @Override
    public PageResult<OutBoundSaleResVO> findSaleList(OutBoundSaleReqVO outBoundSaleReqVO) {
        List<OutBoundSaleResVO> outBoundSaleResVOList = new ArrayList<>();
        List<Sale> saleList = new ArrayList<>();
        LambdaQueryWrapperX<Sale> queryWrapperX = new LambdaQueryWrapperX();
        if (StringUtils.isNotBlank(outBoundSaleReqVO.getCustomerName()))
            queryWrapperX.eq(Sale::getCustomerName, outBoundSaleReqVO.getCustomerName());
        if (StringUtils.isNotBlank(outBoundSaleReqVO.getCustomerNumber()))
            queryWrapperX.eq(Sale::getCustomerNumber, outBoundSaleReqVO.getCustomerNumber());
         for (Sale sale : saleMapper.selectPage(outBoundSaleReqVO, queryWrapperX).getList()) {
            if (("已完成").equals(sale.getStatus())) {
                continue;
            }
            for (SaleItem saleItem : saleItemMapper.selectList()) {
                if (sale.getNumber().equals(saleItem.getSaleNumber())) {
                    sale.setSaleNumber(saleItem.getSaleNumber());
                    if (sale.getItemList() == null) {
                        sale.setItemList(new ArrayList<>());
                    }
                    sale.getItemList().add(saleItem);

                }

            }
            saleList.add(sale);
        }
        try {
            for (int i=0; i<saleList.size(); ++i) {
                outBoundSaleResVOList.add(new OutBoundSaleResVO());
                BeanUtils.copyProperties(saleList.get(i), outBoundSaleResVOList.get(i));
                outBoundSaleResVOList.get(i).setCustomerNumber(saleList.get(i).getCustomerNumber());
                outBoundSaleResVOList.get(i).setSendOut(BigDecimal.valueOf(0)).setNoSend(saleList.get(i).getQuantity());
                if (saleList.get(i).getIsTax().equals("Y")) {
                    outBoundSaleResVOList.get(i).setTotalPrice(saleList.get(i).getIncludeTax());
                } else {
                    outBoundSaleResVOList.get(i).setTotalPrice(saleList.get(i).getNoIncludeTax());
                }
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return new PageResult<>(outBoundSaleResVOList, (long) outBoundSaleResVOList.size());
    }



    @Override
    public List<OutBoundSaleItemResVO> findSaleItemList(OutBoundSaleItemResVO  outBoundSaleItemResVO) {
        List<OutBoundSaleItemResVO> saleItemResVOList  = outboundMapper.findSaleItemByNum(outBoundSaleItemResVO.getSaleNumber(),outBoundSaleItemResVO.getLineType(),outBoundSaleItemResVO.getModel(),outBoundSaleItemResVO.getSpec(),outBoundSaleItemResVO.getCustomerNumber());
        for(OutBoundSaleItemResVO boundSaleItemResVO:saleItemResVOList){
            boundSaleItemResVO.setTotalTareWeight(new BigDecimal(0));
            boundSaleItemResVO.setTare(new BigDecimal(0));
        }
        return saleItemResVOList;
    }



    @Override
    public List<OutBoundVo> findOutBoundList(String number) {
        return outboundMapper.findOutBoundList(number);
    }



    @Override
    public List<Label> findLabelList(String model) {
        LambdaQueryWrapperX<Label> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Label::getModel,model);
        return labelMapper.selectList(queryWrapperX);
    }



   // 原先的已出库
   /* @Override
    public CommonResult outBound(String number) {
        Outbound outbound = outboundMapper.selectOne(new LambdaQueryWrapperX<Outbound>().eq(Outbound::getNumber,number));
        outbound.setStatus("已出库");
        outboundMapper.updateById(outbound);
        //根据出库单号查询出库明细数据
        List<OutboundItem> outboundItemList = outboundItemMapper.findOutBoundItemByNum(outbound.getNumber());
        //遍历出库明细修改销售订单明细的状态信息
        for(OutboundItem outboundItem:outboundItemList){
            SaleItem saleItem =  saleItemMapper.findSaleItemBySaleNumber(outboundItem.getSaleItemNumber());
            saleItem.setStatus("1");
            saleItemMapper.updateById(saleItem);
            //根据销售订单查询全部明细
            List<SaleItem> saleItemList = saleItemMapper.findSaleItemListBySaleNumber(outbound.getSaleNumber());
            //执行判断如果明细中所有的状态不是1跳出
            boolean allStatusOne = true;
            for(SaleItem item:saleItemList){
                if (!"1".equals( item.getStatus())) {
                    allStatusOne = false;
                    break;
                }
            }
            //执行判断如果明细中所有的状态是1修改销售单状态
            if (allStatusOne) {
                Outbound outbound1 =  outboundMapper.findOutBoundByNum(outboundItem.getOutboundNumber());
                Sale sale =  saleMapper.findSaleByNumber(outbound1.getSaleNumber());
                sale.setStatus("已完成");
                saleMapper.updateById(sale);
            }
        }
        return  null;
    }*/






    @Override
    public void outBound(String number) {

        List<OutboundItem> outboundItemList = outboundItemMapper.findOutBoundItemByNum(number);
        BigDecimal totalSend = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalNoSend = new BigDecimal(BigInteger.ZERO);
        BigDecimal countWeight = new BigDecimal(BigInteger.ZERO);
        BigDecimal countPrice = new BigDecimal(BigInteger.ZERO);
        for(OutboundItem outboundItem:outboundItemList){
            OutboundItem item = outboundItemMapper.selectById(outboundItem.getId());
            item.setStatus("已完成").setSendOut(item.getQuantity()).setNoSend(item.getQuantity().subtract(item.getSendOut()))
                    .setTotalWeight(item.getSendOut())
                    .setTotalPrice(item.getPrice().multiply(item.getSendOut()));
            outboundItemMapper.updateById(item);
            totalSend = totalSend.add(item.getSendOut());
            totalNoSend = totalNoSend.add(item.getNoSend());
            countWeight = countWeight.add(item.getTotalWeight());
            countPrice = countPrice.add(item.getTotalPrice());
            SaleItem saleItem = saleItemMapper.findSaleItemBySaleNumber(item.getSaleItemNumber());
            saleItem.setOutboundStatus("1");
            saleItemMapper.updateById(saleItem);
            Sale sale = saleMapper.findSaleByNumber(saleItem.getSaleNumber());
            List<SaleItem> saleItemList = saleItemMapper.findSaleItemAllBySaleNumber(sale.getNumber());
            boolean allCompleted  = true;
            for (SaleItem itemDo : saleItemList) {
                if (!"1".equals(itemDo.getOutboundStatus())) {
                    allCompleted = false;
                    break;
                }
            }
            if (allCompleted) {
                Sale saleDo = saleMapper.findSaleByNumber(outboundItem.getSaleNumber());
                saleDo.setStatus("已完成") ;
                saleMapper.updateById(saleDo);
            }
        }
        Outbound outbound = outboundMapper.findOutBoundByNum(number);
        outbound.setStatus("已出库").setNoSend(totalNoSend).setSendOut(totalSend).setTotalWeight(String.valueOf(countWeight)).setTotalPrice(countPrice);;
        outboundMapper.updateById(outbound);

    }





    @Override
    public List<OutboundItemLabel> findItemById(String saleItemNumber, String number) {
        return  outboundItemLabelMapper.selectList(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getSaleItemNumber,saleItemNumber).eq(OutboundItemLabel::getOutboundNumber,number));
    }




    @Override
    public Integer saveOutboundDetail(OutboundItem outboundItem) {
        if(outboundItem.getLabelList().size() >0) {
          for (OutboundItemLabel labelDo : outboundItem.getLabelList()) {
            if(outboundItemLabelMapper.selectLabel(labelDo.getId(),labelDo.getSaleItemNumber()).size() == 0) {
                OutboundItemLabel outboundItemLabel = new OutboundItemLabel();
                    BeanUtils.copyProperties(labelDo,outboundItemLabel);
                    outboundItemLabel.setOutboundNumber(outboundItem.getNumber());
                    outboundItemLabel.setWhLabelId(labelDo.getId()).setId(labelDo.getId());
                    outboundItemLabel.setSaleItemNumber(outboundItem.getSaleItemNumber());
                //    outboundItemLabel.setOutboundItemNumber( outboundItemMapper.findNumberBySaleItemNum(outboundItemLabel.getSaleItemNumber()).getNumber());
                    outboundItemLabelMapper.insert(outboundItemLabel);
             }
          }
      }
        return null;
    }




    @Override
    public Outbound findOutboundById(Long id) {
        return outboundMapper.selectOne(new LambdaQueryWrapperX<Outbound>().eq(Outbound::getId,id));
    }



    @Override
    public void stockReturn(String number) {
        List<OutboundItem> outboundItemList = outboundItemMapper.findOutBoundItemByNum(number);
        BigDecimal totalSend = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalNoSend = new BigDecimal(BigInteger.ZERO);
        BigDecimal countWeight = new BigDecimal(BigInteger.ZERO);
        BigDecimal countPrice = new BigDecimal(BigInteger.ZERO);
        int  countPieces = 0;
        for(OutboundItem outboundItem:outboundItemList){
            List<OutboundItemLabel> outboundItemLabelList = outboundItemLabelMapper.findboundItemLabeByItemNum(outboundItem.getNumber());
         //   BigDecimal totalTare = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalWeight = new BigDecimal(BigInteger.ZERO);
            if(outboundItemLabelList.size() > 0){
               for(OutboundItemLabel outboundItemLabel :outboundItemLabelList){
            //       totalTare = totalTare.add(outboundItemLabel.getTotalTare());
                   totalWeight = totalWeight.add(outboundItemLabel.getTotalHeight());

                   Label label = labelService.findByBarCode(outboundItemLabel.getBarCode());
                   label.setStatus("1");
                   labelService.updateLabel(label);

                   outboundItemLabelMapper.delete(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getBarCode,outboundItemLabel.getBarCode()));
               }
           }

            OutboundItem item = outboundItemMapper.selectById(outboundItem.getId());
           //两种情况  1、在页面上直接出库时label表是没有数据的所有totalWeight为 0
           //2、出库完之后退库的，出库label表是有数据的所以才会有以下的判断
            if (totalWeight.compareTo(BigDecimal.ZERO) == 0) {
                item.setSendOut(item.getQuantity().subtract(item.getSendOut()))
                        .setNoSend(item.getQuantity().subtract(item.getSendOut()))
                        .setTotalWeight(item.getQuantity().subtract(item.getTotalWeight()))
                        .setTotalPrice(item.getSendOut().multiply(item.getPrice()))
                        .setPieces(String.valueOf(outboundItemLabelList.size() - Integer.valueOf(item.getPieces()))).setStatus("未完成");
                BigDecimal bigDecimal = new BigDecimal(item.getPieces());
                item.setTotalTare(item.getTare().multiply(bigDecimal));
            }else {
                item.setSendOut(totalWeight.subtract(item.getSendOut()))
                        .setNoSend(item.getQuantity().subtract(item.getSendOut()))
                        .setTotalWeight(totalWeight.subtract(item.getTotalWeight()))
                        .setTotalPrice(item.getSendOut().multiply(item.getPrice()))
                        .setPieces(String.valueOf(outboundItemLabelList.size() - Integer.valueOf(item.getPieces()))).setStatus("未完成");
                BigDecimal bigDecimal = new BigDecimal(item.getPieces());
                item.setTotalTare(item.getTare().multiply(bigDecimal));
            }

            outboundItemMapper.updateById(item);

            totalSend = totalSend.add(item.getSendOut());
            totalNoSend = totalNoSend.add(item.getNoSend());
            countWeight = countWeight.add(item.getTotalWeight());
            countPieces += Integer.valueOf(item.getPieces());
            countPrice = countPrice.add(item.getTotalPrice());
            SaleItem saleItem = saleItemMapper.findSaleItemBySaleNumber(item.getSaleItemNumber());
            saleItem.setStatus("未完成").setOutboundStatus("0");
            saleItemMapper.updateById(saleItem);

            Sale sale = saleMapper.findSaleByNumber(saleItem.getSaleNumber());
            List<SaleItem> saleItemList = saleItemMapper.findSaleItemAllBySaleNumber(sale.getNumber());
            boolean allCompleted  = true;
            for (SaleItem itemDo : saleItemList) {
                if ("未完成".equals(itemDo.getStatus())) {
                    allCompleted = true;
                }
            }
            if (allCompleted) {
                Sale saleDo = saleMapper.findSaleByNumber(outboundItem.getSaleNumber());
                saleDo.setStatus("未完成") ;
                saleMapper.updateById(saleDo);
            }
        }
            Outbound outbound = outboundMapper.findOutBoundByNum(number);
            outbound.setStatus("待出库").setNoSend(totalNoSend).setSendOut(totalSend).setTotalWeight(String.valueOf(countWeight)).setPieces(String.valueOf(countPieces)).setTotalPrice(countPrice);;
            outboundMapper.updateById(outbound);
    }







    @Override
    public List<PrintDataVo> printDataInclTax(String number) {
        return outboundMapper.printDataInclTax(number);
    }

    @Override
    public List<PrintDataVo> printDataNoInclTax(String outBoundNumber) {
        return outboundMapper.printDataNoInclTax(outBoundNumber);
    }

    @Override
    public List<PrintDataVo> printDataNoPrice(String outBoundNumber) {
        return outboundMapper.printDataNoPrice(outBoundNumber);
    }

    @Override
    public List<PrintDataVo> printDataInstruct(String outBoundNumber) {
        return outboundMapper.printDataInstruct(outBoundNumber);
    }

    @Override
    public List<PrintDataVo> printDataPoundScale(String outBoundNumber) {
        return outboundMapper.printDataPoundScale(outBoundNumber);
    }

    @Override
    public List<OutBoundSaleItemResVO> findSaleItemAll(OutBoundSaleItemResVO outBoundSaleItemResVO) {
        return outboundMapper.findSaleItemAll(outBoundSaleItemResVO);
    }

    @Override
    public List<Outbound> selectList() {
        return outboundMapper.selectList();
    }




    @Override
    public void scanCodeOutBound(String code, String outBoundNumber, String itemNumber) {
        //根据传递过来的二维码查询入库表(可以增加条件)
       List<Label> labelList =  labelMapper.findLabelByCode(code);

        OutboundItem outboundItem =  outboundItemMapper.findOutBoundItemByNumAndOutNum(outBoundNumber,itemNumber);
        //将总净重相加就是已发数量
        int totalSend = 0,totalTare = 0;
        for(Label label:labelList){
          //  totalSend+=(int) Double.parseDouble(label.getTotalHeight());
          //  totalTare +=(int) Double.parseDouble(label.getTotalTare());

            //保存表格数据到当前出库明细的label中
            OutboundItemLabel outboundItemLabel = new OutboundItemLabel();
            BeanUtils.copyProperties(label,outboundItemLabel);
            outboundItemLabel.setWhLabelId(label.getId()).setSaleItemNumber(null).setOutboundNumber(outBoundNumber);
            outboundItemLabelMapper.insert(outboundItemLabel);
        }

        //查询出库明细表----设置已发
          outboundItem.setSendOut(BigDecimal.valueOf(totalSend)).setNoSend(outboundItem.getQuantity().subtract(outboundItem.getSendOut())).setTotalWeight(BigDecimal.valueOf(totalSend)).setTotalTare(BigDecimal.valueOf(totalTare))  .setPieces(String.valueOf(labelList.size()))
                      .setTotalPrice(outboundItem.getPrice().multiply(outboundItem.getTotalWeight()));
          outboundItemMapper.updateById(outboundItem);

        String outboundNumber = outboundItem.getOutboundNumber();
        List<OutboundItem> outBoundItemByNum = outboundItemMapper.findOutBoundItemByNum(outboundItem.getOutboundNumber());

        BigDecimal totalSendOut = BigDecimal.ZERO, totalQuantity = BigDecimal.ZERO, totalNoSend = BigDecimal.ZERO, totalWeight = BigDecimal.ZERO,totalPrice = BigDecimal.ZERO;

        int totalPiece = 0;
        for(OutboundItem outboundItem1:outBoundItemByNum){
            totalQuantity = totalQuantity.add( outboundItem1.getQuantity());
            totalSendOut = totalSendOut.add( outboundItem1.getSendOut());
            totalNoSend = totalNoSend.add( outboundItem1.getNoSend());
            totalWeight = totalWeight.add( outboundItem1.getTotalWeight());
            totalPiece +=(int) Double.parseDouble(outboundItem1.getPieces());
            totalPrice = totalPrice.add( outboundItem1.getTotalPrice());

        }

        Outbound outbound = outboundMapper.findOutBoundByNum(outboundNumber);
        outbound.setSendOut(totalSendOut).setNoSend(totalNoSend).setTotalWeight(String.valueOf(totalWeight)).setPieces(String.valueOf(totalPiece)).setTotalPrice(totalPrice);
        outboundMapper.updateById(outbound);
    }

    @Override
    public List<Outbound> outBoundList() {
        LambdaQueryWrapperX<Outbound> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Outbound::getStatus,"待出库");
        return outboundMapper.selectList(queryWrapperX);
    }

    @Override
    public List<OutBoundVo> findOutBoundQuantityList(String model, String spec, String lineType,String itemCode,String otmNumber) {
        return outboundMapper.findOutBoundQuantityList(model,spec,lineType,itemCode,otmNumber);
    }

    @Override
    public List<Outbound> findOutBoundBySaleNumber(String saleNumber) {
        return outboundMapper.selectList(new LambdaQueryWrapperX<Outbound>().eq(Outbound::getSaleNumber,saleNumber));
    }

    @Override
    public Integer removeByNumber(String number) {
        return outboundMapper.delete(new LambdaQueryWrapperX<Outbound>().eq(Outbound::getNumber,number));
    }

    @Override
    public Outbound findOutboundByNumber(String outboundNumber) {
        return outboundMapper.selectOne(new LambdaQueryWrapperX<Outbound>().eq(Outbound::getNumber,outboundNumber));
    }

    @Override
    public PageResult<Outbound> findPageNoSale(OutboundQueryVo orderOutboundQueryVo) {
        return outboundMapper.findPageNoSale(orderOutboundQueryVo);
    }

    @Override
    public List<OutBoundExcelVo> exportData() {
        List<Outbound> outboundList = outboundMapper.selectList();
        ArrayList<OutBoundExcelVo> outBoundExcelVo = new ArrayList<>(outboundList.size());
        outboundList.forEach(dict -> {
            OutBoundExcelVo excelDictDTO = new OutBoundExcelVo();
            if("Y".equals(dict.getIsTax())){
                dict.setIsTax("是");
            }
            dict.setNoSend(dict.getQuantity().subtract(dict.getSendOut()));
            BeanUtils.copyProperties(dict, excelDictDTO);
            outBoundExcelVo.add(excelDictDTO);
        });
        return outBoundExcelVo;
    }

    @Override
    public List<OutBoundAllExcelVo> listAllDataByIds(List<Integer> ids) {
        return outboundMapper.exportAllByIds(ids);
    }

    @Override
    public Integer updateOutbound(Outbound outboundByNumber) {
        return outboundMapper.updateById(outboundByNumber);
    }


}
