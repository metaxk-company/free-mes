package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Project;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/6 13:20
 */
@Mapper
public interface ProjectMapper extends BaseMapperX<Project> {

  default   PageResult<Project> findPage(ProjectQueryVo reqVO){
      LambdaQueryWrapperX<Project> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(reqVO.getProjectCode())){
          queryWrapperX.eq(Project::getProjectCode,reqVO.getProjectCode());
      }
      if(StringUtils.isNotBlank(reqVO.getProjectName())){
          queryWrapperX.eq(Project::getProjectName,reqVO.getProjectName());
      }
      if(StringUtils.isBlank(reqVO.getProjectName()) && StringUtils.isBlank(reqVO.getProjectCode())){
          queryWrapperX.isNotNull(Project::getId);
      }
      return  selectPage(reqVO,queryWrapperX);
  }


  default Project findProjectByCodeAndName(String projectCode, String projectName){
      LambdaQueryWrapperX<Project> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(Project::getProjectCode,projectCode);
      queryWrapperX.eq(Project::getProjectName,projectName);
      return  selectOne(queryWrapperX);
  }
}
