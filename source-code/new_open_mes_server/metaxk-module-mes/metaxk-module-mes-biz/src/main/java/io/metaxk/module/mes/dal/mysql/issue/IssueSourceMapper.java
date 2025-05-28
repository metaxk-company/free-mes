package io.metaxk.module.mes.dal.mysql.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueSourceQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueSource;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * io.metaxk.module.mes.dal.mysql.issue
 *
 * @author 万界星空
 * @time 2023/7/26 16:22
 */

@Mapper
public interface IssueSourceMapper extends BaseMapperX<IssueSource> {

    default PageResult<IssueSource> findPage(IssueSourceQueryVo issueSourceQueryVo) {
        LambdaQueryWrapperX<IssueSource> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(issueSourceQueryVo.getName())){
            queryWrapperX.eq(IssueSource::getName, issueSourceQueryVo.getName());
        }
        if(StringUtils.isNotBlank(issueSourceQueryVo.getDescription())){
            queryWrapperX.eq(IssueSource::getDescription, issueSourceQueryVo.getDescription());
        }

        if(StringUtils.isBlank(issueSourceQueryVo.getName())
            && StringUtils.isBlank(issueSourceQueryVo.getDescription())){
            queryWrapperX.isNotNull(IssueSource::getId);
        }
        return selectPage(issueSourceQueryVo, queryWrapperX);
    }

    @Select("select * from issue_source where name = #{name}")
    List<IssueSource> findBySourceName(String name);

}
