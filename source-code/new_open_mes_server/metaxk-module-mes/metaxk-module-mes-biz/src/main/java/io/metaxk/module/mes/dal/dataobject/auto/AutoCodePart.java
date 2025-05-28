package io.metaxk.module.mes.dal.dataobject.auto;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 编码生成规则组成 DO
 * @author 万界星空
 */
@TableName("sys_auto_code_part")
@Data
public class AutoCodePart {

    @TableId
    private Long partId;

    private Long ruleId;

    private Integer partIndex;

    private String partType;

    private String partCode;

    private String partName;

    private Integer partLength;

    private String dateFormat;

    private String inputCharacter;

    private String fixCharacter;

    private Integer seriaStartNo;

    private Integer seriaStep;

    private Integer seriaNowNo;

    private String cycleFlag;

    private String cycleMethod;

    private Long tenantId;


}
