package io.metaxk.module.mes.service.pro;

import java.util.*;

import io.metaxk.module.mes.controller.admin.pro.vo.WorkorderBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrderBom;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 生产工单BOM组成 Service 接口
 * @author 万界星空
 */
public interface WorkOrderBomService {

    /**
     * 删除工单BOM
     * @param workerId
     */
    void removeWorkOrderBomByWorkOrderId(Long workerId) ;

    /**
     * 新增工单BOM
     * @param createReqVO
     * @return Integer
     */
    Integer createWorkorderBom( WorkOrderBom createReqVO);

    /**
     * 修改工单BOM
     * @param updateReqVO
     */
    void updateWorkorderBom( WorkOrderBom updateReqVO);

    /**
     * 删除工单BOM
     * @param id
     */
    void deleteWorkorderBom(Long id);



    /**
     * 工单BOM条件分页查询
     * @param pageReqVO
     * @return PageResult<WorkerBom>
     */
    PageResult<WorkOrderBom> getWorkorderBomPage(WorkorderBomQueryVo pageReqVO);

    /**
     * 新增工单BOM
     * @param workerBom
     */
    void insertProWorkorderBom(WorkOrderBom workerBom);

    /**
     * 工单BOM列表
     * @param param
     * @return List<WorkerBom>
     */
    List<WorkOrderBom> selectProWorkorderBomList(WorkOrderBom param);
}
