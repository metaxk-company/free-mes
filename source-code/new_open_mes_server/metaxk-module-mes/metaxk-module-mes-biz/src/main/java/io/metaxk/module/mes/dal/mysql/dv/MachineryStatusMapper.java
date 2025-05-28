package io.metaxk.module.mes.dal.mysql.dv;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryStatusQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryStatus;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 设备状态 Mapper
 * @author 万界星空
 */
@Mapper
public interface MachineryStatusMapper extends BaseMapperX<MachineryStatus> {


    /**
     * 设备状态条件分页查询
     * @param machineryStatusPageVO
     * @return PageResult<MachineryStatus>
     */
  default   PageResult<MachineryStatus> findList(MachineryStatusQueryVo machineryStatusPageVO){
      LambdaQueryWrapperX<MachineryStatus> queryWrapperX = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotBlank(machineryStatusPageVO.getStatusName())){
          queryWrapperX.eq(MachineryStatus::getStatusName,machineryStatusPageVO.getStatusName());
      }
      if(StringUtils.isBlank(machineryStatusPageVO.getStatusName())){
          queryWrapperX.isNotNull(MachineryStatus::getId);
      }
      return  selectPage(machineryStatusPageVO,queryWrapperX);
  }










}
