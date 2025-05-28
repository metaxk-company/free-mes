package io.metaxk.module.mes.dal.dataobject.pro;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 工艺路线 DO
 * @author 万界星空
 */
@TableName("pro_route")
@Data
public class Route extends EntityCommon {

    /**
     * 工艺路线ID
     */
    private Long id;
    /**
     * 工艺路线编号
     */
    private String routeCode;
    /**
     * 工艺路线名称
     */
    private String routeName;
    /**
     * 工艺路线说明
     */
    private String routeDesc;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Boolean deleted;

}
