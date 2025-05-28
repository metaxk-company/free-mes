package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;
import io.metaxk.module.mes.dal.dataobject.qc.StandardDetail;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveStandardItemMapper;
import io.metaxk.module.mes.service.qc.ReceiveStandardItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 11:01
 */
@Service
public class ReceiveStandardItemServiceImpl implements ReceiveStandardItemService {

    @Resource
    private ReceiveStandardItemMapper receiveStandardItemMapper;
    @Override
    public Integer saveReceiveStandardItem(ReceiveStandardItem receiveStandardItem) {
        return receiveStandardItemMapper.insert(receiveStandardItem);
    }

    @Override
    public ReceiveStandardItem findReceiveStandardItemByCondition(String recStandardNumber, String name, String standard, String device) {
        LambdaQueryWrapperX<ReceiveStandardItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveStandardItem::getRecStandardNumber,recStandardNumber);
        queryWrapperX.eq(ReceiveStandardItem::getName,name);
        queryWrapperX.eq(ReceiveStandardItem::getStandard,standard);
        queryWrapperX.eq(ReceiveStandardItem::getDevice,device);
        return receiveStandardItemMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<ReceiveStandardItem> findReceiveStandardItemByCode(String recStandardNumber) {
        LambdaQueryWrapperX<ReceiveStandardItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveStandardItem::getRecStandardNumber,recStandardNumber);
        return receiveStandardItemMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeCode(String number) {
        LambdaQueryWrapperX<ReceiveStandardItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveStandardItem::getRecStandardNumber,number);
        return receiveStandardItemMapper.delete(queryWrapperX);
    }

    @Override
    public Integer removeReceiveStandardItemByCode(String deviceCode, String number) {
        LambdaQueryWrapperX<ReceiveStandardItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveStandardItem::getDevice,deviceCode);
        queryWrapperX.eq(ReceiveStandardItem::getRecStandardNumber,number);
        return receiveStandardItemMapper.delete(queryWrapperX);
    }

    @Override
    public List<ReceiveStandardItem> findReceiveStandardItem(String itemCode, String enableFlag) {
        LambdaQueryWrapperX<ReceiveStandardItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.like(ReceiveStandardItem::getItemCode, itemCode);
        queryWrapperX.eq(ReceiveStandardItem::getEnableFlag,enableFlag);
        return receiveStandardItemMapper.selectList(queryWrapperX);
    }

    @Override
    public List<ReceiveStandardItem> findReceiveStandardItemByItemCode(String itemCode) {
        return receiveStandardItemMapper.findReceiveStandardItemByItemCode(itemCode);
    }
}
