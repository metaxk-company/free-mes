package io.metaxk.module.mes.dal.dataobject.dv;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;



/**
 * 设备状态
 * @author 万界星空
 */
@Data
@TableName("dv_machinery_status")
public class MachineryStatus extends EntityCommon {

    private Long id;

    private String statusName;

    private String remark;

}
