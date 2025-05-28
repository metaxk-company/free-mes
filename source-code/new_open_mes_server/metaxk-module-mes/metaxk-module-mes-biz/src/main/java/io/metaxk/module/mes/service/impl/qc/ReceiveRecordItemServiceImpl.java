package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordItemsVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecordItem;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveRecordItemMapper;
import io.metaxk.module.mes.service.qc.ReceiveRecordItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
@Service
public class ReceiveRecordItemServiceImpl implements ReceiveRecordItemService {

    @Resource
    private ReceiveRecordItemMapper receiveRecordItemMapper;


    @Override
    public int getCount(Long recordId) {
        return receiveRecordItemMapper.getCount(recordId);
    }

    @Override
    public String selectMaxDetectionOrderNumber(Long recordId) {
        return receiveRecordItemMapper.selectMaxDetectionOrderNumber(recordId);
    }

    @Override
    public List<ReceiveRecordItemsVo> getReceiveRecordItems(Long recordId, String itemCode, String maxSortNumber) {
        return receiveRecordItemMapper.getReceiveRecordItems(recordId,itemCode,maxSortNumber);
    }

    @Override
    public ReceiveRecordItem selectReceiveRecordItem(Long recordId,Long standardItemId, String sortNumber) {
        return receiveRecordItemMapper.selectReceiveRecordItem(recordId,standardItemId,sortNumber);
    }

    @Override
    public Integer insertReceiveRecordItem(ReceiveRecordItem receiveRecordItem) {
        return receiveRecordItemMapper.insert(receiveRecordItem);
    }

    @Override
    public Integer updateReceiveRecordItem(ReceiveRecordItem receiveRecordItem) {
        return receiveRecordItemMapper.updateById(receiveRecordItem);
    }

    @Override
    public Integer updateReceiveRecordItem(Long recordId, String itemBarCode, String flag, String sortNumber) {
        return receiveRecordItemMapper.updateReceiveRecordItem(recordId,itemBarCode,flag,sortNumber);
    }

    @Override
    public List<ReceiveRecordItem> selectReceiveRecordItem(String sortNumber, String status) {
        return receiveRecordItemMapper.selectReceiveRecordItem(sortNumber,status);
    }


}
