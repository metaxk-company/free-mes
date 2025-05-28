package io.metaxk.module.mes.service.impl.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueMethodQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueMethod;
import io.metaxk.module.mes.dal.mysql.issue.IssueMethodMapper;
import io.metaxk.module.mes.service.issue.IssueMethodService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * io.metaxk.module.mes.service.impl.issue
 *
 * @author 万界星空
 * @time 2023/7/27 16:45
 */

@Service
public class IssueMethodServiceImpl implements IssueMethodService {

    @Resource
    private IssueMethodMapper issueMethodMapper;

    @Override
    public Integer saveIssueMethod(IssueMethod issueMethod) {
        return issueMethodMapper.insert(issueMethod);
    }

    @Override
    public Integer removeIssueMethod(List<Long> ids) {
        return issueMethodMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateIssueMethod(IssueMethod issueMethod) {
        return issueMethodMapper.updateById(issueMethod);
    }

    @Override
    public IssueMethod findIssueMethodById(Long id) {
        return issueMethodMapper.selectById(id);
    }

    @Override
    public PageResult<IssueMethod> findPage(IssueMethodQueryVo issueMethodQueryVo) {
        return issueMethodMapper.findPage(issueMethodQueryVo);
    }

    @Override
    public IssueMethod findByName(String name) {
        return issueMethodMapper.selectOne(new LambdaQueryWrapperX<IssueMethod>().eq(IssueMethod::getName, name));
    }

}
