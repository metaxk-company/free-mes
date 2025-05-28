package io.metaxk.module.mes.service.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueProblemSubmitQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueProblemSubmit;

import java.util.List;

/**
 * io.metaxk.module.mes.service.issue
 *
 * @author 万界星空
 * @time 2023/7/27 09:37
 */

public interface IssueProblemSubmitService {

    Integer saveIssueProblemSubmit(IssueProblemSubmit issueProblemSubmit);
    Integer removeIssueProblemSubmit(List<Long> ids);
    Integer updateIssueProblemSubmit(IssueProblemSubmit issueProblemSubmit);
    IssueProblemSubmit findIssueProblemSubmitById(Long id);
    PageResult<IssueProblemSubmit> findPage(IssueProblemSubmitQueryVo issueProblemSubmitQueryVo);

}
