package io.metaxk.module.mes.dal.dataobject.pro;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 报工设备状态
 * @author 万界星空
 */
@Data
@TableName("pro_feedback_equ_status")
public class FeedbackEquStatus {


    private Long id;

    private String status;

    private LocalDateTime time;

    private String  remork;

    private String taskCode;




}
