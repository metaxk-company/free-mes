package io.metaxk.module.bpm.convert.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import io.metaxk.framework.common.util.collection.CollectionUtils;
import io.metaxk.framework.common.util.number.NumberUtils;
import io.metaxk.module.bpm.controller.admin.task.vo.task.BpmTaskDonePageItemRespVO;
import io.metaxk.module.bpm.controller.admin.task.vo.task.BpmTaskRespVO;
import io.metaxk.module.bpm.controller.admin.task.vo.task.BpmTaskTodoPageItemRespVO;
import io.metaxk.module.bpm.dal.dataobject.task.BpmTaskExtDO;
import io.metaxk.module.bpm.service.message.dto.BpmMessageSendWhenTaskCreatedReqDTO;
import io.metaxk.module.system.api.dept.dto.DeptRespDTO;
import io.metaxk.module.system.api.user.dto.AdminUserRespDTO;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bpm 任务 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface BpmTaskConvert {

    BpmTaskConvert INSTANCE = Mappers.getMapper(BpmTaskConvert.class);

    /**
     * 复制对象
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     * @param <T>
     *
     * @return
     */
    public static <T> T copy(Object source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        try {
            T newInstance = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T, K> List<K> copyList(List<T> source, Class<K> target) {
        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(e -> copy(e, target)).collect(Collectors.toList());
    }

    default List<BpmTaskTodoPageItemRespVO> convertList1(List<Task> tasks,
        Map<String, ProcessInstance> processInstanceMap, Map<Long, AdminUserRespDTO> userMap) {
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskTodoPageItemRespVO respVO = convert1(task);
            ProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                AdminUserRespDTO startUser = userMap.get(NumberUtils.parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            return respVO;
        });
    }

    @Mapping(source = "suspended", target = "suspensionState", qualifiedByName = "convertSuspendedToSuspensionState")
    @Mapping(target = "claimTime", expression = "java(bean.getClaimTime()==null?null: LocalDateTime.ofInstant(bean.getClaimTime().toInstant(),ZoneId.systemDefault()))")
    @Mapping(target = "createTime", expression = "java(bean.getCreateTime()==null?null:LocalDateTime.ofInstant(bean.getCreateTime().toInstant(),ZoneId.systemDefault()))")
    BpmTaskTodoPageItemRespVO convert1(Task bean);

    @Named("convertSuspendedToSuspensionState")
    default Integer convertSuspendedToSuspensionState(boolean suspended) {
        return suspended ? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode();
    }

    default List<BpmTaskDonePageItemRespVO> convertList2(List<HistoricTaskInstance> tasks,
        Map<String, BpmTaskExtDO> bpmTaskExtDOMap, Map<String, HistoricProcessInstance> historicProcessInstanceMap,
        Map<Long, AdminUserRespDTO> userMap) {
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskDonePageItemRespVO respVO = convert2(task);
            BpmTaskExtDO taskExtDO = bpmTaskExtDOMap.get(task.getId());
            copyTo(taskExtDO, respVO);
            HistoricProcessInstance processInstance = historicProcessInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                AdminUserRespDTO startUser = userMap.get(NumberUtils.parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            return respVO;
        });
    }

    BpmTaskDonePageItemRespVO convert2(HistoricTaskInstance bean);

    @Mappings({@Mapping(source = "processInstance.id", target = "id"),
        @Mapping(source = "processInstance.name", target = "name"),
        @Mapping(source = "processInstance.startUserId", target = "startUserId"),
        @Mapping(source = "processInstance.processDefinitionId", target = "processDefinitionId"),
        @Mapping(source = "startUser.nickname", target = "startUserNickname")})
    BpmTaskTodoPageItemRespVO.ProcessInstance convert(ProcessInstance processInstance, AdminUserRespDTO startUser);

    default List<BpmTaskRespVO> convertList3(List<HistoricTaskInstance> tasks,
        Map<String, BpmTaskExtDO> bpmTaskExtDOMap, HistoricProcessInstance processInstance,
        Map<Long, AdminUserRespDTO> userMap, Map<Long, DeptRespDTO> deptMap) {
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskRespVO respVO = convert3(task);
            BpmTaskExtDO taskExtDO = bpmTaskExtDOMap.get(task.getId());
            copyTo(taskExtDO, respVO);
            if (processInstance != null) {
                AdminUserRespDTO startUser = userMap.get(NumberUtils.parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            AdminUserRespDTO assignUser = userMap.get(NumberUtils.parseLong(task.getAssignee()));
            if (assignUser != null) {
                respVO.setAssigneeUser(convert3(assignUser));
                DeptRespDTO dept = deptMap.get(assignUser.getDeptId());
                if (dept != null) {
                    respVO.getAssigneeUser().setDeptName(dept.getName());
                }
            }
            return respVO;
        });
    }

    @Mapping(source = "taskDefinitionKey", target = "definitionKey")
    @Mapping(target = "createTime", expression = "java(bean.getCreateTime() == null ? null : LocalDateTime.ofInstant(bean.getCreateTime().toInstant(), ZoneId.systemDefault()))")
    @Mapping(target = "endTime", expression = "java(bean.getEndTime() == null ? null : LocalDateTime.ofInstant(bean.getEndTime().toInstant(), ZoneId.systemDefault()))")
    BpmTaskRespVO convert3(HistoricTaskInstance bean);

    BpmTaskRespVO.User convert3(AdminUserRespDTO bean);

    @Mapping(target = "id", ignore = true)
    void copyTo(BpmTaskExtDO from, @MappingTarget BpmTaskDonePageItemRespVO to);

    @Mappings({@Mapping(source = "processInstance.id", target = "id"),
        @Mapping(source = "processInstance.name", target = "name"),
        @Mapping(source = "processInstance.startUserId", target = "startUserId"),
        @Mapping(source = "processInstance.processDefinitionId", target = "processDefinitionId"),
        @Mapping(source = "startUser.nickname", target = "startUserNickname")})
    BpmTaskTodoPageItemRespVO.ProcessInstance convert(HistoricProcessInstance processInstance,
        AdminUserRespDTO startUser);

    default BpmTaskExtDO convert2TaskExt(Task task) {
        BpmTaskExtDO taskExtDO = new BpmTaskExtDO().setTaskId(task.getId())
            .setAssigneeUserId(NumberUtils.parseLong(task.getAssignee())).setName(task.getName())
            .setProcessDefinitionId(task.getProcessDefinitionId()).setProcessInstanceId(task.getProcessInstanceId());
        taskExtDO.setCreateTime(LocalDateTimeUtil.of(task.getCreateTime()));
        return taskExtDO;
    }

    default BpmMessageSendWhenTaskCreatedReqDTO convert(ProcessInstance processInstance, AdminUserRespDTO startUser,
        Task task) {
        BpmMessageSendWhenTaskCreatedReqDTO reqDTO = new BpmMessageSendWhenTaskCreatedReqDTO();
        reqDTO.setProcessInstanceId(processInstance.getProcessInstanceId())
            .setProcessInstanceName(processInstance.getName()).setStartUserId(startUser.getId())
            .setStartUserNickname(startUser.getNickname()).setTaskId(task.getId()).setTaskName(task.getName())
            .setAssigneeUserId(NumberUtils.parseLong(task.getAssignee()));
        return reqDTO;
    }

}
