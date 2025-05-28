package io.metaxk.module.mes.dal.dataobject.md;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 设备资源
 * @author 万界星空
 */
@TableName("md_workstation_machine")
@Data
public class WorkStationMachine extends EntityCommon {

    /**
     * 工作站设备资源ID
     */
    private Long id;
    /**
     * 工作站ID
     */
    private Long workstationId;
    /**
     * 设备ID
     */
    private Long machineryId;
    /**
     * 设备编码
     */
    private String machineryCode;
    /**
     * 设备名称
     */
    private String machineryName;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 备注
     */
    private String remark;

}
