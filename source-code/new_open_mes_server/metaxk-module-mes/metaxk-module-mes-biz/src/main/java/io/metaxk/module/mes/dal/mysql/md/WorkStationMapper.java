package io.metaxk.module.mes.dal.mysql.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.md.vo.WorkstationQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkstationMachVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工作站 Mapper
 * @author 万界星空
 */
@Mapper
public interface WorkStationMapper extends BaseMapperX<WorkStation> {


    /**
     * 工作站条件分页查询
     * @param reqVO
     * @return PageResult<Workstation>
     */
    default PageResult<WorkStation> selectPage(WorkstationQueryVo reqVO) {
        LambdaQueryWrapperX<WorkStation> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(reqVO.getWorkstationCode())){
            queryWrapper.eq(WorkStation::getWorkstationCode,reqVO.getWorkstationCode());
        }
        if(StringUtils.isNotBlank(reqVO.getWorkstationName())){
            queryWrapper.like(WorkStation::getWorkstationName,reqVO.getWorkstationName());
        }
        if(StringUtils.isNotNull(reqVO.getWorkshopId())){
            queryWrapper.eq(WorkStation::getWorkshopId,reqVO.getWorkshopId());
        }
        if(StringUtils.isNotNull(reqVO.getProcessId())){
            queryWrapper.eq(WorkStation::getProcessId,reqVO.getProcessId());
        }
        if(StringUtils.isBlank(reqVO.getWorkstationCode()) && StringUtils.isBlank(reqVO.getWorkstationName())
        && StringUtils.isNull(reqVO.getWorkshopId()) && StringUtils.isNull(reqVO.getProcessId())){
            queryWrapper.isNotNull(WorkStation::getId);
        }
        return selectPage(reqVO,queryWrapper);
    }


    /**
     * 查询工作站信息
     * @param createReqVO
     * @return Workstation
     */
    default WorkStation checkWorkStationCodeUnique(WorkStation createReqVO) {
        return selectOne(new LambdaQueryWrapperX<WorkStation>()
                .eqIfPresent(WorkStation::getWorkstationCode, createReqVO.getWorkstationCode()));
    }

    /**
     * 查询工作站信息
     * @param createReqVO
     * @return Workstation
     */
    default WorkStation checkWorkStationNameUnique(WorkStation createReqVO) {
        return selectOne(new LambdaQueryWrapperX<WorkStation>()
                .eqIfPresent(WorkStation::getWorkstationName, createReqVO.getWorkstationName()));
    }


    /**
     * 工作站集合
     * @return List<WorkstationMachVo>
     */
    List<WorkstationMachVo> selectWorkstationByCode();

    /**
     * 根据工作站编号查询工作站信息
     * @param workstationCode
     * @return Workstation
     */
   default WorkStation findWorkshopCodeByWorkstationCode(String workstationCode){
       LambdaQueryWrapperX<WorkStation> queryWrapper = new LambdaQueryWrapperX<>();
       queryWrapper.eq(WorkStation::getWorkstationCode,workstationCode);
       return selectOne(queryWrapper);
   }

    /**
     * 根据工作站编号，工作站名称查询工作站信息
     * @param workstationCode
     * @param workstationName
     * @return Workstation
     */
  default WorkStation selectByCodeAndName(String workstationCode, String workstationName){
      LambdaQueryWrapperX<WorkStation> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(WorkStation::getWorkstationCode,workstationCode);
      queryWrapperX.eq(WorkStation::getWorkstationName,workstationName);
      return  selectOne(queryWrapperX);
  }
}
