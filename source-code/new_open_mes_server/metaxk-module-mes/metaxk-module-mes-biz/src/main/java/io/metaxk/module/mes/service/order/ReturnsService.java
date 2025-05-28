package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnsQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Returns;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnExcelVo;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/18 16:41
 */
public interface ReturnsService {

    Integer saveReturns(Returns returns);


    Returns findReturnById(Long id);

    Integer removeReturns(List<Long> ids);

    Integer updateReturns(Returns returns);

    PageResult<Returns> findPage(ReturnsQueryVo returnsQueryVo);

    List<ReturnExcelVo> exportData();
}
