package io.metaxk.module.mes.service.qc;

import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecordResult;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 17:39
 */
public interface ProcessRecordResultService {

    public void insertProcessFormResult(ProcessRecordResult processFormResult);

    public List<ProcessRecordResult> selectByProcessFormId(Long processFormId);

    public List<ProcessRecordResult> selectByResultStatus(Long processFormId,String resultStatus);
}
