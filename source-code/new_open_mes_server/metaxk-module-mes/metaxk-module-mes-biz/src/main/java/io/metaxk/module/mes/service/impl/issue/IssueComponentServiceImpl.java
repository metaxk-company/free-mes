package io.metaxk.module.mes.service.impl.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueComponentQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueComponent;
import io.metaxk.module.mes.dal.mysql.issue.IssueComponentMapper;
import io.metaxk.module.mes.service.issue.IssueComponentService;
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
public class IssueComponentServiceImpl implements IssueComponentService {

    @Resource
    private IssueComponentMapper issueComponentMapper;

    @Override
    public Integer saveIssueComponent(IssueComponent issueComponent) {
        return issueComponentMapper.insert(issueComponent);
    }

    @Override
    public Integer removeIssueComponent(List<Long> ids) {
        return issueComponentMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateIssueComponent(IssueComponent issueComponent) {
        return issueComponentMapper.updateById(issueComponent);
    }

    @Override
    public IssueComponent findIssueComponentById(Long id) {
        return issueComponentMapper.selectById(id);
    }

    @Override
    public PageResult<IssueComponent> findPage(IssueComponentQueryVo issueComponentQueryVo) {
        return issueComponentMapper.findPage(issueComponentQueryVo);
    }

    @Override
    public IssueComponent findByName(String name) {
        return issueComponentMapper.selectOne(new LambdaQueryWrapperX<IssueComponent>().eq(IssueComponent::getName, name));
    }

}
