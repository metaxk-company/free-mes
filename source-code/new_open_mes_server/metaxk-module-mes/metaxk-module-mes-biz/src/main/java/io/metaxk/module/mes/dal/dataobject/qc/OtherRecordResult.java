package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 其他检验结果实体类
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Data
@TableName("qc_other_record_result")
public class OtherRecordResult extends EntityCommon {

    private Long id;
    //其他检验单
    private String recordNumber;
    //产品编号
    private String itemCode;
    //型号
    private String model;
    //规格
    private String spec;
    //线别
    private String lineType;
    //盘号
    private String reelNumber;
    //颜色
    private String color;
    //检测数量
    private String detectionNumber;
    //是否有检测标准0：否1：是
    private String inspectFlag;
    //是否合格0：不合格1：合格
    private String resultStatus;
    //序列号
    private String sortNumber;
}
