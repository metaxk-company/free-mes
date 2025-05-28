package io.metaxk.module.mes.dal.mysql.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueComponentQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueComponent;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * io.metaxk.module.mes.dal.mysql.issue
 *
 * @author 万界星空
 * @time 2023/7/26 16:20
 */

@Mapper
public interface IssueComponentMapper extends BaseMapperX<IssueComponent> {

    default PageResult<IssueComponent> findPage(IssueComponentQueryVo issueComponentQueryVo) {
        LambdaQueryWrapperX<IssueComponent> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(issueComponentQueryVo.getName())){
            queryWrapperX.eq(IssueComponent::getName, issueComponentQueryVo.getName());
        }
        if(StringUtils.isNotBlank(issueComponentQueryVo.getDescription())){
            queryWrapperX.eq(IssueComponent::getDescription, issueComponentQueryVo.getDescription());
        }

        if(StringUtils.isBlank(issueComponentQueryVo.getName())
                && StringUtils.isBlank(issueComponentQueryVo.getDescription())){
            queryWrapperX.isNotNull(IssueComponent::getId);
        }
        return selectPage(issueComponentQueryVo, queryWrapperX);
    }

}
