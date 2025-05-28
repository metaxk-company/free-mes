package io.metaxk.module.mes.service.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.WorkstationMachineQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStationMachine;

/**
 * 工作站设备资源 Service 接口
 * @author 万界星空
 */
public interface WorkStationMachineService {


    /**
     * 新增设备资源
     * @param workstationMachine
     * @return Integer
     */
    Integer save(WorkStationMachine workstationMachine);


    /**
     * 删除设备资源
     * @param id
     * @return Integer
     */
    Integer deleteWorkstationMachine(List<Long> id);

    /**
     * 根据id查询设备资源
     * @param id
     * @return WorkstationMachine
     */
    WorkStationMachine getWorkstationMachine(Long id);


    /**
     * 设备资源条件分页查询
     * @param pageReqVO
     * @return  PageResult<WorkstationMachine>
     */
    PageResult<WorkStationMachine> getWorkstationMachinePage(WorkstationMachineQueryVo pageReqVO);

    /**
     * 根据工作站id查询设备资源
     * @param workstationId
     */
    void deleteByWorkstationId(Long workstationId);

    /**
     * 校验设备资源
     * @param workstationMachine
     * @return WorkstationMachine
     */
    WorkStationMachine checkMachineryExists(WorkStationMachine workstationMachine);

    /**
     * 设备资源列表
     * @param workstationId
     * @return List<WorkstationMachine>
     */
    List<WorkStationMachine> findWorkstationMachineByWorkstationId(Long workstationId);


    /**
     * 根据设备编号删除设备资源
     * @param machineryCode
     * @return Integer
     */
    Integer removeByMachineryCode(String machineryCode);
}
