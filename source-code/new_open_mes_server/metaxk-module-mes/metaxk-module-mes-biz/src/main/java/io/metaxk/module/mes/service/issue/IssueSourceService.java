package io.metaxk.module.mes.service.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueSourceQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueSource;

import java.util.List;

/**
 * io.metaxk.module.mes.service.issue
 *
 * @author 万界星空
 * @time 2023/7/27 16:37
 */

public interface IssueSourceService {

    Integer saveIssueSource(IssueSource issueSource);
    Integer removeIssueSource(List<Long> ids);
    Integer updateIssueSource(IssueSource issueSource);
    IssueSource findIssueSourceById(Long id);
    PageResult<IssueSource> findPage(IssueSourceQueryVo issueSourceQueryVo);

    List<IssueSource> findBySourceName(String name);

    IssueSource findByName(String name);
}
