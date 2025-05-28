package io.metaxk.module.mes.service.impl.md;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.WorkstationMachineQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStationMachine;
import io.metaxk.module.mes.dal.mysql.md.WorkStationMachineMapper;
import io.metaxk.module.mes.service.md.WorkStationMachineService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.WORKSTATION_MACHINE_NOT_EXISTS;

/**
 * 设备资源 Service 实现类
 *
 * @author 万界星空MES
 */
@Service
public class WorkStationMachineServiceImpl implements WorkStationMachineService {

    @Resource
    private WorkStationMachineMapper workstationMachineMapper;

    @Override
    public Integer save(WorkStationMachine workstationMachine) {
       workstationMachine.setCreateTime(new Date());
       return   workstationMachineMapper.insert(workstationMachine);
    }



    @Override
    public Integer deleteWorkstationMachine(List<Long> ids) {
       return workstationMachineMapper.deleteBatchIds(ids);
    }

    private void validateWorkstationMachineExists(Long id) {
        if (workstationMachineMapper.selectById(id) == null) {
            throw exception(WORKSTATION_MACHINE_NOT_EXISTS);
        }
    }

    @Override
    public WorkStationMachine getWorkstationMachine(Long id) {
        return workstationMachineMapper.selectById(id);
    }



    @Override
    public PageResult<WorkStationMachine> getWorkstationMachinePage(WorkstationMachineQueryVo pageReqVO) {
        return workstationMachineMapper.selectPage(pageReqVO);
    }



    @Override
    public void deleteByWorkstationId(Long workstationId) {
        QueryWrapper<WorkStationMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workstation_id",workstationId);
        workstationMachineMapper.delete(queryWrapper);
    }


    /**
     * 检查机器资源是否已经被占用
     * 一台机器只能被分配到一个工作站中，且只能分配一次
     * @param workstationMachine
     * @return
     */
    @Override
    public WorkStationMachine checkMachineryExists(WorkStationMachine workstationMachine) {
        LambdaQueryWrapper<WorkStationMachine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkStationMachine::getMachineryCode,workstationMachine.getMachineryCode());
        queryWrapper. last("LIMIT 1");
        return  workstationMachineMapper.selectOne(queryWrapper);
    }

    @Override
    public  List<WorkStationMachine> findWorkstationMachineByWorkstationId(Long workstationId) {
        LambdaQueryWrapperX<WorkStationMachine> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkStationMachine::getWorkstationId,workstationId);
        return workstationMachineMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeByMachineryCode(String machineryCode) {
        LambdaQueryWrapperX<WorkStationMachine> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkStationMachine::getMachineryCode,machineryCode);
        return workstationMachineMapper.delete(queryWrapperX);
    }


}
