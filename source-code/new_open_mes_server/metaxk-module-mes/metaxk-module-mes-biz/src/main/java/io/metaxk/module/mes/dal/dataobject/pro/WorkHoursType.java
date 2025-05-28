package io.metaxk.module.mes.dal.dataobject.pro;


import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;



/**
 * 工时类型
 * @author 万界星空
 */
@Data
@TableName("pro_workhours_type")
public class WorkHoursType extends EntityCommon {


    private Long id;

    private String workhoursType;


    private String remark;

}
