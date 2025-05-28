package io.metaxk.module.mes.service.qc;

import io.metaxk.module.mes.dal.dataobject.qc.OtherRecordItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
public interface OtherRecordItemService {

    Integer saveOtherRecordItem(OtherRecordItem otherRecordItem);

    List<OtherRecordItem> findOtherRecordItem(String recordNumber);
}
