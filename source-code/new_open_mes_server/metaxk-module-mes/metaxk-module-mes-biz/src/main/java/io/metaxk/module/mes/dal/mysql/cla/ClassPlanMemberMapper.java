package io.metaxk.module.mes.dal.mysql.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlanMember;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/6/30 13:47
 */
@Mapper
public interface ClassPlanMemberMapper extends BaseMapperX<ClassPlanMember> {


  default   PageResult<ClassPlanMember> findClassPlanMemberPage(ClaPlanMemberQueryVo classPlanMemberPageReqVO){
      LambdaQueryWrapperX<ClassPlanMember> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(classPlanMemberPageReqVO.getPlanCode())){
          queryWrapperX.eq(ClassPlanMember::getPlanCode,classPlanMemberPageReqVO.getPlanCode());
      }
      return  selectPage(classPlanMemberPageReqVO,queryWrapperX);
  }



}
