package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.OutBoundVo;
import io.metaxk.module.mes.dal.dataobject.md.Client;
import io.metaxk.module.mes.dal.dataobject.order.*;
import io.metaxk.module.mes.dal.mysql.md.ClientMapper;
import io.metaxk.module.mes.dal.mysql.order.*;
import io.metaxk.module.mes.service.data.CustomDictDataService;
import io.metaxk.module.mes.service.order.OutboundRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.LABEL_QR_CODE_NOT_EXIST;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.OUTBOUND_QUANTITY_NOT_LEGAL;

/**
 * @author 万界星空
 * @time 2023/7/19 15:15
 */
@Service
public class OutboundRecordServiceImpl implements OutboundRecordService {

    @Resource
    private OutboundRecordMapper outboundRecordMapper;

    @Resource
    private OutboundItemMapper outboundItemMapper;

    @Resource
    private LabelMapper labelMapper;

    @Resource
    private OutboundItemLabelMapper  outboundItemLabelMapper;

    @Resource
    private OutboundMapper outboundMapper;

    @Resource
    private SaleMapper saleMapper;

    @Resource
    private SaleItemMapper saleItemMapper;

    @Resource
    private CustomDictDataService customDictDataService;

    @Resource
    private ClientMapper clientMapper;



    @Override
    @Transactional
    public String saveOutboundCount(OutboundRecord outboundRecord) {
       String[] split = outboundRecord.getModelAndSpec().split("#");
       int  length1 = split.length;
        List<Label> labelList = null;
        if("barCode".equals(outboundRecord.getOutBoundType())) {
            labelList = labelMapper.findLabelByCode(outboundRecord.getBarCode());
       }else {
            String panhao = null;
            if(outboundRecord.getPanhao().length() >12){
                panhao = outboundRecord.getPanhao().substring(0, 12);
            }
            labelList = labelMapper.findLabelByPanHao(panhao);
        }

        if(labelList.size() == 0){
            return "该条码已经出库";
        }

       for(Label label:labelList){
          OutboundItemLabel outboundItemLabel = new OutboundItemLabel();
          BeanUtils.copyProperties(label,outboundItemLabel);

          String outItemNumber = null;
          if(length1 == 7){
              outItemNumber = split[6];
          }else {
              outItemNumber = split[7];
          }
          OutboundItem outboundItem = outboundItemMapper.findOutItemByNum(outItemNumber);
          outboundItemLabel.setWhLabelId(label.getId()).setSaleItemNumber(null).setOutboundNumber(outboundRecord.getOutboundNumber())
                           .setOutboundItemNumber(outItemNumber).setTotalTare(label.getTotalTare()).setTotalHeight(label.getTotalHeight())
                           .setSaleItemNumber(outboundItem.getSaleItemNumber()).setOutReelNumber(outboundRecord.getPanhao()).setStatus("2");
          outboundItemLabelMapper.insert(outboundItemLabel);

           //新加修改入库表的状态
           Label labelDo = labelMapper.selectById(label.getId());
           labelDo.setStatus("2");
           labelMapper.updateById(labelDo);
       }
      //根据出库明细查询出库明细下的label数据
       String outBoundItemNumber = null;
       if(length1 == 7){
           outBoundItemNumber = split[6];
       }else {
           outBoundItemNumber = split[7];
       }
      List<OutboundItemLabel> outboundItemLabelList =  outboundItemLabelMapper.findboundItemLabeByItemNum(outBoundItemNumber);
      BigDecimal totalWeight = new BigDecimal(BigInteger.ZERO);

      for(OutboundItemLabel outboundItemLabel :outboundItemLabelList){
          totalWeight= totalWeight.add(outboundItemLabel.getTotalHeight());
      }

      OutboundItem outboundItem =  outboundItemMapper.findOutBoundItemByNumAndOutNum(outboundRecord.getOutboundNumber(),outBoundItemNumber);
      //查询出库明细表----设置已发
      outboundItem.setSendOut(totalWeight).setNoSend(outboundItem.getQuantity().subtract(outboundItem.getSendOut())).setTotalWeight(totalWeight).setPieces(String.valueOf(outboundItemLabelList.size()))
                  .setTotalPrice(outboundItem.getPrice().multiply(outboundItem.getTotalWeight()));


        String saleNum = outboundItem.getSaleNumber();
        Sale saleDo = saleMapper.findSaleByNumber(saleNum);


        //取出PDA上面件数的数据，用于计算件数出库
        Pattern pattern = Pattern.compile("件数:(\\d+)");
        Matcher matcher = pattern.matcher(outboundRecord.getSaleQuantity());
        //对PDA件数的值进行解析
        String piecesValue = null;
        if (matcher.find()) {
            piecesValue = matcher.group(1);
        }
        //得到label表出了多少件
        int size = labelList.size();
        int piecesCountValue = Integer.parseInt(piecesValue);
        //使用件数加上PDA上的件数
        int piecesQuantity = piecesCountValue + size;

        /*//获取数据字典中的值----用于设置可以已发的最大值
        CustomDictData customDictData = customDictDataService.findOutBoundLimit();
        String value = customDictData.getValue();
        BigDecimal valueDecimal = new BigDecimal(value).divide(new BigDecimal(100));
        BigDecimal outboundItemQuantity = outboundItem.getQuantity().multiply(valueDecimal);
        // 将增加量加到原有数量上
        BigDecimal newOutboundItemQuantity= outboundItem.getQuantity().add(outboundItemQuantity);*/

        // 获取客户的出库最大值和最小值
        String clientNumber = saleDo.getCustomerNumber();
        Client client = clientMapper.selectOne(new LambdaQueryWrapperX<Client>().eq(Client::getClientCode, clientNumber));
        int max; int min;
        if (client.getOutMax() == null)
            max = 0;
        else
            max = client.getOutMax();
        if (client.getOutMax() == null)
            min = 0;
        else
            min = client.getOutMax();

        BigDecimal maxOutboundItemQuantity = outboundItem.getQuantity().add(new BigDecimal(max));
        BigDecimal minOutboundItemQuantity = outboundItem.getQuantity().subtract(new BigDecimal(min));

        System.out.println("最大:" + maxOutboundItemQuantity);
        System.out.println("最小:" + minOutboundItemQuantity);
        System.out.println(client);

        if (outboundItem.getSendOut().compareTo(maxOutboundItemQuantity) > 0)
            throw exception(OUTBOUND_QUANTITY_NOT_LEGAL);

        if (outboundItem.getSendOut().compareTo(minOutboundItemQuantity) >= 0 && "weight".equals(saleDo.getPriceModel()) || piecesQuantity == outboundItem.getQuantity().intValue() || piecesQuantity > outboundItem.getQuantity().intValue()) {
            outboundItem.setStatus("已完成");
            //新加
            SaleItem saleItem = saleItemMapper.findSaleItemBySaleNumber(outboundItem.getSaleItemNumber());
            saleItem.setOutboundStatus("1");
            saleItemMapper.updateById(saleItem);
        }
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(outboundItemLabelList.size()));
        System.out.println("--------------件数-------------" + outboundItem.getPieces());
        System.out.println("--------------皮重-------------" + outboundItem.getTare());
        outboundItem.setTotalTare(outboundItem.getTare().multiply(bigDecimal));
        outboundItemMapper.updateById(outboundItem);
        System.out.println("--------------------------------------------");
      List<OutboundItem> outBoundItemByNum = outboundItemMapper.findOutBoundItemByNum(outboundItem.getOutboundNumber());
      BigDecimal totalSendOut = BigDecimal.ZERO, totalQuantity = BigDecimal.ZERO, totalNoSend = BigDecimal.ZERO, totalWeight1 = BigDecimal.ZERO,totalPrice = BigDecimal.ZERO;
      int totalPiece = 0;
      for(OutboundItem outboundItem1:outBoundItemByNum){
          totalQuantity = totalQuantity.add( outboundItem1.getQuantity());
          totalSendOut = totalSendOut.add( outboundItem1.getSendOut());
          totalNoSend = totalNoSend.add( outboundItem1.getNoSend());
          if(outboundItem1.getTotalWeight() != null) {
              totalWeight1 = totalWeight1.add(outboundItem1.getTotalWeight());
          }
          totalPiece +=(int) Double.parseDouble(outboundItem1.getPieces());
          totalPrice = totalPrice.add( outboundItem1.getTotalPrice());

      }
      Outbound outbound = outboundMapper.findOutBoundByNum(outboundRecord.getOutboundNumber());
      outbound.setSendOut(totalSendOut).setNoSend(totalNoSend).setTotalWeight(String.valueOf(totalWeight1)).setPieces(String.valueOf(totalPiece)).setTotalPrice(totalPrice);

      outboundMapper.updateById(outbound);
      //获取出库的数量  当扫码已发数量超过出库数量+出库数量乘以百分之二十之后修改出库状态为已出库
        List<OutboundItem> outboundItemList = outboundItemMapper.findOutBoundItemByNum(outboundItem.getOutboundNumber());
        boolean allCompleted  = true;
        for (OutboundItem item : outboundItemList) {
            if (!"已完成".equals(item.getStatus())) {
                allCompleted = false;
                break;
            }
        }
        if (allCompleted) {
          outbound.setStatus("已出库");
          outboundMapper.updateById(outbound);
            List<OutboundItem> outboundItemList1 = outboundItemMapper.findOutBoundItemByNum(outbound.getNumber());
            for(OutboundItem outboundItem1 :outboundItemList1) {
                String saleNumber = outboundItem1.getSaleNumber();
                List<SaleItem> saleItemList = saleItemMapper.findSaleItemListBySaleNumber(saleNumber);
                boolean outBoundStatus = true;
                for (SaleItem item : saleItemList) {
                    if (!"1".equals(item.getOutboundStatus())) {
                        outBoundStatus = false;
                        break;
                    }
                }
                if (outBoundStatus) {
                    Sale sale = saleMapper.findSaleByNumber(outboundItem1.getSaleNumber());
                    sale.setId(sale.getId());
                    sale.setStatus("已完成");
                    saleMapper.updateById(sale);
                }
            }
        }

        outboundRecordMapper.insert(outboundRecord);

        String modelAndSpec = outboundRecord.getModelAndSpec();
        String[] parts = modelAndSpec.split("#");
        String lineType = parts[0];     String model = parts[1];
        String spec = parts[2];
        int length = parts.length;
        String itemCode = null,otmNumber = null;
        if(length == 7) {
             itemCode = parts[5];
             otmNumber = parts[6];
        }else {
            itemCode = parts[6];
            otmNumber = parts[7];
        }
        List<OutBoundVo> outBoundVoList =   outboundMapper.findOutBoundQuantityList(model,spec,lineType,itemCode,otmNumber);
        String textBook = null;
        for (OutBoundVo outbound1 : outBoundVoList) {
            textBook = outbound1.getSalesQuantity();
        }
        return  textBook;
    }

    @Override
    public List<OutboundRecord> findByOutboundNum(String number) {
        return outboundRecordMapper.selectList(new LambdaQueryWrapperX<OutboundRecord>().eq(OutboundRecord::getOutboundNumber,number));
    }

    @Override
    public Integer removeByOutboundNum(String number) {
        return outboundRecordMapper.delete(new LambdaQueryWrapperX<OutboundRecord>().eq(OutboundRecord::getOutboundNumber,number));
    }

    @Override
    @Transactional
    public Boolean returnGood(String qrCode) {
        LambdaQueryWrapperX<Label> queryWrapperX = new LambdaQueryWrapperX<Label>().eq(Label::getBarCode, qrCode);
        Label label = labelMapper.selectOne(queryWrapperX);
        if (label == null)
            throw exception(LABEL_QR_CODE_NOT_EXIST);
        label.setId(null);
        label.setStatus("1");

        labelMapper.delete(queryWrapperX);
        return labelMapper.insert(label) == 1;
    }

}
