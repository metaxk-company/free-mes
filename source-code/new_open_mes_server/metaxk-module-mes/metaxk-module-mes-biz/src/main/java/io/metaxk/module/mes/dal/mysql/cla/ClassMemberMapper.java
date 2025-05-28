package io.metaxk.module.mes.dal.mysql.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassMember;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/6/27 16:01
 */
@Mapper
public interface ClassMemberMapper extends BaseMapperX<ClassMember> {

  default   PageResult<ClassMember> selectClassMemberByCode(ClaMemberQueryVo classMemberPageReqVO){
      LambdaQueryWrapperX<ClassMember> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(classMemberPageReqVO.getTeamCode())){
          queryWrapperX.eq(ClassMember::getTeamCode,classMemberPageReqVO.getTeamCode());
      }
      return  selectPage(classMemberPageReqVO,queryWrapperX);
  }
}
