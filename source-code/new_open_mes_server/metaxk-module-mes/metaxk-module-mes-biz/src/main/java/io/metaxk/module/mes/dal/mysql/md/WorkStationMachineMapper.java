package io.metaxk.module.mes.dal.mysql.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.md.vo.WorkstationMachineQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStationMachine;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工作站设备资源 Mapper
 * @author 万界星空
 */
@Mapper
public interface WorkStationMachineMapper extends BaseMapperX<WorkStationMachine> {

    /**
     * 工作站设备资源条件分页查询
     * @param workstationMachine
     * @return PageResult<WorkstationMachine>
     */
    default PageResult<WorkStationMachine> selectPage(WorkstationMachineQueryVo workstationMachine) {
        LambdaQueryWrapperX<WorkStationMachine> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotNull(workstationMachine.getWorkstationId())){
            queryWrapper.eq(WorkStationMachine::getWorkstationId,workstationMachine.getWorkstationId());
        }
        return  selectPage(workstationMachine,queryWrapper);
    }


    /**
     * 根据id查询工作站设备资源
     * @param id
     * @return WorkstationMachine
     */
   default WorkStationMachine selectByWorkstationId(Long id){
       LambdaQueryWrapperX<WorkStationMachine> queryWrapper = new LambdaQueryWrapperX<>();
       queryWrapper.eq(WorkStationMachine::getWorkstationId,id);
       return  selectOne(queryWrapper);
   }




}
