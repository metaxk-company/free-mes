package io.metaxk.module.mes.dal.mysql.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.TemporaryWorkHours;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 临时工时管理 Mapper
 * @author 万界星空
 */
@Mapper
public interface TemporaryWorkHoursMapper extends BaseMapperX<TemporaryWorkHours> {

    /**
     *  临时工时管理条件分页
     * @param temporaryWorkHoursVO
     * @return PageResult<TemporaryWorkHours>
     */
  default   PageResult<TemporaryWorkHours> list(TemporaryWorkHoursVo temporaryWorkHoursVO){
      LambdaQueryWrapperX<TemporaryWorkHours> queryWrapper = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(temporaryWorkHoursVO.getWorkhoursType())){
          queryWrapper.eq(TemporaryWorkHours::getWorkhoursType,temporaryWorkHoursVO.getWorkhoursType());
      }
      if(StringUtils.isNotBlank(temporaryWorkHoursVO.getWorkerName())){
          queryWrapper.eq(TemporaryWorkHours::getWorkerName,temporaryWorkHoursVO.getWorkerName());
      }
      if(StringUtils.isNotBlank(temporaryWorkHoursVO.getWorkhours())){
          queryWrapper.like(TemporaryWorkHours::getWorkhours,temporaryWorkHoursVO.getWorkhours());
      }
      if(StringUtils.isNotBlank(temporaryWorkHoursVO.getWorkshopName())){
          queryWrapper.like(TemporaryWorkHours::getWorkshopName,temporaryWorkHoursVO.getWorkshopName());
      }
      if (StringUtils.isNotBlank(temporaryWorkHoursVO.getCreateTime()) && StringUtils.isNotBlank(temporaryWorkHoursVO.getEndTime())) {
          queryWrapper.between(TemporaryWorkHours::getCreateTime, temporaryWorkHoursVO.getCreateTime(), temporaryWorkHoursVO.getEndTime());
      }
      if(StringUtils.isBlank(temporaryWorkHoursVO.getWorkhoursType()) && StringUtils.isBlank(temporaryWorkHoursVO.getWorkhours()) &&
      StringUtils.isBlank(temporaryWorkHoursVO.getWorkerName()) && StringUtils.isBlank(temporaryWorkHoursVO.getWorkshopName())
      && StringUtils.isBlank(temporaryWorkHoursVO.getCreateTime()) && StringUtils.isBlank(temporaryWorkHoursVO.getEndTime())){
          queryWrapper.isNotNull(TemporaryWorkHours::getId);
      }
      return  selectPage(temporaryWorkHoursVO,queryWrapper);
  }





}
