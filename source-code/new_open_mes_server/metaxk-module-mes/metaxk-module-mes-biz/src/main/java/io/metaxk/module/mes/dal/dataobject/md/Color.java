package io.metaxk.module.mes.dal.dataobject.md;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 颜色
 *
 * @author 万界星空MES
 */
@TableName("md_color")
@Data
public class Color extends EntityCommon {

    /**
     * 盘号ID
     */
    private Long id;
    /**
     * 颜色名称
     */
    private String name;

}
