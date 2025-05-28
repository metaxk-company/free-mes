package io.metaxk.module.mes.dal.dataobject.auto;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 编码生成记录 DO
 * @author 万界星空
 */
@TableName("sys_auto_code_result")
@Data
public class AutoCodeResult {

    /**
     * 记录ID
     */
    @TableId
    private Long codeId;
    /**
     * 规则ID
     */
    private Long ruleId;
    /**
     * 生成日期时间
     */
    private String genDate;
    /**
     * 最后产生的序号
     */
    private Integer genIndex;
    /**
     * 最后产生的值
     */
    private String lastResult;
    /**
     * 最后产生的流水号
     */
    private Integer lastSerialNo;
    /**
     * 最后传入的参数
     */
    private String lastInputChar;
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
