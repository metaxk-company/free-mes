package io.metaxk.module.mes.service.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueMethodQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueMethod;

import java.util.List;

/**
 * io.metaxk.module.mes.service.issue
 *
 * @author 万界星空
 * @time 2023/7/27 09:37
 */

public interface IssueMethodService {

    Integer saveIssueMethod(IssueMethod issueMethod);
    Integer removeIssueMethod(List<Long> ids);
    Integer updateIssueMethod(IssueMethod issueMethod);
    IssueMethod findIssueMethodById(Long id);
    PageResult<IssueMethod> findPage(IssueMethodQueryVo issueMethodQueryVo);

    IssueMethod findByName(String name);
}
