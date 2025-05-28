package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 工序检验单对比实体类
 * @author 万界星空
 * @time 2023/7/11 16:45
 */
@Data
@TableName("qc_process_record_item")
public class ProcessRecordItem extends EntityCommon {
    private Long id;

    private Long recordId;

    //物料编码
    private String productCode;

    //物料条码
    private String productBarcode;

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

    //是否合格 0：不合格 1：合格
    private String status;

    //是否检验0：否1：是
    private String flag;

}
