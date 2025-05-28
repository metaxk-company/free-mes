package io.metaxk.module.mes.dal.mysql.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueType;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * io.metaxk.module.mes.dal.mysql.issue
 *
 * @author 万界星空
 * @time 2023/7/26 16:22
 */

@Mapper
public interface IssueTypeMapper extends BaseMapperX<IssueType> {

    default PageResult<IssueType> findPage(IssueTypeQueryVo issueTypeQueryVO) {
        LambdaQueryWrapperX<IssueType> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(issueTypeQueryVO.getName())){
            queryWrapperX.eq(IssueType::getName, issueTypeQueryVO.getName());
        }
        if(StringUtils.isNotBlank(issueTypeQueryVO.getSubTopic())){
            queryWrapperX.eq(IssueType::getSubTopic, issueTypeQueryVO.getSubTopic());
        }

        if(StringUtils.isBlank(issueTypeQueryVO.getName())
                && StringUtils.isBlank(issueTypeQueryVO.getSubTopic())){
            queryWrapperX.isNotNull(IssueType::getId);
        }
        return selectPage(issueTypeQueryVO, queryWrapperX);
    }

    /*@Select("SELECT type.name as type_name, mode.name as mode_name FROM issue_type as type Left Join issue_mode as mode ON type.name = mode.type")
    List<TypeAndModeVO> selectAllTypeNameWithModeNameList();*/

}
