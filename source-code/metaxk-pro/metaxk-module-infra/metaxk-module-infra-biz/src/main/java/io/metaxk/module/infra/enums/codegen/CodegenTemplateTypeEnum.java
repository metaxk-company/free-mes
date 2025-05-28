package io.metaxk.module.infra.enums.codegen;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码生成模板类型
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@AllArgsConstructor
@Getter
public enum CodegenTemplateTypeEnum {

    CRUD(1), // 单表（增删改查）
    TREE(2), // 树表（增删改查）
    ;

    /**
     * 类型
     */
    private final Integer type;

}
