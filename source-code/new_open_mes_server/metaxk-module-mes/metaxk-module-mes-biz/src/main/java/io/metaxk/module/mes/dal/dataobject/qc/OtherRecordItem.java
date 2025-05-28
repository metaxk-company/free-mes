package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 其他检验信息实体类
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Data
@TableName("qc_other_record_item")
public class OtherRecordItem extends EntityCommon {

    private Long id;

    //其他检验单编号
    private String recordNumber;

    //产品编号
    private String itemCode;

    //产品条码
    private String itemBarcode;

    //序列号
    private String sortNumber;

    //检验项目id
    private Long standardItemId;

    //检测名称
    private String itemName;

    //检测标准
    private String itemStandard;

    //检测器具
    private String itemDevice;

    //检测实际信息
    private String itemValue;

    //描述
    private String remark;

    //是否合格 0：不合格 1：合格：2:不质检
    private String status;

    //是否检验0：否1：是
    private String flag;
}
