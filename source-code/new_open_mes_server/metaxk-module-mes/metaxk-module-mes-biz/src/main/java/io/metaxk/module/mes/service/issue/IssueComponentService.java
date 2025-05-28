package io.metaxk.module.mes.service.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueComponentQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueComponent;

import java.util.List;

/**
 * io.metaxk.module.mes.service.issue
 *
 * @author 万界星空
 * @time 2023/7/27 09:37
 */

public interface IssueComponentService {

    Integer saveIssueComponent(IssueComponent issueComponent);
    Integer removeIssueComponent(List<Long> ids);
    Integer updateIssueComponent(IssueComponent issueComponent);
    IssueComponent findIssueComponentById(Long id);
    PageResult<IssueComponent> findPage(IssueComponentQueryVo issueComponentQueryVo);

    IssueComponent findByName(String name);
}
