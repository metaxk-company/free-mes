package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.util.List;

/**
 * 检测器具实体类
 * @author 万界星空
 * @time 2023/7/6 11:14
 */
@Data
@TableName("qc_device")
public class Device extends EntityCommon {

    private Long    id;

    private String  deviceCode;

    private String  deviceName;

    private String  agreement;

    private String  workshop;

    private String  department;


     private String  remark;

     private String process;

     @TableField(exist = false)
     public List<String> processList;


}
