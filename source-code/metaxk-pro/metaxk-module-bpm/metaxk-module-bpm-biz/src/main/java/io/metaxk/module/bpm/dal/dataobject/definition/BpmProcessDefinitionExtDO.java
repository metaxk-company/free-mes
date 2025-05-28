package io.metaxk.module.bpm.dal.dataobject.definition;

import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import io.metaxk.module.bpm.enums.definition.BpmModelFormTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Bpm 流程定义的拓展表
 * 主要解决 Activiti {@link ProcessDefinition} 不支持拓展字段，所以新建拓展表
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@TableName(value = "bpm_process_definition_ext", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmProcessDefinitionExtDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 流程定义的编号
     *
     * 关联 ProcessDefinition 的 id 属性
     */
    private String processDefinitionId;
    /**
     * 流程模型的编号
     *
     * 关联 Model 的 id 属性
     */
    private String modelId;
    /**
     * 描述
     */
    private String description;

    /**
     * 表单类型
     *
     * 关联 {@link BpmModelFormTypeEnum}
     */
    private Integer formType;
    /**
     * 动态表单编号
     * 在表单类型为 {@link BpmModelFormTypeEnum#NORMAL} 时
     *
     * 关联 {@link BpmFormDO#getId()}
     */
    private Long formId;
    /**
     * 表单的配置
     * 在表单类型为 {@link BpmModelFormTypeEnum#NORMAL} 时
     *
     * 冗余 {@link BpmFormDO#getConf()}
     */
    private String formConf;
    /**
     * 表单项的数组
     * 在表单类型为 {@link BpmModelFormTypeEnum#NORMAL} 时
     *
     * 冗余 {@link BpmFormDO#getFields()} ()}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> formFields;
    /**
     * 自定义表单的提交路径，使用 Vue 的路由地址
     * 在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时
     */
    private String formCustomCreatePath;
    /**
     * 自定义表单的查看路径，使用 Vue 的路由地址
     * 在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时
     */
    private String formCustomViewPath;


}
