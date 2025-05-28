package io.metaxk.module.mes.controller.admin.auto;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 万界星空
 */
@RestController
@RequestMapping("/system/autocode")
public class AutoCodeController {


    @Resource
    private AutoCodeUtil autoCodeUtil;


    @GetMapping(value = {"/get/{ruleCode}/{inputCharacter}","/get/{ruleCode}"})
    public CommonResult getAutoCode(@PathVariable String ruleCode, @PathVariable(required = false) String inputCharacter){
        return CommonResult.success(autoCodeUtil.genSerialCode(ruleCode, inputCharacter));
    }


}
