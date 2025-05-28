package io.metaxk.module.mes.service.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Task;
import java.util.List;


/**
 * 生产任务 Service 接口
 * @author 万界星空
 */
public interface TaskService  {

    /**
     * 生产任务条件查询
     * @param taskDO
     * @return List<TaskListByConditionalVO>
     */
    List<TaskListByConditionalVo> listByConditionals(ProTaskQueryVo taskDO);

    /**
     * 生产任务条件分页查询
     * @param pageReqVO
     * @return PageResult<Task>
     */
    PageResult<Task> getTaskPage(TaskQueryVo pageReqVO);

    /**
     * 根据订单编号查询生产任务
     * @param workerCode
     * @return List<TaskOrderVo>
     */
    List<TaskOrderVo> selectTaskOrder(String workerCode);

    /**
     * 根据任务编号查询生产任务
     * @param taskCode
     * @return Task
     */
    Task selectProTaskByTaskCode(String taskCode);

    /**
     * 修改生产任务
     * @param proTask
     * @return Integer
     */
    Integer updateProTask(Task proTask);

    /**
     * 根据id查询生产任务
     * @param id
     * @return Task
     */
    Task selectProTaskById(Long id);

    /**
     * 生产任务列表
     * @param param
     * @return  List<Task>
     */
    List<Task> selectProTaskList(Task param);

    /**
     * 新增生产任务
     * @param proTask
     * @return Integer
     */
    Integer insertProTask(Task proTask);

    /**
     * 删除生产任务
     * @param ids
     * @return Integer
     */
    Integer deleteProTaskByTIds(List<Long> ids);


    /**
     * 修改生产任务
     * @param proTask
     * @return Integer
     */
    Integer update(TaskUpdateVo proTask);

    /**
     * 导出生产任务
     * @return  List<TaskListByConditionalVO>
     */
    List<TaskListByConditionalVo> exportTaskData();


    /**
     * 多条件查询生产任务
     * @param workerCode
     * @param workerName
     * @param productCode
     * @param productName
     * @param workstationName
     * @return List<Task>
     */
    List<Task> selectByCodeAndName(String workerCode, String workerName, String productCode, String productName, String workstationName);

    /**
     * 根据工作站编号查询生产任务
     * @param workstationCode
     * @return TaskListByConditionalVO
     */
    TaskListByConditionalVo findTaskMachineryCode(String workstationCode);

    /**
     * 根据工单编号工序id查询生产任务
     * @param workerCode
     * @param processId
     * @return  List<Task>
     */
    List<Task>  findByWorkOrderCode(String workerCode,Long  processId);

    /**
     * 根据工序id工作站id查询生产任务
     * @param processId
     * @param workstationId
     * @return  List<Task>
     */
    List<Task> findTaskByProcessId(Long processId,Long  workstationId );

    /**
     * 根据订单编号查询生产任务
     * @param workerCode
     * @return  List<Task>
     */
    List<Task> findByOrderCode(String workerCode);

    /**
     * 根据订单编号查询生产任务
     * @param workOrderCode
     * @return List<TaskListByConditionalVO>
     */
    List<TaskListByConditionalVo> findTaskByOrderCode(String workOrderCode);

    /**
     * 根据订单编号查询生产任务
     * @param workerCode
     * @return List<Task>
     */
    List<Task> findTaskByWorkOrdercode(String workerCode);

    /**
     * 用作修改，根据工单以及工序查询当前的生产任务
     * @param workerCode
     * @param processId
     * @param taskCode
     * @return Task
     */
    Task findTaskByWoroOrderCodeAndProcessId(String workerCode, Long processId,String taskCode);

    /**
     * 删除订单
     * @param id
     * @return Integer
     */
    Integer deleteByWorkOrderId(Long id);

    /**
     * 根据任务编号查询生产任务
     * @param taskCode
     * @return Task
     */
    Task findTaskByTaskCode(String taskCode);

    /**
     *
     * @param task
     * @return TaskListByConditionalVO
     */
    TaskListByConditionalVo findTaskByCode(ProTaskQueryVo task);

    /**
     * 根据订单id查询任务信息
     * @param workorderId
     * @return List<Task>
     */
    List<Task> findTaskByOrderId(Long workorderId);

    /**
     * 查询任务全部信息
     * @return List<Task>
     */
    List<Task> findTaskList();

    /**
     * 根据班组编号查询任务信息
     * @param teamCode
     * @return List<Task>
     */
    List<Task> findTaskByTeamCode(String teamCode);


}
