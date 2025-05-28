package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.util.List;

/**
 * 半成品入库实体类
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
@Data
@TableName("wh_semi_label")
public class SemiLabel extends EntityCommon {
    /**
     * id
     */
    private Long  id;

    /**
     * 编号
     */
    private String  number;

    /**
     * 仓库
     */
    private String warehouse;

    /**
     * 状态
     */
    private String  status;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<SemiLabelItem> semiLabelItemList;



    /**
     * 外部系统id
     */
    private String externalId;
}
