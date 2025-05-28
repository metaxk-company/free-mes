package io.metaxk.module.mes.service.qc;

import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 11:01
 */
public interface ReceiveStandardItemService {

    Integer saveReceiveStandardItem(ReceiveStandardItem receiveStandardItem);

    ReceiveStandardItem findReceiveStandardItemByCondition(String recStandardNumber,String name,String standard,String device);

    List<ReceiveStandardItem> findReceiveStandardItemByCode(String recStandardNumber);

    Integer removeCode(String number);

    Integer removeReceiveStandardItemByCode(String deviceCode,String number);

    List<ReceiveStandardItem> findReceiveStandardItem(String itemCode,String enableFlag);

    List<ReceiveStandardItem> findReceiveStandardItemByItemCode(String itemCode);
}
