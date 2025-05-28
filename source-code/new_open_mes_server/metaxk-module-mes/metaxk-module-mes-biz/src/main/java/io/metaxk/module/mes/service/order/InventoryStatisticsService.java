package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsQueryVO;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsVO;

/**
 * io.metaxk.module.mes.service.order
 *
 * @author xx
 * @time 2023/8/17 11:51
 */
public interface InventoryStatisticsService {
    PageResult<InventoryStatisticsVO> InventoryStatistics(String Mode, InventoryStatisticsQueryVO inventoryStatisticsQueryVO);
}
