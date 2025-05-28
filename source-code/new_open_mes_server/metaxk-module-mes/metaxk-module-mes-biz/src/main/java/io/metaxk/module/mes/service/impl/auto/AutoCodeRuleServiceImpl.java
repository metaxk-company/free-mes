package io.metaxk.module.mes.service.impl.auto;

import cn.hutool.core.collection.CollectionUtil;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodeRule;
import io.metaxk.module.mes.dal.mysql.auto.AutoCodeRuleMapper;
import io.metaxk.module.mes.service.auto.AutoCodeRuleService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;



@Service
public class AutoCodeRuleServiceImpl implements AutoCodeRuleService {

    @Resource
    private AutoCodeRuleMapper sysAutoCodeRuleMapper;

    @Override
    public AutoCodeRule getOne(String ruleCode) {
        AutoCodeRule param = new AutoCodeRule();
        param.setRuleCode(ruleCode);
        List<AutoCodeRule> rules = sysAutoCodeRuleMapper.selectSysAutoCodeResultList(param);
        if(CollectionUtil.isNotEmpty(rules)){
            return rules.get(0);
        }
        return null;
    }

    @Override
    public List<AutoCodeRule> selectAutoCodeList(AutoCodeRule sysAutoCodeRule) {
        return sysAutoCodeRuleMapper.selectSysAutoCodeResultList(sysAutoCodeRule);
    }



    @Override
    public AutoCodeRule findById(Long ruleId) {
        return sysAutoCodeRuleMapper.findById(ruleId);
    }



    @Override
    public String checkRuleCodeUnique(AutoCodeRule sysAutoCodeRule) {
        AutoCodeRule rule = sysAutoCodeRuleMapper.checkRuleCodeUnique(sysAutoCodeRule.getRuleCode());
        if (StringUtils.isNotNull(rule) && rule.getRuleId().longValue() != sysAutoCodeRule.getRuleId().longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    @Override
    public String checkRuleNameUnique(AutoCodeRule sysAutoCodeRule) {
        AutoCodeRule rule = sysAutoCodeRuleMapper.checkRuleNameUnique(sysAutoCodeRule.getRuleName());
        if (StringUtils.isNotNull(rule) && rule.getRuleId().longValue() != sysAutoCodeRule.getRuleId().longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    @Override
    public Integer insertInfo(AutoCodeRule rule) {
        return sysAutoCodeRuleMapper.insert(rule);
    }



    @Override
    public Integer deleteById(Long ruleId) {

        return sysAutoCodeRuleMapper.deleteById(ruleId);
    }


}
