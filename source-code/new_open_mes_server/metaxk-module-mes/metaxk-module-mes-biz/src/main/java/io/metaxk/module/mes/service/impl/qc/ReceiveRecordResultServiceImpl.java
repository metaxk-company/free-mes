package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecordResult;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecordResult;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveRecordResultMapper;
import io.metaxk.module.mes.service.qc.ReceiveRecordResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
@Service
public class ReceiveRecordResultServiceImpl implements ReceiveRecordResultService {

    @Resource
    private ReceiveRecordResultMapper receiveRecordResultMapper;

    @Override
    public Integer insertReceiveRecordResult(ReceiveRecordResult receiveRecordResult) {
        return receiveRecordResultMapper.insert(receiveRecordResult);
    }

    @Override
    public List<ReceiveRecordResult> selectByResultStatus(Long recordId, String resultStatus) {
        LambdaQueryWrapperX<ReceiveRecordResult> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(ReceiveRecordResult::getRecordId,recordId);
        queryWrapper.eq(ReceiveRecordResult::getResultStatus,resultStatus);
        return receiveRecordResultMapper.selectList(queryWrapper);
    }

    @Override
    public List<ReceiveRecordResult> selectByStatus(String recNumber, String resultStatus) {
        LambdaQueryWrapperX<ReceiveRecordResult> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(ReceiveRecordResult::getRecNumber,recNumber);
        queryWrapper.eq(ReceiveRecordResult::getResultStatus,resultStatus);
        return receiveRecordResultMapper.selectList(queryWrapper);
    }

    @Override
    public List<ReceiveRecordResult> selectReceiveRecordResult(String itemCode, String recNumber, String resultStatus) {
        LambdaQueryWrapperX<ReceiveRecordResult> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(ReceiveRecordResult::getItemCode,itemCode);
        queryWrapper.eq(ReceiveRecordResult::getRecNumber,recNumber);
        queryWrapper.eq(ReceiveRecordResult::getResultStatus,resultStatus);
        return receiveRecordResultMapper.selectList(queryWrapper);
    }
}
