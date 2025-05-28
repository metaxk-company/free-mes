package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProjectClassify;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/6 11:38
 */
@Mapper
public interface ProjectClassifyMapper extends BaseMapperX<ProjectClassify> {

    /**
     *  检测项目分类条件分页查询
      * @param inspectProjectClassifyPageVO
     * @return  PageResult<InspectProjectClassify>
     */
   default   PageResult<ProjectClassify> findPage(ProjectClassifyQueryVo inspectProjectClassifyPageVO){
      LambdaQueryWrapperX<ProjectClassify> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(inspectProjectClassifyPageVO.getProjectCode())){
          queryWrapperX.eq(ProjectClassify::getProjectCode,inspectProjectClassifyPageVO.getProjectCode());
      }
      if(StringUtils.isNotBlank(inspectProjectClassifyPageVO.getProjectName())){
          queryWrapperX.eq(ProjectClassify::getProjectName,inspectProjectClassifyPageVO.getProjectName());
      }
      if(StringUtils.isBlank(inspectProjectClassifyPageVO.getProjectCode()) && StringUtils.isBlank(inspectProjectClassifyPageVO.getProjectName())){
          queryWrapperX.isNotNull(ProjectClassify::getId);
      }
      return  selectPage(inspectProjectClassifyPageVO,queryWrapperX);
   }


    /**
     * 根据分类编号，名称查询分类信息
     * @param projectCode
     * @param projectName
     * @return InspectProjectClassify
     */
   default ProjectClassify findClassifyByCodeAndName(String projectCode, String projectName){
       LambdaQueryWrapperX<ProjectClassify> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(ProjectClassify::getProjectCode,projectCode);
       queryWrapperX.eq(ProjectClassify::getProjectName,projectName);
       return  selectOne(queryWrapperX);
   }



}
