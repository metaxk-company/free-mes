package io.metaxk.module.mes.dal.mysql.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassTeamType;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



/**
 * 班组类型 Mapper接口
 * @author 万界星空
 */
@Mapper
public interface ClassTeamTypeMapper extends BaseMapperX<ClassTeamType> {


    /**
     * 班组类型条件分页查询
     * @param calTeamTypePageReqVO
     * @return  PageResult<CalTeamType>
     */
     default    PageResult<ClassTeamType> selectPage(ClaTeamTypeQueryVo calTeamTypePageReqVO){
         LambdaQueryWrapperX<ClassTeamType> queryWrapperX = new LambdaQueryWrapperX<>();
         if(StringUtils.isNotBlank(calTeamTypePageReqVO.getTypeName())){
             queryWrapperX.eq(ClassTeamType::getTypeName,calTeamTypePageReqVO.getTypeName());
         }
         if(StringUtils.isNotBlank(calTeamTypePageReqVO.getCreator())){
             queryWrapperX.eq(ClassTeamType::getCreator,calTeamTypePageReqVO.getCreator());
         }
         if(StringUtils.isNotBlank(calTeamTypePageReqVO.getCreateTime())){
             LocalDate createDate = LocalDate.parse(calTeamTypePageReqVO.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             queryWrapperX.like(ClassTeamType::getCreateTime,createDate);
         }
         if(StringUtils.isBlank(calTeamTypePageReqVO.getTypeName()) && StringUtils.isBlank(calTeamTypePageReqVO.getCreator()) && StringUtils.isBlank(calTeamTypePageReqVO.getCreateTime())){
             queryWrapperX.isNotNull(ClassTeamType::getId);
         }
         return  selectPage(calTeamTypePageReqVO,queryWrapperX);
     }




}
