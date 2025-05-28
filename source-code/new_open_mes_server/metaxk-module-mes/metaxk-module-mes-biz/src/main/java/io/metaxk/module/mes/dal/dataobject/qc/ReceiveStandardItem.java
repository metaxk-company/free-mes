package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 来料检验标准明细实体类
 * @author 万界星空
 * @time 2023/7/26 10:28
 */
@Data
@TableName("qc_receive_standard_item")
public class ReceiveStandardItem  extends EntityCommon {

    private Long id;

    //来料检验标准编号
    private String recStandardNumber;

    private String name;

    private String standard;

    private String device;

    //private String recNumber;
    private String itemCode;

    private String remark;

    private String enableFlag;

}
