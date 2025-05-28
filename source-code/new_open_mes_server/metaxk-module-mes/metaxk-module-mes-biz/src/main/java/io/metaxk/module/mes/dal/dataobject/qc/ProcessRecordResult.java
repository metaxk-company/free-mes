package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;


/**
 * 工序检验单结果实体类
 * @author 万界星空
 * @time 2023/7/11 16:45
 */
@Data
@TableName("qc_process_record_result")
public class ProcessRecordResult extends EntityCommon {

    private Long id;

    //工序检验单id
    private Long recordId;

    //物料编码
    private String productCode;

    //工序编号
    private String processCode;

    //工序名称
    private String processName;

    //检测数量
    private String detectionNumber;

    //是否有检测标准0：否1：是
    private String inspectFlag;

    //是否合格0：不合格1：合格
    private String resultStatus;

    //序列号
    private String sortNumber;

}
