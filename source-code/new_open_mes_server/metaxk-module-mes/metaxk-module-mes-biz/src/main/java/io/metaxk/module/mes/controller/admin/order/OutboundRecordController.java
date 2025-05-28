package io.metaxk.module.mes.controller.admin.order;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.controller.admin.order.vo.OutBoundVo;
import io.metaxk.module.mes.dal.dataobject.order.*;
import io.metaxk.module.mes.service.order.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static io.metaxk.framework.common.pojo.CommonResult.error;
import static io.metaxk.framework.common.pojo.CommonResult.success;

/**
 * @author 万界星空
 * @time 2023/7/17 16:42
 */
@Tag(name = "管理后台 - 销售出库 - 扫描出库PC")
@RestController
@RequestMapping("/mes/order/outbound/record")
public class OutboundRecordController {


    @Resource
    private OutboundRecordService outboundRecordService;


    @Resource
    private  OutboundService outboundService;


    @Resource
    private OutboundItemService outboundItemService;


    @Resource
    private LabelService labelService;


    @Resource
    private OutboundItemLabelService outboundItemLabelService;

    @Resource
    private SaleItemService saleItemService;

    @Resource
    private SaleService saleService;



    @GetMapping("/return")
    @Operation(summary = "扫码退库")
    public CommonResult returnOutBound(String outBoundType,String panhao,String barCode ){
        if("barCode".equals(outBoundType)){
            Label labelExist = labelService.findByBarCode(barCode);
            if(labelExist == null){
                return  error(500,"条码不存在");
            }
            List<OutboundItemLabel> outboundItemLabelList = outboundItemLabelService.findItemLabeListByBarCode(barCode);
            if(outboundItemLabelList.isEmpty()){
               return  error(500,"未出库，无法退库");
            }else {
                for (OutboundItemLabel outboundItemLabel : outboundItemLabelList) {
                    OutboundItem  outboundItem = outboundItemService.findOutboundItemByItemNumber(outboundItemLabel.getOutboundItemNumber());
                    BigDecimal pieces = new BigDecimal(outboundItem.getPieces());
                        outboundItem.setSendOut(outboundItem.getSendOut().subtract(outboundItemLabel.getTotalHeight()))
                                    .setNoSend(outboundItem.getQuantity().subtract(outboundItem.getSendOut()))
                                    .setPieces(String.valueOf(pieces.subtract(BigDecimal.valueOf(1))))
                                    .setTotalWeight(outboundItem.getTotalWeight().subtract(outboundItemLabel.getTotalHeight()))
                                    .setTotalPrice(outboundItem.getSendOut().multiply(outboundItem.getPrice()));
                                    BigDecimal bigDecimal1 = new BigDecimal(outboundItem.getPieces());
                                    outboundItem.setTotalTare(outboundItem.getTare().multiply(bigDecimal1));

                                    if("已完成".equals(outboundItem.getStatus())){
                                        outboundItem.setStatus("未完成");
                                    }
                                    outboundItemService.updateOutboundItem(outboundItem);

                        //退库时修改出库的总价格
                        List<OutboundItem> outboundItemList = outboundItemService.findByOutboundNumber(outboundItem.getOutboundNumber());
                        boolean allCompleted  = true;
                        BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);
                        for(OutboundItem outboundItem1:outboundItemList){
                            bigDecimal = bigDecimal.add(outboundItem1.getTotalPrice());
                            if ("未完成".equals(outboundItem1.getStatus())) { // 如果状态是"未完成"
                                allCompleted = true;
                                break;
                            }
                        }
                        Outbound outboundByNumber = outboundService.findOutboundByNumber(outboundItem.getOutboundNumber());
                        if(allCompleted){
                            outboundByNumber.setStatus("待出库");
                        }
                        outboundByNumber.setTotalPrice(bigDecimal);
                        outboundService.updateOutbound(outboundByNumber);


                    this.updateSaleStatus(outboundItem.getSaleItemNumber());

                    Label label = labelService.findByBarCode(barCode);
                        label.setStatus("1");
                        labelService.updateLabel(label);
                        outboundItemLabelService.removeOutItemByCode(barCode);
                    return success("").setMsg("退库成功");
                }
            }
        }else {
            String palletNumber = null;
            if(panhao.length() >12){
                palletNumber = panhao.substring(0, 12);
            }
            List<Label> labelListExist = labelService.findByPanHao(palletNumber);
            if( labelListExist.isEmpty()) {
                return error(500, "托盘号不存在");
            }
            List<OutboundItemLabel> outboundItemLabelList = outboundItemLabelService.findItemLabeByPanHao(palletNumber);
            if(outboundItemLabelList.isEmpty()){
                return  error(500,"未出库，无法退库");
            }else {
                for(OutboundItemLabel outboundItemLabel :outboundItemLabelList){
                    OutboundItem  item = outboundItemService.findOutboundItemByItemNumber(outboundItemLabel.getOutboundItemNumber());
                    BigDecimal pieces = new BigDecimal(item.getPieces());

                            item.setSendOut(item.getSendOut().subtract(outboundItemLabel.getTotalHeight())).setNoSend(item.getQuantity().subtract(item.getSendOut()))
                                    .setPieces(String.valueOf(pieces.subtract(BigDecimal.valueOf(1)))).setTotalWeight(item.getTotalWeight().subtract(outboundItemLabel.getTotalHeight())).setTotalPrice(item.getSendOut().multiply(item.getPrice()));
                            BigDecimal bigDecimal1 = new BigDecimal(item.getPieces());
                            item.setTotalTare(item.getTare().multiply(bigDecimal1));
                            if("已完成".equals(item.getStatus())){
                                item.setStatus("未完成");
                            }
                            outboundItemService.updateOutboundItem(item);
                            //退库时修改出库的总价格
                            String outboundNumber = item.getOutboundNumber();
                            List<OutboundItem> outboundItemList = outboundItemService.findByOutboundNumber(outboundNumber);
                            BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);
                            boolean allCompleted  = true;
                            for(OutboundItem outboundItem1:outboundItemList){
                                bigDecimal = bigDecimal.add(outboundItem1.getTotalPrice());
                                if ("未完成".equals(outboundItem1.getStatus())) {
                                    allCompleted = true;
                                    break;
                                }
                            }
                            Outbound outboundByNumber = outboundService.findOutboundByNumber(outboundNumber);
                            if(allCompleted){
                                outboundByNumber.setStatus("待出库");
                            }
                            outboundByNumber.setTotalPrice(bigDecimal);
                            outboundService.updateOutbound(outboundByNumber);

                            this.updateSaleStatus(item.getSaleItemNumber());

                        List<Label> labelList =   labelService.findByReelNumber(palletNumber);
                       for(Label label1:labelList){
                           Label label = labelService.findByBarCode(label1.getBarCode());
                           if(label != null) {
                               label.setStatus("1");
                               labelService.updateLabel(label);
                           }
                       }
                    outboundItemLabelService.removeOutItemByPanHao(palletNumber);
                    }
            }
            return  success("").setMsg("退库成功");
        }
       return  null;
    }



    //这个方法用于在退库的时候修改销售明细中的状态---并且根据销售单查询所有的销售明细---以对销售的状态进行修改
    public  List<SaleItem> updateSaleStatus(String saleItemNumber){
        List<SaleItem> saleItemList = saleItemService.findsaleItemByItemNum(saleItemNumber);
        for(SaleItem saleItem:saleItemList){
            SaleItem saleItemDo = saleItemService.findSaleItemById(saleItem.getId());
            saleItemDo.setOutboundStatus("0");
            saleItemService.updateSaleItem(saleItemDo);

            List<Sale> saleList = saleService.findBySaleNumber(saleItem.getSaleNumber());
            for(Sale sale:saleList){
                List<SaleItem> saleItemList1 = saleItemService.findsaleItemByNum(sale.getNumber());
                boolean allSaleStatus  = true;
                for(SaleItem saleItem1:saleItemList1){
                    if ("0".equals(saleItem1.getOutboundStatus())) {
                        allSaleStatus = true;
                        break;
                    }
                }
                if(allSaleStatus){
                    Sale saleDo = saleService.findOrderSaleById(sale.getId());
                    saleDo.setStatus("未完成");
                    saleService.updateSale(saleDo);
                }
            }
        }
        return  saleItemList;
    }










    @GetMapping("/list")
    @Operation(summary = "查询出库的列表---查询的是待出库状态")
    public CommonResult<List<HashMap<String, Object>>> outBoundList(){
        List<Outbound> list =    outboundService.outBoundList();
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        for (Outbound outbound : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", outbound.getNumber());
            map.put("value", outbound.getNumber());
            resultList.add(map);
        }
        return  success(resultList);
    }




    @GetMapping("/findCustomerName")
    @Operation(summary = "根据出库单号查找客户")
    public CommonResult<HashMap<String, Object>>  findCustomerName( String number){
        HashMap<String, Object> map = new HashMap<>();
        if(number != null) {
            OutboundItem outboundItem = outboundItemService.findCustomerNameByOutBoundNumber(number);
            map.put("text", outboundItem.getCustomerName());
            map.put("value", outboundItem.getCustomerName());
        }
        return  success(map);
    }



    /**
     *  注意：线别  型号，规格 盘号  产品编号，出库单号必须要有，否则会出现系统异常
     */
    @GetMapping("/outBoundList")
    @Operation(summary = "扫码出库返回拼接好的字符串")
    public  CommonResult<List<HashMap<String, Object>>> findOutBoundList(String number){
        List<OutBoundVo> outBoundVoList =   outboundService.findOutBoundList(number);
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        for (OutBoundVo outbound : outBoundVoList) {
            HashMap<String, Object> map = new HashMap<>();
            int thirdHashIndex = -1;
            String modelAndSpec = outbound.getModelAndSpec();
            String[] split = modelAndSpec.split("#");
            int length = split.length;
            String extractedPart = null;
             if(length  == 7 ) {
                 for (int i = 0; i < 5; i++) {
                     thirdHashIndex = modelAndSpec.indexOf("#", thirdHashIndex + 1);
                     if (thirdHashIndex == -1) {
                         break;
                     }
                 }
             }else {
                 for (int i = 0; i < 6; i++) {
                     thirdHashIndex = modelAndSpec.indexOf("#", thirdHashIndex + 1);
                     if (thirdHashIndex == -1) {
                         break;
                     }
                 }
             }
            if (thirdHashIndex != -1) {
                 extractedPart = modelAndSpec.substring(0, thirdHashIndex);
            }
            map.put("text", extractedPart);
            map.put("value", outbound.getModelAndSpec());
            resultList.add(map);
        }
        return  success(resultList);
    }




    @GetMapping("/outBoundQuantityList")
    @Operation(summary = "扫码出库返回拼接好的字符串")
    public  CommonResult<String> findOutBoundQuantityList(String text){
        String[] parts = text.split("#");
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '#') {
                count++;
            }
        }
        List<OutBoundVo> outBoundVoList = null;
        if(count == 7) {
            outBoundVoList =   outboundService.findOutBoundQuantityList(parts[1], parts[2], parts[0], parts[5], parts[6]);
        }else {
            outBoundVoList =   outboundService.findOutBoundQuantityList(parts[1], parts[2], parts[0], parts[6], parts[7]);
        }
        String textBook = null;
        for (OutBoundVo outbound : outBoundVoList) {
           textBook = outbound.getSalesQuantity();
        }
        return  success(textBook);
    }




    @PostMapping("/save")
    @Operation(summary = "新增扫码出库")
    public CommonResult<String> save(@RequestBody OutboundRecord outboundRecord){
        String[] split = outboundRecord.getModelAndSpec().split("#");
        int length = split.length;
        OutboundItem outboundItem = null;
        if(length == 7){
            outboundItem  = outboundItemService.findOutboundItemByNumber(outboundRecord.getOutboundNumber(),split[6]);
        }else {
            outboundItem =  outboundItemService.findOutboundItemByNumber(outboundRecord.getOutboundNumber(),split[7]);
        }

        if("已完成".equals(outboundItem.getStatus())){
            return error(500,"当前出库明细已完成出库");
        }

        if("barCode".equals(outboundRecord.getOutBoundType())){
            if(outboundRecord.getBarCode().isEmpty()){
                return error(500,"前端没有传条码值");
            }

            Label label = labelService.findByBarCode(outboundRecord.getBarCode());
            if( label == null){
                return error(500,"条码不存在");
            }else {
                StringBuffer sb = new StringBuffer();
                sb.append(label.getLineType());
                sb.append("#");
                sb.append(label.getModel());
                sb.append("#");
                sb.append(label.getSpec());
                sb.append("#");
                sb.append(label.getReelNumber().trim());
                sb.append("#");
                sb.append(label.getColor());
              if(!sb.toString().equals(split[0]+"#" + split[1]+"#"+split[2]+"#" + split[3]+"#" + split[4])) {
                  return error(500, "扫码规格与选择的规格不匹配");
              } else if ("3".equals(label.getStatus())){{
                  return  error(500,"已重包,无法扫码出库");
               }
             }
            }
           if(outboundItemLabelService.findItemLabeByBarCode(outboundRecord.getBarCode()) != null){
               return error(500,"请勿重复扫码");
           }
        }else {
            if(outboundRecord.getPanhao().isEmpty()){
                return error(500,"前端没有传条码值");
            }
            String panhao = null;
            if(outboundRecord.getPanhao().length() >12){
                panhao = outboundRecord.getPanhao().substring(0, 12);
            }
            List<Label> labelList = labelService.findByPanHao(panhao);
            if(labelList.isEmpty()){
               return error(500,"托盘号不存在");
            }else {
                for(Label label:labelList) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(label.getLineType());
                    sb.append("#");
                    sb.append(label.getModel());
                    sb.append("#");
                    sb.append(label.getSpec());
                    sb.append("#");
                    sb.append(label.getReelNumber().trim());
                    sb.append("#");
                    sb.append(label.getColor());
                    if(!sb.toString().equals(split[0]+"#" + split[1]+"#"+split[2]+"#" + split[3]+"#" + split[4])) {
                        return error(500, "扫码规格与选择的规格不匹配");
                    }
                     else if ("3".equals(label.getStatus())) {
                        return error(500, "已重包,无法按照托盘出库");
                    }
                }
            }
            List<OutboundItemLabel> outboundItemLabelList = outboundItemLabelService.findItemLabeByPanHao(outboundRecord.getPanhao());
            if(!outboundItemLabelList.isEmpty()){
               for(OutboundItemLabel outboundItemLabel:outboundItemLabelList){
                   List<Label> labelList1 = labelService.findByPanHao(outboundItemLabel.getPalletNumber());
                   boolean allCompleted  = true;
                   for(Label label:labelList1){
                       if (!"2".equals(label.getStatus())) {
                           allCompleted = false;
                           break;
                       }
                   }
                   if (allCompleted) {
                      return error(500,"已出库,请勿重复扫托盘");
                   }
               }
           }
        }
        String text = outboundRecordService.saveOutboundCount(outboundRecord);
        return  success(text).setMsg(text);
    }







    @GetMapping("/updateStatus")
    public CommonResult  updateLabelStatus(String outBoundType,String panhao,String barCode){
        if("barCode".equals(outBoundType)){
            if(barCode.isEmpty()){
                return error(500,"前端没有传条码值");
            }

            if( labelService.findByBarCode(barCode) == null){
                return error(500,"条码不存在");
            }
            Label label = labelService.findByBarCode(barCode);
            if("3".equals(label.getStatus())){
                return  error(500,"已重包，请勿重复重包");
            }else {
                label.setStatus("3");
                //先修改状态为已重包
                labelService.updateLabel(label);
                //在返回已重包的型号规格与件数
                OutboundRecord outboundRecord =  labelService.countRepackage();
                return success(outboundRecord).setMsg("已重包");
            }
        }else {
            if(panhao.isEmpty()){
                return error(500,"前端没有传条码值");
            }
            String palletNumber = null;
            if(panhao.length() >12){
                palletNumber = panhao.substring(0, 12);
                System.out.println("===========12============" + palletNumber);
            }
            List<Label> labelList = labelService.findByPanHao(palletNumber);
            if( labelList.isEmpty()){
                return error(500,"托盘号不存在");
            }else {
                OutboundRecord outboundRecord = null;
                for (Label label : labelList) {
                    if ("3".equals(label.getStatus())) {
                        return error(500, "已重包，请勿重复重包");
                    } else {
                        Label labelDo = labelService.findLabelById(label.getId());
                        labelDo.setStatus("3");
                        labelService.updateLabel(labelDo);
                        outboundRecord =  labelService.countRepackage();
                    }
                }
                return success(outboundRecord).setMsg("已重包");
            }
        }
    }

    @PutMapping("/returnGood")
    @Operation(summary = "扫码退货")
    public CommonResult<Boolean> returnGood(String qrCode){
        return success(outboundRecordService.returnGood(qrCode)).setMsg("退货完成");
    }


}
