package io.metaxk.module.mes.service.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueModeQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueMode;

import java.util.List;

/**
 * io.metaxk.module.mes.service.issue
 *
 * @author 万界星空
 * @time 2023/7/27 09:37
 */

public interface IssueModeService {

    Integer saveIssueMode(IssueMode issueMode);
    Integer removeIssueMode(List<Long> ids);
    Integer updateIssueMode(IssueMode issueMode);
    IssueMode findIssueModeById(Long id);
    PageResult<IssueMode> findPage(IssueModeQueryVo issueModeQueryVo);

    List<IssueMode> findByIssueType(String typeName);

    IssueMode findByName(String name);
}
