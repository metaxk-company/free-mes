package io.metaxk.module.mes.dal.mysql.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueModeQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueMode;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * io.metaxk.module.mes.dal.mysql.issue
 *
 * @author 万界星空
 * @time 2023/7/26 16:22
 */

@Mapper
public interface IssueModeMapper extends BaseMapperX<IssueMode> {default PageResult<IssueMode> findPage(IssueModeQueryVo issueModeQueryVo) {
    LambdaQueryWrapperX<IssueMode> queryWrapperX = new LambdaQueryWrapperX<>();
    if(StringUtils.isNotBlank(issueModeQueryVo.getName())){
        queryWrapperX.eq(IssueMode::getName, issueModeQueryVo.getName());
    }
    if(StringUtils.isNotBlank(issueModeQueryVo.getType())){
        queryWrapperX.eq(IssueMode::getType, issueModeQueryVo.getType());
    }
    if(StringUtils.isNotBlank(issueModeQueryVo.getDescription())){
        queryWrapperX.eq(IssueMode::getDescription, issueModeQueryVo.getDescription());
    }

    if(StringUtils.isBlank(issueModeQueryVo.getName())
            && StringUtils.isBlank(issueModeQueryVo.getType())
            && StringUtils.isBlank(issueModeQueryVo.getDescription())){
        queryWrapperX.isNotNull(IssueMode::getId);
    }
    return selectPage(issueModeQueryVo, queryWrapperX);
}
    
}
