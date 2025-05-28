package io.metaxk.module.mes.dal.dataobject.pro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;


/**
 * 生产报工 实体类
 * @author 万界星空
 */
@TableName("pro_feedback")
@Data
public class Feedback  {

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 工作站ID
     */
    private Long workstationId;
    /**
     * 工作站编号
     */
    private String workstationCode;
    /**
     * 工作站名称
     */
    private String workstationName;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 生产工单名称
     */
    private String workorderName;
    /**
     * 生产任务ID
     */
    private Long taskId;
    /**
     * 生产任务编号
     */
    private String taskCode;
    /**
     * 产品物料ID
     */
    private Long itemId;
    /**
     * 产品物料编码
     */
    private String itemCode;
    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 单位
     */
    private String unitOfMeasure;

    /**
     * 报工数量
     */
    private BigDecimal quantity;
    /**
     * 本次报工数量
     */
  // private BigDecimal quantityFeedback;


    /**
     * 报工用户名
     */
    private String userName;

    /**
     * 开工时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feedbackTime;

    /**
     * 状态
     */
    private String status;
    /**
     * 工序ID
     */
    private Long processId;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;


    private String equipmentCode;

    // private String workHour;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String pauseTime;

    private String creator;

    private String updater;


    /**
     * 排产数量
     */
    private BigDecimal orderQuantity;


    /**
     * 暂停原因
     */
    private String pauseReason;


    /**
     * 订单总数量
     */
    private BigDecimal workOrderQuentity;


    /**
     * 报工进度
     */
    private  BigDecimal reportingProgress;

    private String assignUsername;
}
