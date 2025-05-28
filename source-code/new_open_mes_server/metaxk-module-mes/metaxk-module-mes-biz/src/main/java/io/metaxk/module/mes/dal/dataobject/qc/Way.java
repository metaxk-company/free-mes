package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 检验方式实体类
 * @author 万界星空
 * @time 2023/7/6 10:18
 */
@Data
@TableName("qc_way")
public class Way extends EntityCommon {

    private Long id;

    private String inspectCode;

    private String inspectName;

}
