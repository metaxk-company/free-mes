package io.metaxk.module.mes.dal.mysql.issue;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueProblemSubmitQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueProblemSubmit;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * io.metaxk.module.mes.dal.mysql.issue
 *
 * @author 万界星空
 * @time 2023/7/26 16:21
 */

@Mapper
public interface IssueProblemSubmitMapper extends BaseMapperX<IssueProblemSubmit> {
    default PageResult<IssueProblemSubmit> findPage(IssueProblemSubmitQueryVo issueProblemSubmitQueryVo) {
        LambdaQueryWrapperX<IssueProblemSubmit> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getUsername())){
            queryWrapperX.eq(IssueProblemSubmit::getUsername, issueProblemSubmitQueryVo.getUsername());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getWorkshopAffiliation())){
            queryWrapperX.eq(IssueProblemSubmit::getWorkshopAffiliation, issueProblemSubmitQueryVo.getWorkshopAffiliation());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getSource())){
            queryWrapperX.eq(IssueProblemSubmit::getSource, issueProblemSubmitQueryVo.getSource());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getComponent())){
            queryWrapperX.eq(IssueProblemSubmit::getComponent, issueProblemSubmitQueryVo.getComponent());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getType())){
            queryWrapperX.eq(IssueProblemSubmit::getType, issueProblemSubmitQueryVo.getType());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getMode())){
            queryWrapperX.eq(IssueProblemSubmit::getMode, issueProblemSubmitQueryVo.getMode());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getDescription())){
            queryWrapperX.eq(IssueProblemSubmit::getDescription, issueProblemSubmitQueryVo.getDescription());
        }
        if(StringUtils.isNotBlank(issueProblemSubmitQueryVo.getStatus())){
            queryWrapperX.eq(IssueProblemSubmit::getStatus, issueProblemSubmitQueryVo.getStatus());
        }

        if(StringUtils.isBlank(issueProblemSubmitQueryVo.getUsername())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getWorkshopAffiliation())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getSource())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getComponent())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getType())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getMode())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getDescription())
                && StringUtils.isBlank(issueProblemSubmitQueryVo.getStatus())){
            queryWrapperX.isNotNull(IssueProblemSubmit::getId);
        }
        return selectPage(issueProblemSubmitQueryVo, queryWrapperX);
    }
}
