package io.metaxk.module.mes.dal.dataobject.pro;



import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 报工工时
 * @author 万界星空
 */
@Data
@TableName("pro_feedback_hours")
public class FeedbackHours {

    private Long  id;

    private String  workorderCode;

    private String  taskCode;

    private String  itemName;

    private String  unitOfMeasure;

    private BigDecimal quantity;

    private String  userName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feedbackTime;

    private String  createBy;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  createTime;

    private String  updateBy;

    private LocalDateTime  updateTime;

    private String  equipmentCode;

    private String  workHour;

    private String  equipmentHour;



    /**
     * 工人完工时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  workerFinishedTime;


    /**
     * 设备完工时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  equipmentFinishedTime;


    private Long  feedbackId;

    /**
     * 新加字段 工作站编号
    */
    private String workstationCode;


    /**
     * 车间编号
    */
    private String workshopCode;


   /**
     * 工序编号
    */
    private String processCode;


}
