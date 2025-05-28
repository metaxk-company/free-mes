package io.metaxk.module.mes.service.impl.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueProblemSubmitQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.*;
import io.metaxk.module.mes.dal.mysql.issue.*;
import io.metaxk.module.mes.service.issue.IssueProblemSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;

/**
 * io.metaxk.module.mes.service.impl.issue
 *
 * @author 万界星空
 * @time 2023/7/27 16:45
 */

@Service
public class IssueProblemSubmitServiceImpl implements IssueProblemSubmitService {

    @Resource
    private IssueProblemSubmitMapper issueProblemSubmitMapper;
    @Resource
    private IssueTypeMapper issueTypeMapper;
    @Resource
    private IssueModeMapper issueModeMapper;
    @Resource
    private IssueComponentMapper issueComponentMapper;
    @Resource
    private IssueSourceMapper issueSourceMapper;

    @Override
    public Integer saveIssueProblemSubmit(IssueProblemSubmit issueProblemSubmit) {
        if (!typeExist(issueProblemSubmit.getType()))
            throw exception(ISSUE_TYPE_NOT_EXIST);
        if (!componentExist(issueProblemSubmit.getComponent()))
            throw exception(ISSUE_COMPONENT_NOT_EXIST);
        if (!sourceExist(issueProblemSubmit.getSource()))
            throw exception(ISSUE_SOURCE_NOT_EXIST);
        if (!modeExist(issueProblemSubmit.getMode(), issueProblemSubmit.getType()))
            throw exception(ISSUE_MODE_NOT_EXIST);
        return issueProblemSubmitMapper.insert(issueProblemSubmit);
    }

    @Override
    public Integer removeIssueProblemSubmit(List<Long> ids) {
        return issueProblemSubmitMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateIssueProblemSubmit(IssueProblemSubmit issueProblemSubmit) {
        if (!typeExist(issueProblemSubmit.getType()))
            throw exception(ISSUE_TYPE_NOT_EXIST);
        if (!componentExist(issueProblemSubmit.getComponent()))
            throw exception(ISSUE_COMPONENT_NOT_EXIST);
        if (!sourceExist(issueProblemSubmit.getSource()))
            throw exception(ISSUE_SOURCE_NOT_EXIST);
        if (!modeExist(issueProblemSubmit.getMode(), issueProblemSubmit.getType()))
            throw exception(ISSUE_MODE_NOT_EXIST);
        return issueProblemSubmitMapper.updateById(issueProblemSubmit);
    }

    @Override
    public IssueProblemSubmit findIssueProblemSubmitById(Long id) {
        return issueProblemSubmitMapper.selectById(id);
    }

    @Override
    public PageResult<IssueProblemSubmit> findPage(IssueProblemSubmitQueryVo issueProblemSubmitQueryVo) {
        return issueProblemSubmitMapper.findPage(issueProblemSubmitQueryVo);
    }

    private Boolean typeExist(String type) {
        if (type == null)
            return true;
        return issueTypeMapper.selectOne(new LambdaQueryWrapperX<IssueType>().eq(IssueType::getName, type)) != null;
    }
    private Boolean componentExist(String component) {
        if (component == null)
            return true;
        return issueComponentMapper.selectOne(new LambdaQueryWrapperX<IssueComponent>().eq(IssueComponent::getName, component)) != null;
    }
    private Boolean sourceExist(String source) {
        if (source == null)
            return true;
        return issueSourceMapper.selectOne(new LambdaQueryWrapperX<IssueSource>().eq(IssueSource::getName, source)) != null;
    }
    private Boolean modeExist(String mode, String type) {
        if (mode == null && type == null)
            return true;
        return issueModeMapper.selectOne(new LambdaQueryWrapperX<IssueMode>().eq(IssueMode::getName, mode).eq(IssueMode::getType, type)) != null;
    }

}
