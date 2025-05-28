package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.dal.dataobject.qc.OtherRecordItem;
import io.metaxk.module.mes.dal.mysql.qc.OtherRecordItemMapper;
import io.metaxk.module.mes.service.qc.OtherRecordItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Service
public class OtherRecordItemServiceImpl implements OtherRecordItemService {

    @Resource
    private OtherRecordItemMapper otherRecordItemMapper;
    @Override
    public Integer saveOtherRecordItem(OtherRecordItem otherRecordItem) {
        return otherRecordItemMapper.insert(otherRecordItem);
    }

    @Override
    public List<OtherRecordItem> findOtherRecordItem(String recordNumber) {
        return otherRecordItemMapper.findOtherRecordItem(recordNumber);
    }
}
