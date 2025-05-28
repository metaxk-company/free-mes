package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecordResult;
import io.metaxk.module.mes.dal.mysql.qc.ProcessRecordResultMapper;
import io.metaxk.module.mes.service.qc.ProcessRecordResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 17:34
 */
@Service
public class ProcessRecordResultServiceImpl implements ProcessRecordResultService {

    @Resource
    private ProcessRecordResultMapper processFormResultMapper;

    @Override
    public void insertProcessFormResult(ProcessRecordResult processFormResult) {
        processFormResultMapper.insert(processFormResult);
    }

    @Override
    public List<ProcessRecordResult> selectByProcessFormId(Long recordId) {
        LambdaQueryWrapperX<ProcessRecordResult> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(ProcessRecordResult::getRecordId,recordId);
        return processFormResultMapper.selectList(queryWrapper);
    }

    @Override
    public List<ProcessRecordResult> selectByResultStatus(Long recordId, String resultStatus) {
        LambdaQueryWrapperX<ProcessRecordResult> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(ProcessRecordResult::getRecordId,recordId);
        queryWrapper.eq(ProcessRecordResult::getResultStatus,resultStatus);
        return processFormResultMapper.selectList(queryWrapper);
    }
}
