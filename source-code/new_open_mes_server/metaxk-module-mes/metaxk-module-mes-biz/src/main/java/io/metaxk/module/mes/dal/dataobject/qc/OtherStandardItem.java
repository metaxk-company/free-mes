package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 其他检验标准实体类
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Data
@TableName("qc_other_standard_item")
public class OtherStandardItem extends EntityCommon {

    private Long id;

    /**
     * 检验标准编号
     */
    private String otherStandardNumber;

    private String name;

    private String standard;

    private String  device;

    private String remark;

    private String enableFlag;
}
