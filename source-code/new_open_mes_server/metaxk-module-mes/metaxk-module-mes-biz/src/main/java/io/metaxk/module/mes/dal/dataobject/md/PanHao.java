package io.metaxk.module.mes.dal.dataobject.md;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 盘号
 * @author 万界星空MES
 */
@TableName("md_pan_hao")
@Data
public class PanHao extends EntityCommon {

    /**
     * 盘号ID
     */
    private Long id;
    /**
     * 盘号编号
     */
    private String number;

}
