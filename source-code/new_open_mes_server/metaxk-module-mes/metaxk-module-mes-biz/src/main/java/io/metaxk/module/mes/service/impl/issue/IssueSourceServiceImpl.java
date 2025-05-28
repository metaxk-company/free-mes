package io.metaxk.module.mes.service.impl.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueSourceQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueSource;
import io.metaxk.module.mes.dal.mysql.issue.IssueSourceMapper;
import io.metaxk.module.mes.service.issue.IssueSourceService;
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
public class IssueSourceServiceImpl implements IssueSourceService {

    @Resource
    private IssueSourceMapper issueSourceMapper;

    @Override
    public Integer saveIssueSource(IssueSource issueSource) {
        return issueSourceMapper.insert(issueSource);
    }

    @Override
    public Integer removeIssueSource(List<Long> ids) {
        return issueSourceMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateIssueSource(IssueSource issueSource) {
        return issueSourceMapper.updateById(issueSource);
    }

    @Override
    public IssueSource findIssueSourceById(Long id) {
        return issueSourceMapper.selectById(id);
    }

    @Override
    public PageResult<IssueSource> findPage(IssueSourceQueryVo issueSourceQueryVo) {
        return issueSourceMapper.findPage(issueSourceQueryVo);
    }

    @Override
    public List<IssueSource> findBySourceName(String name) {
        return issueSourceMapper.findBySourceName(name);
    }

    @Override
    public IssueSource findByName(String name) {
        return issueSourceMapper.selectOne(new LambdaQueryWrapperX<IssueSource>().eq(IssueSource::getName, name));
    }

}
