package io.metaxk.module.mes.dal.mysql.auto;

import java.util.*;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodeRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 编码生成规则 Mapper
 * @author 万界星空
 */
@Mapper
public interface AutoCodeRuleMapper extends BaseMapperX<AutoCodeRule> {



    /**
     * 编码生成规则集合
     * @param param
     * @return List<AutoCodeRule>
     */
    default List<AutoCodeRule> selectSysAutoCodeResultList(AutoCodeRule param) {
        return selectList(new LambdaQueryWrapperX<AutoCodeRule>()
                .eqIfPresent(AutoCodeRule::getRuleId, param.getRuleId())
                .likeIfPresent(AutoCodeRule::getRuleName, param.getRuleName())
                .eqIfPresent(AutoCodeRule::getRuleCode, param.getRuleCode()));
    }


    /**
     * 根据id查询编码生成规则
     * @param ruleId
     * @return AutoCodeRule
     */
    default AutoCodeRule findById(Long ruleId) {
        return selectOne(new LambdaQueryWrapperX<AutoCodeRule>()
                .eqIfPresent(AutoCodeRule::getRuleId, ruleId));
    }


    /**
     * 根据编号查询编码生成规则
     * @param ruleCode
     * @return AutoCodeRule
     */
    default AutoCodeRule checkRuleCodeUnique(String ruleCode) {
        return selectOne(new LambdaQueryWrapperX<AutoCodeRule>()
                .eqIfPresent(AutoCodeRule::getRuleCode, ruleCode));
    }


    /**
     * 根据ruleName查询编码生成规则
     * @param ruleName
     * @return AutoCodeRule
     */
    default AutoCodeRule checkRuleNameUnique(String ruleName) {
        return selectOne(new LambdaQueryWrapperX<AutoCodeRule>()
                .eqIfPresent(AutoCodeRule::getRuleName, ruleName));
    }
}
