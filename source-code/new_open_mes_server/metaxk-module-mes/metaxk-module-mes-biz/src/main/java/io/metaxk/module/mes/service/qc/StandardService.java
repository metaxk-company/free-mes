package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.StandardQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 13:43
 */
public interface StandardService {

    Integer saveInspectStandard(Standard inspectStandard);

    Integer removeInspectStandard(List<Long> ids);

    Standard findStandardById(Long id);

    Integer updateInspectStandard(Standard inspectStandard);

    PageResult<Standard> findPage(StandardQueryVo inspectStandardPageReqVO);

    List<Standard> findStandardList();

    List<Standard> findStandardByProcessCode(String code);
}
