package io.metaxk.module.mes.dal.dataobject.dv;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;


/**
 * 设备
 * @author 万界星空
 */
@TableName("dv_machinery")
@Data
public class Machinery extends EntityCommon {

    /**
     * 设备类型ID
     */
    private Long id;
    /**
     * 设备类型编码
     */
    private String machineryCode;
    /**
     * 设备类型名称
     */
    private String machineryName;
    /**
     * 品牌
     */
    private String machineryBrand;
    /**
     * 规格型号
     */
    private String machinerySpec;
    /**
     * 设备类型ID
     */
    private Long machineryTypeId;
    /**
     * 设备类型编码
     */
    private String machineryTypeCode;
    /**
     * 设备类型名称
     */
    private String machineryTypeName;
    /**
     * 所属车间ID
     */
    private Long workshopId;
    /**
     * 所属车间编码
     */
    private String workshopCode;
    /**
     * 所属车间名称
     */
    private String workshopName;
    /**
     * 设备状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 备注
     */
    private String location;


    @TableField(exist = false)
    private String onlineRate;

    /**
     * 产能
     */
    private String capacity;

    /**
     * 型号
     */
    private String pclModel;

    /**
     * 购买日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchasingTime;

    /**
     * 分配用户
     */
    private String assignUser;


}
