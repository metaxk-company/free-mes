package io.metaxk.module.mes.service.impl.order;



import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.order.vo.InboundExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.InboundQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.InboundVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteExportVo;
import io.metaxk.module.mes.dal.dataobject.order.Inbound;
import io.metaxk.module.mes.dal.dataobject.order.InboundItem;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.order.Quote;
import io.metaxk.module.mes.dal.mysql.order.InboundItemMapper;
import io.metaxk.module.mes.dal.mysql.order.InboundMapper;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderItemMapper;
import io.metaxk.module.mes.service.order.InboundItemService;
import io.metaxk.module.mes.service.order.InboundService;
import io.metaxk.module.mes.service.order.PurchaseOrderItemService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 采购入库ServiceImpl
 * @author 万界星空MES
 */
@Service
public class InboundServiceImpl implements InboundService {

    @Resource
    private InboundMapper InboundMapper;

    @Resource
    private InboundItemMapper InboundItemMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Resource
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    @Resource
    private InboundItemService inboundItemService;

    @Resource
    private PurchaseOrderItemService receiptItemService;

    @Override
    public Integer saveInbound(Inbound inbound) {
        //设置流水号
        inbound.setInNumber(autoCodeUtil.genSerialCode(UserConstants.INBOUND_CODE,null));
        for(InboundItem inboundItem: inbound.getItemList()){
            InboundItem quoteItem = new InboundItem();
            quoteItem.setInNumber(inbound.getInNumber())
                    .setItemCode(inboundItem.getItemCode()).setItemName(inboundItem.getItemName()).setModel(inboundItem.getModel())
                    .setSpec(inboundItem.getSpec()).setBatchNumber(inboundItem.getBatchNumber()).setVendor(inboundItem.getVendor())
                    .setBoxNumber(inboundItem.getBoxNumber()).setQuantity(inboundItem.getQuantity()).setPurchasePrice(inboundItem.getPurchasePrice())
                    .setTotalPrice(inboundItem.getTotalPrice()).setUnitOfMeasure(inboundItem.getUnitOfMeasure()).setKind(inboundItem.getKind())
                    .setBarcode(inboundItem.getBarcode()).setProductionDate(inboundItem.getProductionDate()).setEffectiveDate(inboundItem.getEffectiveDate()).setRemark(inboundItem.getRemark())
                    .setReceiptNumber(inboundItem.getReceiptNumber());

            PurchaseOrderItem purchaseOrderItem =  purchaseOrderItemMapper.findPurchaseOrderItem(inboundItem.getItemCode(),inboundItem.getReceiptNumber());
            purchaseOrderItem.setStatus("1");
            purchaseOrderItemMapper.updateById(purchaseOrderItem);
            InboundItemMapper.insert(quoteItem);
        }
        return InboundMapper.insert(inbound);
    }



    @Override
    public Integer removeInboundByIds(List<Long> ids) {
        Map<String, Object> map = new HashMap<>();
        for (int i=0; i<ids.size(); ++i) {
            map.clear();
            map.put("in_number", InboundMapper.selectById(ids.get(i)).getInNumber());
            InboundItemMapper.deleteByMap(map);
        }
        return InboundMapper.deleteBatchIds(ids);
    }


    @Override
    public Integer updateInbound(Inbound inbound) {
        Map<String, Object> map = new HashMap<>();
        map.put("in_number", inbound.getInNumber());
        List<InboundItem> itemList = inboundItemService.findItemByNum(inbound.getInNumber());
        for(InboundItem inboundItem :itemList){
            PurchaseOrderItem purchaseOrderItem =  receiptItemService.findReceiptItemByNumAndCode(inboundItem.getItemCode(),inboundItem.getReceiptNumber());
            purchaseOrderItem.setStatus("0");
            receiptItemService.updatePurchaseOrderItem(purchaseOrderItem);
        }
        InboundItemMapper.deleteByMap(map);
        for(InboundItem inboundItem: inbound.getItemList()){
            InboundItem quoteItem = new InboundItem();
            quoteItem.setInNumber(inbound.getInNumber())
                    .setItemCode(inboundItem.getItemCode()).setItemName(inboundItem.getItemName()).setModel(inboundItem.getModel())
                    .setSpec(inboundItem.getSpec()).setBatchNumber(inboundItem.getBatchNumber()).setVendor(inboundItem.getVendor())
                    .setBoxNumber(inboundItem.getBoxNumber()).setQuantity(inboundItem.getQuantity()).setPurchasePrice(inboundItem.getPurchasePrice())
                    .setTotalPrice(inboundItem.getTotalPrice()).setUnitOfMeasure(inboundItem.getUnitOfMeasure()).setKind(inboundItem.getKind())
                    .setBarcode(inboundItem.getBarcode()).setProductionDate(inboundItem.getProductionDate()).setEffectiveDate(inboundItem.getEffectiveDate()).setRemark(inboundItem.getRemark())
                    .setReceiptNumber(inboundItem.getReceiptNumber());
            PurchaseOrderItem purchaseOrderItem =  receiptItemService.findReceiptItemByNumAndCode(inboundItem.getItemCode(),inboundItem.getReceiptNumber());
            purchaseOrderItem.setStatus("1");
            receiptItemService.updatePurchaseOrderItem(purchaseOrderItem);
            InboundItemMapper.insert(quoteItem);
        }
        return InboundMapper.updateById(inbound);
    }







    @Override
    public Inbound findInboundById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("in_number", InboundMapper.selectById(id).getInNumber());
        List<InboundItem> list = InboundItemMapper.selectByMap(map);
        Inbound Inbound = InboundMapper.selectById(id).setItemList(list);
        return Inbound;
    }



    @Override
    public PageResult<Inbound> findPage(InboundQueryVo InboundQueryVo) {
        return InboundMapper.findPage(InboundQueryVo);
    }

    @Override
    public List<InboundVo> inBoundPrint(String number) {
        return InboundMapper.inBoundPrint(number);
    }

    @Override
    public Integer removeInboundByNum(String number) {
        return InboundMapper.delete(new LambdaQueryWrapperX<Inbound>().eq(Inbound::getInNumber,number));
    }

    @Override
    public List<InboundExcelVo> exportData() {
        List<Inbound> inboundList = InboundMapper.selectList();
        ArrayList<InboundExcelVo> inboundExcelVo = new ArrayList<>(inboundList.size());
        inboundList.forEach(dict -> {
            InboundExcelVo excelDictDTO = new InboundExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            inboundExcelVo.add(excelDictDTO);
        });
        return inboundExcelVo;
    }



}
