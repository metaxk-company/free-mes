package io.metaxk.module.bpm.dal.mysql.definition;

import io.metaxk.module.bpm.dal.dataobject.definition.BpmTaskAssignRuleDO;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper
public interface BpmTaskAssignRuleMapper extends BaseMapperX<BpmTaskAssignRuleDO> {

    default List<BpmTaskAssignRuleDO> selectListByProcessDefinitionId(String processDefinitionId,
                                                                      @Nullable String taskDefinitionKey) {
        return selectList(new QueryWrapperX<BpmTaskAssignRuleDO>()
                .eq("process_definition_id", processDefinitionId)
                .eqIfPresent("task_definition_key", taskDefinitionKey));
    }

    default List<BpmTaskAssignRuleDO> selectListByModelId(String modelId) {
        return selectList(new QueryWrapperX<BpmTaskAssignRuleDO>()
                .eq("model_id", modelId)
                .eq("process_definition_id", BpmTaskAssignRuleDO.PROCESS_DEFINITION_ID_NULL));
    }

    default BpmTaskAssignRuleDO selectListByModelIdAndTaskDefinitionKey(String modelId,
                                                                        String taskDefinitionKey) {
        return selectOne(new QueryWrapperX<BpmTaskAssignRuleDO>()
                .eq("model_id", modelId)
                .eq("process_definition_id", BpmTaskAssignRuleDO.PROCESS_DEFINITION_ID_NULL)
                .eq("task_definition_key", taskDefinitionKey));
    }



}
