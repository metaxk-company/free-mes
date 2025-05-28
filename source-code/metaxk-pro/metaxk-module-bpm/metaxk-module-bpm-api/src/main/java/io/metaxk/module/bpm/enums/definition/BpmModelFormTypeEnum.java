package io.metaxk.module.bpm.enums.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 模型的表单类型的枚举
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum BpmModelFormTypeEnum {

    NORMAL(10, "流程表单"), // 对应 BpmFormDO
    CUSTOM(20, "业务表单") // 业务自己定义的表单，自己进行数据的存储
    ;

    private final Integer type;
    private final String desc;
}
