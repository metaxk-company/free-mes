package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.order.vo.OtherInboundQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.*;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBillItem;
import io.metaxk.module.mes.dal.mysql.order.OtherInboundItemMapper;
import io.metaxk.module.mes.dal.mysql.order.OtherInboundMapper;
import io.metaxk.module.mes.dal.mysql.wh.InboundRecBillItemMapper;
import io.metaxk.module.mes.service.order.OtherInboundItemService;
import io.metaxk.module.mes.service.order.OtherInboundService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 万界星空
 * @time 2023/8/22 16:32
 */
@Service
public class OtherInboundServiceImpl implements OtherInboundService {

    @Resource
    private OtherInboundMapper otherInboundMapper;
    @Resource
    private OtherInboundItemMapper otherInboundItemMapper;
    @Resource
    private InboundRecBillItemMapper inboundRecBillItemMapper;
    @Resource
    private OtherInboundItemService otherInboundItemService;

    @Resource
    private AutoCodeUtil autoCodeUtil;


    @Override
    public PageResult<OtherInbound> findPage(OtherInboundQueryVo otherInboundQueryVo) {
        return otherInboundMapper.findPage(otherInboundQueryVo);
    }

    @Override
    public OtherInbound findOtherInboundById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("in_number", otherInboundMapper.selectById(id).getInNumber());
        List<OtherInboundItem> list = otherInboundItemMapper.selectByMap(map);
        return otherInboundMapper.selectById(id).setItemList(list);
    }

    @Override
    public Integer saveOtherInbound(OtherInbound otherInbound) {
        //设置流水号
        //todo
        otherInbound.setInNumber(autoCodeUtil.genSerialCode(UserConstants.INBOUND_CODE,null));
        for(OtherInboundItem otherInboundItem: otherInbound.getItemList()){
            OtherInboundItem quoteItem = new OtherInboundItem();
            quoteItem.setInNumber(otherInbound.getInNumber())
                    .setReceiptNumber(otherInboundItem.getReceiptNumber())
                    .setRecNumber(otherInboundItem.getRecNumber())
                    .setItemCode(otherInboundItem.getItemCode()).setItemName(otherInboundItem.getItemName()).setModel(otherInboundItem.getModel())
                    .setSpec(otherInboundItem.getSpec()).setBatchNumber(otherInboundItem.getBatchNumber()).setVendor(otherInboundItem.getVendor())
                    .setBoxNumber(otherInboundItem.getBoxNumber()).setQuantity(otherInboundItem.getQuantity()).setAmount(otherInboundItem.getAmount()).setUnqualifiedNumber(otherInboundItem.getUnqualifiedNumber()).setPurchasePrice(otherInboundItem.getPurchasePrice())
                    .setTotalPrice(otherInboundItem.getTotalPrice()).setUnitOfMeasure(otherInboundItem.getUnitOfMeasure()).setKind(otherInboundItem.getKind())
                    .setBarcode(otherInboundItem.getBarcode()).setProductionDate(otherInboundItem.getProductionDate()).setEffectiveDate(otherInboundItem.getEffectiveDate()).setRemark(otherInboundItem.getRemark());

            InboundRecBillItem inboundRecBillItem =  inboundRecBillItemMapper.findInboundRecBillItem(otherInboundItem.getItemCode(),otherInboundItem.getRecNumber());
            inboundRecBillItem.setStatus("1");
            inboundRecBillItemMapper.updateById(inboundRecBillItem);
            otherInboundItemMapper.insert(quoteItem);
        }
        return otherInboundMapper.insert(otherInbound);
    }

    @Override
    public Integer updateOtherInbound(OtherInbound otherInbound) {
        Map<String, Object> map = new HashMap<>();
        map.put("in_number", otherInbound.getInNumber());
        List<OtherInboundItem> itemList = otherInboundItemService.findItemByNumber(otherInbound.getInNumber());
        for(OtherInboundItem otherInboundItem :itemList){
            InboundRecBillItem inboundRecBillItem =  inboundRecBillItemMapper.findInboundRecBillItem(otherInboundItem.getItemCode(),otherInboundItem.getRecNumber());
            inboundRecBillItem.setStatus("0");
            inboundRecBillItemMapper.updateById(inboundRecBillItem);
        }
        //先删除，再添加
        otherInboundItemMapper.deleteByMap(map);
        for(OtherInboundItem otherInboundItem: otherInbound.getItemList()){
            OtherInboundItem quoteItem = new OtherInboundItem();
            quoteItem.setInNumber(otherInbound.getInNumber())
                    .setReceiptNumber(otherInboundItem.getReceiptNumber())
                    .setRecNumber(otherInboundItem.getRecNumber())
                    .setItemCode(otherInboundItem.getItemCode()).setItemName(otherInboundItem.getItemName()).setModel(otherInboundItem.getModel())
                    .setSpec(otherInboundItem.getSpec()).setBatchNumber(otherInboundItem.getBatchNumber()).setVendor(otherInboundItem.getVendor())
                    .setBoxNumber(otherInboundItem.getBoxNumber()).setQuantity(otherInboundItem.getQuantity()).setAmount(otherInboundItem.getAmount()).setUnqualifiedNumber(otherInboundItem.getUnqualifiedNumber()).setPurchasePrice(otherInboundItem.getPurchasePrice())
                    .setTotalPrice(otherInboundItem.getTotalPrice()).setUnitOfMeasure(otherInboundItem.getUnitOfMeasure()).setKind(otherInboundItem.getKind())
                    .setBarcode(otherInboundItem.getBarcode()).setProductionDate(otherInboundItem.getProductionDate()).setEffectiveDate(otherInboundItem.getEffectiveDate()).setRemark(otherInboundItem.getRemark());

            InboundRecBillItem inboundRecBillItem =  inboundRecBillItemMapper.findInboundRecBillItem(otherInboundItem.getItemCode(),otherInboundItem.getRecNumber());
            inboundRecBillItem.setStatus("1");
            inboundRecBillItemMapper.updateById(inboundRecBillItem);
            otherInboundItemMapper.insert(quoteItem);
        }
        return otherInboundMapper.updateById(otherInbound);
    }

    @Override
    public Integer removeOtherInboundItem(List<Long> ids) {
        Map<String, Object> map = new HashMap<>();
        for (int i=0; i<ids.size(); ++i) {
            map.clear();
            map.put("in_number", otherInboundMapper.selectById(ids.get(i)).getInNumber());
            otherInboundItemMapper.deleteByMap(map);
        }
        return otherInboundMapper.deleteBatchIds(ids);
    }
}
