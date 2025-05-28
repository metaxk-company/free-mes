package io.metaxk.module.mes.dal.dataobject.pro;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;


/**
 * 临时工时
 * @author 万界星空
 */
@Data
@TableName("pro_temporary_workhours ")
public class TemporaryWorkHours extends EntityCommon {


    private Long id;

    private String workhoursType;

    private String workhours;

    private String workerName;

    private String workshopName;

    @TableField(exist = false)
    private Integer totalWorkhours;

}
