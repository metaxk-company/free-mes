package io.metaxk.module.mes.dal.dataobject.md;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 型号
 *
 * @author 万界星空MES
 */
@TableName("md_model")
@Data
public class Model extends EntityCommon {

    /**
     * 型号ID
     */
    private Long id;
    /**
     * 型号编号
     */
    private String number;
    /**
     * 型号名称
     */
    private String name;

}
