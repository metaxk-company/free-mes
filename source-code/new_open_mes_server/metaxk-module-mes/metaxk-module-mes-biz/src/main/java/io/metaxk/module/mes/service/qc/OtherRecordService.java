package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecord;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/19 10:43
 */
public interface OtherRecordService {

    PageResult<OtherRecord> findPage(OtherRecordQueryVo otherRecordQueryVo);

    List<OtherRecordExcelVo> exportDate();

    Integer saveOtherRecord(OtherRecord otherRecord);

    Integer updateOtherRecord(OtherRecord otherRecord);


    OtherRecord findOtherRecordById(Long id);
}
