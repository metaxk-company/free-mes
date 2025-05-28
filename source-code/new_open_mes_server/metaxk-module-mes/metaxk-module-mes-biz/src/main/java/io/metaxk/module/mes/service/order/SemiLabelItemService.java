package io.metaxk.module.mes.service.order;

import io.metaxk.module.mes.dal.dataobject.order.SemiLabelItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
public interface SemiLabelItemService {

    List<SemiLabelItem> selectSemiLabelItemByNumber(String semiNumber);

    Integer deleteSemiLabelItem(String semiNumber);
}
