package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 来料检验结果实体类
 * @author 万界星空
 * @time 2023/7/26 14:28
 */
@Data
@TableName("qc_receive_record_result")
public class ReceiveRecordResult extends EntityCommon {

    private Long id;
    //来料检验单id
    private Long recordId;
    //产品编号
    private String itemCode;
    //到货通知单编号
    private String recNumber;
    //检测数量
    private String detectionNumber;
    //是否有检测标准0：否1：是
    private String inspectFlag;
    //是否合格0：不合格1：合格
    private String resultStatus;
    //序列号
    private String sortNumber;

}
