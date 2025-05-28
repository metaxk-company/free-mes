package io.metaxk.module.mes.dal.mysql.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassTeam;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/6/26 9:41
 */
@Mapper
public interface ClassTeamMapper extends BaseMapperX<ClassTeam> {

    /**
     *  班组分页条件查询
      * @param calTeamPageReqVO
     * @return PageResult<CalTeam>
     */
  default   PageResult<ClassTeam> getCalTeamPage(ClaTeamQueryVo calTeamPageReqVO){
      LambdaQueryWrapperX<ClassTeam> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(calTeamPageReqVO.getTeamType())){
          queryWrapperX.eq(ClassTeam::getTeamType,calTeamPageReqVO.getTeamType());
      }

      if(StringUtils.isNotBlank(calTeamPageReqVO.getTeamCode())){
          queryWrapperX.eq(ClassTeam::getTeamCode,calTeamPageReqVO.getTeamCode());
      }

      if(StringUtils.isNotBlank(calTeamPageReqVO.getTeamName())){
          queryWrapperX.eq(ClassTeam::getTeamName,calTeamPageReqVO.getTeamName());
      }
      if(StringUtils.isBlank(calTeamPageReqVO.getTeamType()) && StringUtils.isBlank(calTeamPageReqVO.getTeamCode()) && StringUtils.isBlank(calTeamPageReqVO.getTeamName())){
          queryWrapperX.isNotNull(ClassTeam::getId);
      }

      return  selectPage(calTeamPageReqVO,queryWrapperX);
  }
}
