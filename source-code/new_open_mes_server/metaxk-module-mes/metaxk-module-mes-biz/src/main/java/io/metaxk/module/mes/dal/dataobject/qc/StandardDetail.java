package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 工序检验标准明细
 * @author 万界星空
 * @time 2023/7/11 17:15
 */
@Data
@TableName("qc_process_standard_item")
public class StandardDetail extends EntityCommon {

    private Long id;

    @TableField("process_standard_number")
    private String processStandardCode;

    @TableField("name")
    private String detectionName;

    @TableField("standard")
    private String detectionStandard;

    @TableField("device")
    private String  detectionDevice;

    private String processCode;

    private String remark;

    private String enableFlag;

}
