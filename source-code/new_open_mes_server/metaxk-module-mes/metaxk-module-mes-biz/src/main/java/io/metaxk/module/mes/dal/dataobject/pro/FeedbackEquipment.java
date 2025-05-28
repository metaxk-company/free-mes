package io.metaxk.module.mes.dal.dataobject.pro;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 报工设备
 * @author 万界星空
 */
@Data
@TableName("pro_feedback_equipment")
public class FeedbackEquipment {
    private Long  id;

    private Long   feedbackCode;

    private String equipmentCode;

    private String pauseTime;

    private String taskCode;




}
