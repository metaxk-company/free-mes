package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
public interface ReceiveRecordService {

    PageResult<ReceiveRecord> findPage(ReceiveRecordQueryVo receiveRecordQueryVo);

    List<ReceiveRecordExcelVo> exportData();

    Integer updateReceiveRecord(ReceiveRecord receiveRecord);

    ReceiveRecord findReceiveRecordById(Long id);

    Integer updateStatus(Long id,String status);

    Integer updateReceiveRecords(ReceiveRecord receiveRecord);

    List<ReceiveRecord> findReceiveRecordByRecNumber(String recNumber);

    List<ReceiveRecord> findReceiveRecordByItemCode(String itemCode);
}
