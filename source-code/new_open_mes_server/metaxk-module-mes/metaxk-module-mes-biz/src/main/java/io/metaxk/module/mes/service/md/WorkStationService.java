package io.metaxk.module.mes.service.md;

import java.util.*;
import com.baomidou.mybatisplus.extension.service.IService;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.*;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;

/**
 * 工作站 Service 接口
 * @author 万界星空
 */
public interface WorkStationService extends IService<WorkStation> {



    /**
     * 根据id查询工作站
     * @param id
     * @return Workstation
     */
    WorkStation getWorkstation(Long id);


    /**
     * 工作站条件分页查询
     * @param pageReqVO
     * @return PageResult<Workstation>
     */
    PageResult<WorkStation> getWorkstationPage(WorkstationQueryVo pageReqVO);


    /**
     * 校验工作站
     * @param createReqVO
     * @return String
     */
    String checkWorkStationCodeUnique(WorkStation createReqVO);

    /**
     * 校验工作站
     * @param createReqVO
     * @return String
     */
    String checkWorkStationNameUnique(WorkStation createReqVO);

    /**
     * 新增工作站
     * @param createReqVO
     * @return Integer
     */
    Integer insert(WorkStation createReqVO);

    /**
     * 修改工作站
     * @param createReqVO
     * @return Integer
     */
    Integer updateWorkstations(WorkStation createReqVO);

    /**
     * 根据工作站id查询工作站
     * @param workstationId
     * @return Workstation
     */
    WorkStation selectWorkstationById(Long workstationId);

    /**
     * 导出工作站
     * @return List<WorkstationExcelVO>
     */
    List<WorkstationExcelVo> listData();


    /**
     * 根据工作站id查询工作站信息
     * @param workstationId
     * @return Workstation
     */
    WorkStation selectMdWorkstationByWorkstationId(Long workstationId);

    /**
     * 根据工序id查询工作站列表
     * @param processId
     * @return  List<Workstation>
     */
    List<WorkStation> findWorkStationByProcessId(Long processId);
}
