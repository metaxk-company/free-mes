package io.metaxk.module.mes.service.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueType;

import java.util.List;

/**
 * io.metaxk.module.mes.service.issue
 *
 * @author 万界星空
 * @time 2023/7/27 09:37
 */

public interface IssueTypeService {

    Integer saveIssueType(IssueType issueType);
    Integer removeIssueType(List<Long> ids);
    Integer updateIssueType(IssueType issueType);
    IssueType findIssueTypeById(Long id);
    PageResult<IssueType> findPage(IssueTypeQueryVo issueTypeQueryVo);

    IssueType findByName(String name);
}
