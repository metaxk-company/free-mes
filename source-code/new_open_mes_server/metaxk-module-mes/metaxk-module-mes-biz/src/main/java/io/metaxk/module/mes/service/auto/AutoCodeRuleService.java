package io.metaxk.module.mes.service.auto;


import io.metaxk.module.mes.dal.dataobject.auto.AutoCodeRule;

import java.util.List;

/**
 * 编码生成规则 Service 接口
 * @author 万界星空
 */
public interface AutoCodeRuleService {

     /**
      * 根据编号查询编码生成规则
      * @param ruleCode
      * @return AutoCodeRule
      */
     public AutoCodeRule getOne(String ruleCode);

     /**
      * 编码生成规则列表
      * @param sysAutoCodeRule
      * @return List<AutoCodeRule>
      */
     public List<AutoCodeRule> selectAutoCodeList(AutoCodeRule sysAutoCodeRule);

     /**
      * 根据id查询编码生成规则
      * @param ruleId
      * @return AutoCodeRule
      */
     public AutoCodeRule findById(Long ruleId);

     /**
      * 校验 编码生成规则
      * @param sysAutoCodeRule
      * @return String
      */
     public String checkRuleCodeUnique(AutoCodeRule sysAutoCodeRule);

     /**
      * 校验编码生成规则
      * @param sysAutoCodeRule
      * @return String
      */
     public String checkRuleNameUnique(AutoCodeRule sysAutoCodeRule);

     /**
      * 新增编码生成规则
      * @param rule
      * @return Integer
      */
     public Integer insertInfo(AutoCodeRule rule);


     /**
      * 删除编码生成规则
      * @param ruleId
      * @return Integer
      */
     public Integer deleteById(Long ruleId);












}
