package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Task;
import io.metaxk.module.mes.dal.mysql.pro.TaskMapper;
import io.metaxk.module.mes.service.pro.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import javax.annotation.Resource;


/**
 * 生产任务 Service 实现类
 */
@Service
public class TaskServiceImpl  implements TaskService {


    @Resource
    public TaskMapper taskMapper;

    @Override
    public List<TaskListByConditionalVo> listByConditionals(ProTaskQueryVo task) {
        return  taskMapper.listByConditionals(task);
    }


    @Override
    public PageResult<Task> getTaskPage(TaskQueryVo pageReqVO) {
        return taskMapper.selectPage(pageReqVO);
    }




    @Override
    public List<TaskOrderVo> selectTaskOrder(String workorderCode) {
        return taskMapper.selectTaskOrder(workorderCode);
    }

    @Override
    public Task selectProTaskByTaskCode(String taskCode) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_code",taskCode);
        return taskMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer updateProTask(Task proTask) {
        proTask.setId(proTask.getId());
        return  taskMapper.updateById(proTask);
    }

    @Override
    public Task selectProTaskById(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public List<Task> selectProTaskList(Task param) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderId,param.getWorkorderId());
        return taskMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer insertProTask(Task proTask) {
        return taskMapper.insert(proTask);
    }


    @Override
    public Integer deleteProTaskByTIds(List<Long> ids) {
        return taskMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer update(TaskUpdateVo proTask) {
        return taskMapper.update(proTask);
    }


    @Override
    public List<TaskListByConditionalVo> exportTaskData() {
      List<TaskListByConditionalVo> dictList =  taskMapper.selectTaskList();
        ArrayList<TaskListByConditionalVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            TaskListByConditionalVo excelDictDTO = new TaskListByConditionalVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }



    @Override
    public List<Task> selectByCodeAndName(String workorderCode, String workorderName, String productCode, String productName, String workstationName) {
        return taskMapper.selectByCodeAndName(workorderCode,workorderName,productCode,productName,workstationName);
    }

    @Override
    public TaskListByConditionalVo findTaskMachineryCode(String workstationCode) {
        return taskMapper.TaskListByConditionalVO(workstationCode);
    }

    @Override
    public  List<Task>  findByWorkOrderCode(String workorderCode,Long processId) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderCode,workorderCode);
        queryWrapperX.eq(Task::getProcessId,processId);
        return taskMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Task> findTaskByProcessId(Long processId,Long  workstationId ) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getProcessId,processId);
        queryWrapperX.eq(Task::getWorkstationId,workstationId);
        return taskMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Task> findByOrderCode(String workorderCode) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderCode,workorderCode);
        return taskMapper.selectList(queryWrapperX);
    }

    @Override
    public List<TaskListByConditionalVo> findTaskByOrderCode(String workOrderCode) {
        return taskMapper.findTaskByOrderCode(workOrderCode);
    }

    @Override
    public List<Task> findTaskByWorkOrdercode(String workorderCode) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderCode,workorderCode);
        return taskMapper.selectList(queryWrapperX);
    }



    @Override
    public Task findTaskByWoroOrderCodeAndProcessId(String workorderCode, Long processId,String taskCode) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderCode,workorderCode);
        queryWrapperX.eq(Task::getProcessId,processId);
        queryWrapperX.eq(Task::getTaskCode,taskCode);
        queryWrapperX.last("LIMIT 1");
        return taskMapper.selectOne(queryWrapperX);
    }

    @Override
    public Integer deleteByWorkOrderId(Long id) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderId,id);
        return taskMapper.delete(queryWrapperX);
    }

    @Override
    public Task findTaskByTaskCode(String taskCode) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getTaskCode,taskCode);
        return taskMapper.selectOne(queryWrapperX);
    }

    @Override
    public TaskListByConditionalVo findTaskByCode(ProTaskQueryVo task) {
        return taskMapper.findTaskByCode(task);
    }

    @Override
    public List<Task> findTaskByOrderId(Long workorderId) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getWorkorderId,workorderId);
        return taskMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Task> findTaskList() {
        return taskMapper.selectList();
    }

    @Override
    public List<Task> findTaskByTeamCode(String teamCode) {
        LambdaQueryWrapperX<Task> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Task::getTeamCode,teamCode);
        return taskMapper.selectList(queryWrapperX);
    }




}
