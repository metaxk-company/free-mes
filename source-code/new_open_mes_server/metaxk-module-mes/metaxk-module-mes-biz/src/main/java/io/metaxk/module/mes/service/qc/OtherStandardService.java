package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.OtherStandard;
import io.metaxk.module.mes.dal.dataobject.qc.OtherStandardItem;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
public interface OtherStandardService {

    PageResult<OtherStandard>  findPage(OtherStandardQueryVo otherStandardQueryVo);

    List<OtherStandard> findOtherStandardList();

    List<OtherStandard> findOtherStandardByModel(String model);

    List<OtherStandard> findOtherStandardBySpec(String spec);

    List<OtherStandard> findOtherStandardByLineType(String lineType);

    Integer saveOtherStandard(OtherStandard otherStandard);

    Integer updateOtherStandard(OtherStandard otherStandard);

    OtherStandard findOtherStandard(Long id);

    Integer deleteOtherStandard(List<Long> ids);

    OtherStandard findOtherStandard(String model, String spec, String lineType);
}
