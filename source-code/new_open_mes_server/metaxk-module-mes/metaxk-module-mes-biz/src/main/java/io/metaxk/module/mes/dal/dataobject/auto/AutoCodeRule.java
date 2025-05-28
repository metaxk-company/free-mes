package io.metaxk.module.mes.dal.dataobject.auto;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 编码生成规则 DO
 *
 * @author 万界星空MES
 */
@TableName("sys_auto_code_rule")
@Data
public class AutoCodeRule {
    /**
     * 规则ID
     */
    @TableId
    private Long ruleId;
    /**
     * 规则编码
     */
    private String ruleCode;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 描述
     */
    private String ruleDesc;
    /**
     * 最大长度
     */
    private Integer maxLength;
    /**
     * 是否补齐
     */
    private String isPadded;
    /**
     * 补齐字符
     */
    private String paddedChar;
    /**
     * 补齐方式
     */
    private String paddedMethod;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private String attr2;
    /**
     * 预留字段3
     */
    private Integer attr3;
    /**
     * 预留字段4
     */
    private Integer attr4;

    private Long tenantId;



}
