package io.metaxk.module.mes.service.qc;

import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordItemsVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecordItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
public interface ReceiveRecordItemService {

    int getCount(Long recordId);

    String selectMaxDetectionOrderNumber(Long recordId);

    List<ReceiveRecordItemsVo> getReceiveRecordItems(Long recordId,String itemCode,String maxSortNumber);

    ReceiveRecordItem selectReceiveRecordItem(Long recordId,Long standardItemId,String sortNumber);

    Integer insertReceiveRecordItem(ReceiveRecordItem receiveRecordItem);

    Integer updateReceiveRecordItem(ReceiveRecordItem receiveRecordItem);

    Integer updateReceiveRecordItem(Long recordId,String itemBarCode,String flag,String sortNumber);

    List<ReceiveRecordItem> selectReceiveRecordItem(String sortNumber, String status);
}
