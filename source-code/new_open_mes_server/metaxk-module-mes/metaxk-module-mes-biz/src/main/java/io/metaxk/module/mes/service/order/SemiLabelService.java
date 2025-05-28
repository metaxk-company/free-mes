package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabel;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
public interface SemiLabelService {

    PageResult<SemiLabel> findPage(SemiLabelQueryVo semiLabelQueryVo);

    Integer saveSemiLabel(SemiLabel semiLabel);

    Integer updateSemiLabel(SemiLabel semiLabel);

    Integer removeSemiLabelByIds(List<Long> ids);

    SemiLabel findSemiLabelById(Long id);

    List<SemiLabel> listAll();
    List<SemiLabelExportVo> listData();

    List<SemiLabelAllExportVo> listAllDataByIds(List<Integer> ids);
}
