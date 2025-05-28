package io.metaxk.module.mes.service.impl.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueModeQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueMode;
import io.metaxk.module.mes.dal.dataobject.issue.IssueType;
import io.metaxk.module.mes.dal.mysql.issue.IssueModeMapper;
import io.metaxk.module.mes.dal.mysql.issue.IssueTypeMapper;
import io.metaxk.module.mes.service.issue.IssueModeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.ISSUE_TYPE_NOT_EXIST;

/**
 * io.metaxk.module.mes.service.impl.issue
 *
 * @author 万界星空
 * @time 2023/7/27 16:45
 */

@Service
public class IssueModeServiceImpl implements IssueModeService {

    @Resource
    private IssueModeMapper issueModeMapper;
    @Resource
    private IssueTypeMapper issueTypeMapper;

    @Override
    public Integer saveIssueMode(IssueMode issueMode) {
        if (!typeExist(issueMode.getType()))
            throw exception(ISSUE_TYPE_NOT_EXIST);
        return issueModeMapper.insert(issueMode);
    }

    @Override
    public Integer removeIssueMode(List<Long> ids) {
        return issueModeMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateIssueMode(IssueMode issueMode) {
        if (!typeExist(issueMode.getType()))
            throw exception(ISSUE_TYPE_NOT_EXIST);
        return issueModeMapper.updateById(issueMode);
    }

    @Override
    public IssueMode findIssueModeById(Long id) {
        return issueModeMapper.selectById(id);
    }

    @Override
    public PageResult<IssueMode> findPage(IssueModeQueryVo issueModeQueryVo) {
        return issueModeMapper.findPage(issueModeQueryVo);
    }

    @Override
    public List<IssueMode> findByIssueType(String typeName) {
        return issueModeMapper.selectList(new LambdaQueryWrapperX<IssueMode>().eq(IssueMode::getType, typeName));
    }

    @Override
    public IssueMode findByName(String name) {
        return issueModeMapper.selectOne(new LambdaQueryWrapperX<IssueMode>().eq(IssueMode::getName, name));
    }

    private Boolean typeExist(String type) {
        if (type == null)
            return true;
        return issueTypeMapper.selectOne(new LambdaQueryWrapperX<IssueType>().eq(IssueType::getName, type)) != null;
    }

}
