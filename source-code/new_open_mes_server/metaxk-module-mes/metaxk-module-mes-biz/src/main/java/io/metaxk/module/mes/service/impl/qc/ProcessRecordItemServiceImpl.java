package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordItemsVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecordItem;
import io.metaxk.module.mes.dal.mysql.qc.ProcessRecordItemMapper;
import io.metaxk.module.mes.service.qc.ProcessRecordItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 17:34
 */
@Service
public class ProcessRecordItemServiceImpl implements ProcessRecordItemService {

    @Resource
    private ProcessRecordItemMapper processFormCompareMapper;

    @Override
    public void insertProcessFormCompare(ProcessRecordItem processFormCompare) {
        processFormCompareMapper.insert(processFormCompare);
    }

    @Override
    public void updateProcessRecordItem(ProcessRecordItem processRecordItem) {
        processFormCompareMapper.updateById(processRecordItem);
    }

    @Override
    public int getCount(Long processFormId) {
        return processFormCompareMapper.getCount(processFormId);
    }

    @Override
    public List<ProcessRecordItem> selectCompareByNumber(String detectionOrderNumber, String status) {
        return processFormCompareMapper.selectCompareByNumber(detectionOrderNumber,status);
    }

    @Override
    public int updateProcessRecordItem(Long recordId,String itemBarCode,String flag,String detectionOrderNumber) {
        return processFormCompareMapper.updateProcessRecordItem(recordId,itemBarCode,flag,detectionOrderNumber);
    }

    @Override
    public String selectMaxDetectionOrderNumber(Long processFormId) {
        return processFormCompareMapper.selectMaxDetectionOrderNumber(processFormId);
    }

    @Override
    public List<ProcessRecordItem> getProcessFormCompare(Long processFormId, String detectionOrderNumber) {
        return processFormCompareMapper.getProcessFormCompare(processFormId,detectionOrderNumber);
    }

    @Override
    public List<ProcessRecordItemsVo> getProcessFormCompares(Long recordId, String processCode,String maxSortNumber) {
        return processFormCompareMapper.getProcessFormCompares(recordId,processCode,maxSortNumber);
    }

    @Override
    public ProcessRecordItem selectRecordItemByStandardItemId(Long recordId,Long standardItemId,String sortNumber) {
        return processFormCompareMapper.selectRecordItemByStandardItemId(recordId,standardItemId,sortNumber);
    }
}
