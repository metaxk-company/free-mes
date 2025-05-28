package io.metaxk.module.mes.dal.dataobject.md;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 车间
 * @author 万界星空
 */
@TableName("md_workshop")
@Data
public class WorkShop extends EntityCommon {

    /**
     * 车间ID
     */
    private Long id;
    /**
     * 车间编码
     */
    private String workshopCode;
    /**
     * 车间名称
     */
    private String workshopName;
    /**
     * 面积
     */
    private String area;
    /**
     * 负责人
     */
    private String charge;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;

}
