package io.metaxk.module.mes.service.impl.wh;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemVo;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBillItem;
import io.metaxk.module.mes.dal.mysql.wh.InboundRecBillItemMapper;
import io.metaxk.module.mes.service.wh.InboundRecBillItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 到货通知明细ServiceImpl
 *
 * @author 万界星空MES
 */
@Service
public class InboundRecBillItemServiceImpl implements InboundRecBillItemService {

    @Resource
    private InboundRecBillItemMapper inboundRecBillItemMapper;
    @Override
    public PageResult<InboundRecBillItem> findPage(InboundRecBillItemQueryVo inboundRecBillItemQueryVo,String flag) {
        return inboundRecBillItemMapper.findPage(inboundRecBillItemQueryVo,flag);
    }

    @Override
    public List<InboundRecBillItemVo> findInboundRecBillItem(String receiptNumber) {
        return inboundRecBillItemMapper.findInboundRecBillItem(receiptNumber);
    }

    @Override
    public InboundRecBillItem findInboundRecBillItem(String itemCode, String recNumber) {
        return inboundRecBillItemMapper.findInboundRecBillItem(itemCode,recNumber);
    }

    @Override
    public Integer updateinboundRecBillItem(InboundRecBillItem inboundRecBillItem) {
        return inboundRecBillItemMapper.updateById(inboundRecBillItem);
    }

    @Override
    public Integer updateState(String itemCode, String recNumber, String state) {
        LambdaQueryWrapperX<InboundRecBillItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(InboundRecBillItem::getItemCode,itemCode);
        queryWrapperX.eq(InboundRecBillItem::getRecNumber,recNumber);
        InboundRecBillItem inboundRecBillItem = inboundRecBillItemMapper.selectOne(queryWrapperX);
        inboundRecBillItem.setState(state);
        return inboundRecBillItemMapper.updateById(inboundRecBillItem);
    }
}
