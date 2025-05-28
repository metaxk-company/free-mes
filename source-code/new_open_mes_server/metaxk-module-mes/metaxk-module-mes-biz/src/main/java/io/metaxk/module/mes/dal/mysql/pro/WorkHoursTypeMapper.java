package io.metaxk.module.mes.dal.mysql.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkHoursTypeVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkHoursType;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工时类型 Mapper
 * @author 万界星空
 */
@Mapper
public interface WorkHoursTypeMapper extends BaseMapperX<WorkHoursType> {


    /**
     * 根据工时类型进行查询
     * @param workHoursTypeVO
     * @return PageResult<WorkHoursType>
     */
   default PageResult<WorkHoursType> list(WorkHoursTypeVo workHoursTypeVO){
      LambdaQueryWrapperX<WorkHoursType> queryWrapper = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(workHoursTypeVO.getWorkhoursType())){
          queryWrapper.eq(WorkHoursType::getWorkhoursType,workHoursTypeVO.getWorkhoursType());
      }
      if(StringUtils.isBlank(workHoursTypeVO.getWorkhoursType())){
          queryWrapper.isNotNull(WorkHoursType::getId);
      }
      return selectPage(workHoursTypeVO,queryWrapper);
  }


}
