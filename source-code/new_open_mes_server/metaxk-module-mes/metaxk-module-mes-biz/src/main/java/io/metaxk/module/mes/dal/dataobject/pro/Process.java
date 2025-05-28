package io.metaxk.module.mes.dal.dataobject.pro;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 生产工序 DO
 * @author 万界星空
 */
@TableName("pro_process")
@Data
public class Process extends EntityCommon {

    /**
     * 工序ID
     */
    private Long id;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;
    /**
     * 工艺要求
     */
    private String attention;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 工时(分钟)
     */
    private Long manHour;
    /**
     * 工序二维码
     */
    private String processUrl;
    /**
     * 二维码规则
     */
    private String barcodeFormat;


    /**
     * 准备时间
     */
    private String preparationTime;

    /**
     * 生产时间
     */
    private String productiveTime;

    /**
     * 是否质检
     */
    private  String qualityInspection;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Boolean deleted;

}
