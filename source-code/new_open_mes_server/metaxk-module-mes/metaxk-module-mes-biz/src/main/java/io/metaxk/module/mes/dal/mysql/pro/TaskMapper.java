package io.metaxk.module.mes.dal.mysql.pro;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Task;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 生产任务 Mapper
 * @author 万界星空
 */
@Mapper
public interface TaskMapper extends BaseMapperX<Task> {


    /**
     * 条件查询生产任务
     * @param taskDO
     * @return List<TaskListByConditionalVO>
     */
    List<TaskListByConditionalVo> listByConditionals(ProTaskQueryVo taskDO);


    /**
     * 生产任务条件分页查询
     * @param reqVO
     * @return PageResult<Task>
     */
    default PageResult<Task> selectPage(TaskQueryVo reqVO) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotNull(reqVO.getWorkorderId())){
            queryWrapperX.eq(Task::getWorkorderId,reqVO.getWorkorderId());
        }
        if(StringUtils.isNotNull(reqVO.getProcessId())){
            queryWrapperX.eq(Task::getProcessId,reqVO.getProcessId());
        }
        if(StringUtils.isNull(reqVO.getWorkorderId()) && StringUtils.isNull(reqVO.getProcessId())){
            queryWrapperX.isNotNull(Task::getId);
        }
        return  selectPage(reqVO,queryWrapperX);
    }


    /**
     * 根据订单编号查询
     * @param workorderCode
     * @return List<TaskOrderVo>
     */
    List<TaskOrderVo> selectTaskOrder(String workorderCode);


    /**
     * 根据任务编号查询生产任务
     * @param taskCode
     * @return Task
     */
    default Task findTaskByTaskCode(String taskCode) {
        LambdaQueryWrapperX<Task> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(Task::getTaskCode,taskCode);
        return  selectOne(queryWrapper);
    }


    /**
     * 修改生产任务
     * @param proTask
     * @return int
     */
    int update(TaskUpdateVo proTask);

    /**
     * 生产任务集合
     * @return List<TaskListByConditionalVO>
     */
    List<TaskListByConditionalVo> selectTaskList();

    /**
     * 根据工单号，名称，产品编号，产品名称，工作站名称查询任务
     * @param workorderCode
     * @param workorderName
     * @param productCode
     * @param productName
     * @param workstationName
     * @return  List<Task>
     */
     default List<Task> selectByCodeAndName(String workorderCode, String workorderName, String productCode, String productName, String workstationName){
         LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
         queryWrapperX.eq(Task::getWorkorderCode,workorderCode);
         queryWrapperX.eq(Task::getWorkorderName,workorderName);
         queryWrapperX.eq(Task::getItemCode,productCode);
         queryWrapperX.eq(Task::getItemName,productName);
         queryWrapperX.eq(Task::getWorkstationName,workstationName);
         return  selectList(queryWrapperX);
     }

    /**
     * 根据工作站编号进行查询
     * @param workstationCode
     * @return TaskListByConditionalVO
     */
    TaskListByConditionalVo TaskListByConditionalVO(String workstationCode);


    /**
     * 根据工单号查询
     * @param workOrderCode
     * @return List<TaskListByConditionalVO>
     */
    List<TaskListByConditionalVo> findTaskByOrderCode(String workOrderCode);



    /**
     * 任务条件分页查询
     * @param task
     * @return  List<TaskListByConditionalVO>
     */
    TaskListByConditionalVo findTaskByCode(ProTaskQueryVo task);


}
