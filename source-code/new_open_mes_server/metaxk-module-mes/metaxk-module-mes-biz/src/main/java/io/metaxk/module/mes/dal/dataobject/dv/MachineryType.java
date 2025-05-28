package io.metaxk.module.mes.dal.dataobject.dv;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 设备类型 DO
 * @author 万界星空
 */
@TableName("dv_machinery_type")
@Data
public class MachineryType extends EntityCommon {

    /**
     * 设备类型ID
     */
    private Long id;
    /**
     * 设备类型编码
     */
    private String machineryTypeCode;
    /**
     * 设备类型名称
     */
    private String machineryTypeName;
    /**
     * 父类型ID
     */
    private Long parentTypeId;
    /**
     * 所有父节点ID
     */
    private String ancestors;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;


    /** 子菜单 */
    @TableField(exist = false)
    private List<MachineryType> children = new ArrayList<MachineryType>();

}
